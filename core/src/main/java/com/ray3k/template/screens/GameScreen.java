package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TypingLabel;
import com.lol.fraud.HexTile;
import com.lol.fraud.HexUtils;
import com.lol.fraud.HexUtils.TYPE;
import com.ray3k.stripe.PopTable;
import com.ray3k.template.*;
import com.ray3k.template.OgmoReader.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.screens.DialogDebug.*;
import com.ray3k.template.screens.DialogPause.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.Values.*;

public class GameScreen extends JamScreen {
    public static GameScreen gameScreen;
    public static final Color BG_COLOR = new Color();
    public static Stage stage;
    public boolean paused;
    public Array<GroundEntity> grounds = new Array<>();
    public Array<LavaEntity> lavas = new Array<>();
    public Array<Entity> characters = new Array<>();
    public Array<EnemyEntity> enemies = new Array<>();
    private float bubbleTimer;
    public HexUtils hexUtils;
    public static PlayerEntity player;
    public String level;
    public Music currentDialogAudio;
    public static PentagramEntity pentagramEntity;
    public enum Turn {
        STORY, PLAYER, PLAYER_MOVING, ENEMY, ENEMY_MOVING
    }
    public static Turn turn;
    
    public GameScreen(String level) {
        this.level = level;
    }
    
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
    
        hexUtils = new HexUtils(HexUtils.flat, new Vector2(36, 36), new Vector2(offsetX, offsetY));
        hexUtils.generateRectangularGrid(25, 25, TYPE.ODDR);
        
        entityController.clear();
        loadLevel(level);

