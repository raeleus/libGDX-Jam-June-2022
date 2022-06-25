package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lol.fraud.HexUtils;
import com.lol.fraud.HexUtils.TYPE;
import com.ray3k.template.*;
import com.ray3k.template.OgmoReader.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.screens.DialogDebug.*;
import com.ray3k.template.screens.DialogPause.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.Values.*;

public class GameScreen extends JamScreen {
    public static GameScreen gameScreen;
    public static final Color BG_COLOR = new Color();
    public Stage stage;
    public boolean paused;
    public Array<GroundEntity> grounds = new Array<>();
    public Array<LavaEntity> lavas = new Array<>();
    private float bubbleTimer;
    public HexUtils hexUtils;
    
    @Override
    public void show() {
        super.show();
    
        gameScreen = this;
        BG_COLOR.set(Color.BLACK);
    
        paused = false;
    
        stage = new Stage(new ScreenViewport(), batch);
        
        var root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (!paused && keycode == Keys.ESCAPE) {
                    paused = true;
                
                    DialogPause dialogPause = new DialogPause(GameScreen.this);
                    dialogPause.show(stage);
                    dialogPause.addListener(new PauseListener() {
                        @Override
                        public void resume() {
                            paused = false;
                        }
                    
                        @Override
                        public void quit() {
                            core.transition(new MenuScreen());
                        }
                    });
                }
                return super.keyDown(event, keycode);
            }
        });
    
        stage.addListener(new DebugListener());
    
        shapeDrawer = new ShapeDrawer(batch, skin.getRegion("white"));
        shapeDrawer.setPixelSize(.5f);
    
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    
        camera = new OrthographicCamera();
        viewport = new FitViewport(1024, 576, camera);
        camera.position.set(512, 288, 0);
    
        hexUtils = new HexUtils(HexUtils.flat, new Vector2(72, 64), new Vector2(-18, 0));
        hexUtils.generateRectangularGrid(20, 19, TYPE.EVENQ);
        
        entityController.clear();
        var tutorial = preferences.getInteger("tutorial", 1);
        if (tutorial > 6) {
            loadLevel("home");
        } else {
            loadLevel("tutorial" + Utils.intToTwoDigit(tutorial));
        }
    }
    
    Vector2 temp = new Vector2();
    
    @Override
    public void act(float delta) {
        if (!paused) {
            entityController.act(delta);
            vfxManager.update(delta);
        }
        stage.act(delta);
    
        bubbleTimer -= delta;
        if (bubbleTimer < 0) {
            bubbleTimer = MathUtils.random(bubbleDelayMin, bubbleDelayMax);
            var bubble = new BubbleEntity();
            var lava = lavas.random();
            bubble.setPosition(lava.x - 20 + MathUtils.random(40), lava.y - 20 + MathUtils.random(40));
            entityController.add(bubble);
    
            bubble = new BubbleEntity();
            lava = lavas.random();
            bubble.setPosition(lava.x - 20 + MathUtils.random(40), lava.y - 20 + MathUtils.random(40));
            entityController.add(bubble);
    
            bubble = new BubbleEntity();
            lava = lavas.random();
            bubble.setPosition(lava.x - 20 + MathUtils.random(40), lava.y - 20 + MathUtils.random(40));
            entityController.add(bubble);
        }
        
        if (isButtonJustPressed(Buttons.LEFT)) {
            temp.set(mouseX, mouseY);
            hexUtils.pixelToHex(temp);
        }
    }
    
    @Override
    public void draw(float delta) {
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(Color.WHITE);
        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        entityController.draw(paused ? 0 : delta);
        batch.end();
        vfxManager.endInputCapture();
        vfxManager.applyEffects();
        vfxManager.renderToScreen();
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        if (width + height != 0) {
            vfxManager.resize(width, height);
            viewport.update(width, height);
        
            stage.getViewport().update(width, height, true);
        }
    }
    
    @Override
    public void dispose() {
    
    }
    
    @Override
    public void hide() {
        super.hide();
        vfxManager.removeAllEffects();
        entityController.dispose();
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    public void loadLevel(String name) {
        var reader = new OgmoReader();
        reader.addListener(new OgmoAdapter() {
            @Override
            public void level(String ogmoVersion, int width, int height, int offsetX, int offsetY,
                              ObjectMap<String, OgmoValue> valuesMap) {
                System.out.println("valuesMap.get(\"blessing1\") = " + valuesMap.get("blessing1").asString());
            }
    
            @Override
            public void layer(String name, int gridCellWidth, int gridCellHeight, int offsetX, int offsetY) {
            
            }
    
            @Override
            public void entity(String name, int id, int x, int y, int width, int height, boolean flippedX,
                               boolean flippedY, int originX, int originY, int rotation, Array<EntityNode> nodes,
                               ObjectMap<String, OgmoValue> valuesMap) {
                switch (name) {
                    case "ground":
                        var ground = new GroundEntity();
                        ground.setPosition(x, y);
                        entityController.add(ground);
                        grounds.add(ground);
                        break;
                    case "lava":
                        var lava = new LavaEntity();
                        lava.setPosition(x, y);
                        entityController.add(lava);
                        lavas.add(lava);
                        break;
                    case "cliff":
                        var cliff = new CliffEntity();
                        cliff.setPosition(x, y);
                        entityController.add(cliff);
                        break;
                    case "player":
                        var player = new PlayerEntity();
                        player.setPosition(x, y);
                        entityController.add(player);
                        break;
                    case "satan_dummy":
                        var satan_dummy = new SatanDummyEntity();
                        satan_dummy.setPosition(x, y);
                        entityController.add(satan_dummy);
                        break;
                    case "pentagram":
                        var pentagram = new PentagramEntity();
                        pentagram.setPosition(x, y);
                        entityController.add(pentagram);
                        break;
                    default:
                        if (name.startsWith("tutorial")) {
                            var tutorial = new TutorialEntity(name);
                            tutorial.setPosition(x, y);
                            entityController.add(tutorial);
                        } else {
                            System.out.println(name);
                        }
                        break;
                }
            }
    
            @Override
            public void grid(int col, int row, int x, int y, int width, int height, int id) {
        
            }
    
            @Override
            public void decal(int x, int y, float originX, float originY, float scaleX, float scaleY, int rotation,
                              String texture, String folder, ObjectMap<String, OgmoValue> valuesMap) {
        
            }
    
            @Override
            public void tile(String tileSet, int col, int row, int x, int y, int id) {
        
            }
    
            @Override
            public void tile(String tileSet, int col, int row, int x, int y, int tileX, int tileY) {
        
            }
    
            @Override
            public void layerComplete() {
        
            }
    
            @Override
            public void levelComplete() {
                var iter = lavas.iterator();
                while (iter.hasNext()) {
                    var lava = iter.next();
                    for (var ground : grounds) {
                        if (MathUtils.isEqual(lava.x, ground.x) && MathUtils.isEqual(lava.y, ground.y)) {
                            iter.remove();
                            break;
                        }
                    }
                }
            }
        });
        reader.readFile(Gdx.files.internal("levels/" + name + ".json"));
    }
}