        turn = Turn.PLAYER;
        if (level.equals("home") || level.equals("tutorial01")) {
            turn = Turn.STORY;
            var index = preferences.getInteger("dialog", 1);
            if (index > 10) {
                index = 1;
                preferences.putInteger("dialog", index);
                preferences.flush();
            }
            var audioArray = new Array<Music>();
            var textArray = new Array<String>();
            var imageArray = new Array<Image>();
            var jsonReader = new JsonReader();
            var jsonValue = jsonReader.parse(Gdx.files.internal("dialogs.json"));
            var satanImage = new Image(skin, "portrait-satan");
            satanImage.setScaling(Scaling.fit);
            var sonImage = new Image(skin, "portrait-son");
            sonImage.setScaling(Scaling.fit);
            switch (index) {
                case 1:
                    audioArray.add(bgm_01a);
                    audioArray.add(bgm_01b);
                    audioArray.add(bgm_01c);
                    textArray.add(jsonValue.getString("01a"));
                    textArray.add(jsonValue.getString("01b"));
                    textArray.add(jsonValue.getString("01c"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 2:
                    audioArray.add(bgm_02a);
                    audioArray.add(bgm_02b);
                    audioArray.add(bgm_02c);
                    textArray.add(jsonValue.getString("02a"));
                    textArray.add(jsonValue.getString("02b"));
                    textArray.add(jsonValue.getString("02c"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 3:
                    audioArray.add(bgm_03a);
                    audioArray.add(bgm_03b);
                    audioArray.add(bgm_03c);
                    textArray.add(jsonValue.getString("03a"));
                    textArray.add(jsonValue.getString("03b"));
                    textArray.add(jsonValue.getString("03c"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 4:
                    audioArray.add(bgm_04a);
                    audioArray.add(bgm_04b);
                    textArray.add(jsonValue.getString("04a"));
                    textArray.add(jsonValue.getString("04b"));
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 5:
                    audioArray.add(bgm_05a);
                    textArray.add(jsonValue.getString("05a"));
                    imageArray.add(satanImage);
                    break;
                case 6:
                    audioArray.add(bgm_06a);
                    audioArray.add(bgm_06b);
                    textArray.add(jsonValue.getString("06a"));
                    textArray.add(jsonValue.getString("06b"));
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 7:
                    audioArray.add(bgm_07a);
                    audioArray.add(bgm_07b);
                    audioArray.add(bgm_07c);
                    textArray.add(jsonValue.getString("07a"));
                    textArray.add(jsonValue.getString("07b"));
                    textArray.add(jsonValue.getString("07c"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 8:
                    audioArray.add(bgm_08a);
                    audioArray.add(bgm_08b);
                    textArray.add(jsonValue.getString("08a"));
                    textArray.add(jsonValue.getString("08b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 9:
                    audioArray.add(bgm_09a);
                    audioArray.add(bgm_09b);
                    textArray.add(jsonValue.getString("09a"));
                    textArray.add(jsonValue.getString("09b"));
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 10:
                    audioArray.add(bgm_10a);
                    audioArray.add(bgm_10b);
                    textArray.add(jsonValue.getString("10a"));
                    textArray.add(jsonValue.getString("10b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
            }
            
            var finalIndex = index;
            var popTable = new PopTable() {
                int progress = 0;
    
                @Override
                public void show(Stage stage, Action action) {
                    refresh();
                    super.show(stage, action);
                }
                
                public void refresh() {
                    if (progress >= audioArray.size) {
                        hide();
                        preferences.putInteger("dialog", finalIndex + 1);
                        preferences.flush();
    
                        if (currentDialogAudio != null) currentDialogAudio.stop();
                        turn = Turn.PLAYER;
                    }
                    else {
                        clearChildren();
    
                        if (currentDialogAudio != null) currentDialogAudio.stop();
                        currentDialogAudio = audioArray.get(progress);
                        currentDialogAudio.play();
    
                        var stack = new Stack();
                        add(stack).grow();
    
                        var table = new Table();
                        stack.add(table);
    
                        table.add(imageArray.get(progress)).bottom().left().size(500, 500).expand();
    
                        table = new Table();
                        stack.add(table);
    
                        var subTable = new Table();
                        subTable.setBackground(skin.getDrawable("dialog-10"));
                        table.add(subTable).bottom().right().expand();
                        
                        var typingLabel = new TypingLabel(textArray.get(progress) + "\nClick to continue...", skin);
                        typingLabel.setWrap(true);
                        subTable.add(typingLabel).grow();
                    }
                }
            };
            
            popTable.setModal(true);
            popTable.setFillParent(true);
            popTable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    popTable.progress++;
                    popTable.refresh();
                }
            });
            popTable.show(stage);
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
    
        for (int i = 0; i < grounds.size; i++) {
            var ground = grounds.get(i);
            ground.skeleton.setColor(Color.WHITE);
        }
        
        if (turn == Turn.PLAYER && !player.destroy) {
            temp.set(player.x, player.y);
            var pathHead = hexUtils.pixelToGridHex(temp);
            temp.set(mouseX, mouseY);
            var pathTail = hexUtils.pixelToGridHex(temp);
            if (pathTail != null && pathTail.weight == 0) {
                var obj = pathTail.userObject;
                if (obj instanceof GroundEntity) {
                    var path = hexUtils.getPath(pathTail, pathHead);
            
                    if (path.get(pathHead) != null) {
                        HexTile current = path.get(pathHead);
                        var ground = (GroundEntity) current.userObject;
                        ground.skeleton.setColor(Color.RED);
                        if (isButtonJustPressed(Buttons.LEFT)) {
                            player.moveTowardsTarget(300f, ground.x, ground.y);
                            sfx_gameWalk.play(sfx);
                            pathHead.weight = 0;
                            current.weight = 100;
                            turn = Turn.PLAYER_MOVING;
                        }
//                        for (int i = 1; i < path.size() && current != pathTail; i++) {
//                            current = path.get(current);
//                        }
                    }
                }
            }
        } else if (turn == Turn.ENEMY) {
            if (enemies.size == 0) {
                turn = Turn.PLAYER;
            } else {
                for (var enemy : enemies) {
                    enemy.takeTurn();
                }
                turn = Turn.ENEMY_MOVING;
            }
        } else if (turn == Turn.ENEMY_MOVING) {
            for (var enemy : enemies) {
                enemy.takeTurn();
            }
            turn = Turn.PLAYER;
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
    
//        for(var h: hexUtils.grid){
//            var color = Color.WHITE.cpy().lerp(Color.BLACK,h.weight/10f);
//            color.a = .75f;
//            shapeDrawer.setColor(color);
//            shapeDrawer.rectangle(h.pos.x - 36, h.pos.y - 32, 72, 64);
//            shapeDrawer.polygon(h.pos.x, h.pos.y, 6, 36, 32, 0);
//        }
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
//                System.out.println("valuesMap.get(\"blessing1\") = " + valuesMap.get("blessing1").asString());
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
                        var hex = hexUtils.pixelToGridHex(temp.set(x, y));
                        if (hex != null) {
                            hex.weight = 0;
                            hex.userObject = ground;
                        }
                        break;
                    case "lava":
                        var lava = new LavaEntity();
                        lava.setPosition(x, y);
                        entityController.add(lava);
                        lavas.add(lava);
                        hex = hexUtils.pixelToGridHex(temp.set(x, y));
                        if (hex != null) {
                            hex.weight = 200;
                            hex.userObject = lava;
                        }
                        break;
                    case "cliff":
                        var cliff = new CliffEntity();
                        cliff.setPosition(x, y);
                        entityController.add(cliff);
                        break;
                    case "player":
                        player = new PlayerEntity();
                        player.setPosition(x, y);
                        entityController.add(player);
                        characters.add(player);
                        break;
                    case "satan_dummy":
                        var satan_dummy = new SatanDummyEntity();
                        satan_dummy.setPosition(x, y);
                        entityController.add(satan_dummy);
                        characters.add(satan_dummy);
                        break;
                    case "pentagram":
                        pentagramEntity = new PentagramEntity();
                        pentagramEntity.setPosition(x, y);
                        entityController.add(pentagramEntity);
                        break;
                    case "snake":
                        var snake = new SnakeEntity();
                        snake.setPosition(x, y);
                        entityController.add(snake);
                        characters.add(snake);
                        enemies.add(snake);
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
                            var hex = hexUtils.pixelToGridHex(temp.set(lava.x, lava.y));
                            if (hex != null) {
                                hex.weight = 0;
                                hex.userObject = ground;
                            }
                            iter.remove();
                            break;
                        }
                    }
                }
                
                for (var character : characters) {
                    var hex = hexUtils.pixelToGridHex(temp.set(character.x, character.y));
                    if (hex != null) {
                        hex.weight = 100;
                    }
                }
            }
        });
        reader.readFile(Gdx.files.internal("levels/" + name + ".json"));
    }
}
