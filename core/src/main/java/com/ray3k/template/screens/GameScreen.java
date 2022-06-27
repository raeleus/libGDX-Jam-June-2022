package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.template.HexUtils;
import com.ray3k.template.HexUtils.TYPE;
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
    public static Array<GroundEntity> grounds = new Array<>();
    public static Array<LavaEntity> lavas = new Array<>();
    public static Array<Entity> characters = new Array<>();
    public static Array<EnemyEntity> enemies = new Array<>();
    private float bubbleTimer;
    public static HexUtils hexUtils;
    public static PlayerEntity player;
    public String level;
    public Music currentDialogAudio;
    public static PentagramEntity pentagramEntity;
    public static ShrineEntity shrineEntity;
    public enum Turn {
        STORY, PLAYER, PLAYER_MOVING, ENEMY, ENEMY_MOVING
    }
    public static Turn turn;
    public enum Mode {
        MOVE, STRIKE, DASH, THROW, WAIT
    }
    public static Mode mode;
    public enum Power {
        STRENGTH_OF_SAMSON, BLOOD_OF_THE_LAMB, PARTING_THE_RED_SEA, PATIENCE_OF_JOB, FAITH_OF_DAVID, BODY_OF_CHRIST,
        LANCE_OF_LONGINUS, WINGS_OF_MICHAEL, HOLY_TRINITY, GODSPEED, CROWN_OF_THORNS, HOLY_LIGHT, NOAHS_DOVE,
        MARK_OF_THE_BEAST, HOLY_GRAIL, ARC_OF_THE_COVENANT, CHRIST_THE_REDEEMER
    }
    public static Array<Power> powers = new Array<>();
    public static Table healthTable;
    public static Table pipTable;
    public static Table controlsTable;
    public static ButtonGroup<Button> controlsButtonGroup;
    public static Button strikeButton;
    public static Button throwButton;
    public static Button dashButton;
    public static Button waitButton;
    public static Array<String> blessings = new Array<>();
    public static String shrineTitle;
    public static int health = 3;
    public static int maxHealth = 3;
    public static int energy = 6;
    public static int maxEnergy = 6;
    public static boolean hasTrident = true;
    public static TridentEntity tridentEntity;
    public static String nextLevel;
    public static int killStreak;
    public static boolean killedThisTurn;
    public static boolean skipEnemyTurn;
    public static boolean wolfMode;
    
    public GameScreen(String level) {
        this.level = level;
        if (level.equals("level01")) {
            preferences.putBoolean("completedTutorial", true);
            preferences.flush();
        }
    }
    
    @Override
    public void show() {
        super.show();
        killStreak = 0;
        killedThisTurn = false;
        skipEnemyTurn = false;
        energy = maxEnergy;
        grounds.clear();
        lavas.clear();
        characters.clear();
        enemies.clear();
        player = null;
        pentagramEntity = null;
        shrineEntity = null;
        hexUtils = null;
        hasTrident = true;
        tridentEntity = null;
        nextLevel = null;
        blessings.clear();
    
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
        
        createStageElements();
    
        camera = new OrthographicCamera();
        viewport = new FitViewport(1024, 576, camera);
        camera.position.set(512, 288, 0);
    
        hexUtils = new HexUtils(HexUtils.flat, new Vector2(36, 36), new Vector2(offsetX, offsetY));
        hexUtils.generateRectangularGrid(25, 25, TYPE.ODDR, temp);
        
        entityController.clear();
        loadLevel(level);

        turn = Turn.PLAYER;
        mode = Mode.MOVE;
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
            var sonImage = new Image(skin, wolfMode ? "portrait-son-wolf" : "portrait-son");
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
    
                        if (bgm_menu.isPlaying()) {
                            stage.addAction(new Action() {
                                @Override
                                public boolean act(float delta) {
                                    bgm_menu.setVolume(Utils.approach(bgm_menu.getVolume(), 0, .25f * delta));
                                    if (MathUtils.isZero(bgm_menu.getVolume())) {
                                        bgm_menu.stop();
                                        return true;
                                    } else return false;
                                }
                            });
                        }
    
                        if (!bgm_game.isPlaying()) {
                            bgm_game.play();
                            bgm_game.setPosition(bgm_menu.getPosition());
                            bgm_game.setVolume(0);
                            bgm_game.setLooping(true);
                            stage.addAction(new Action() {
                                @Override
                                public boolean act(float delta) {
                                    bgm_game.setVolume(Utils.approach(bgm_game.getVolume(), bgm, .25f * delta));
                                    return MathUtils.isEqual(bgm_game.getVolume(), bgm);
                                }
                            });
                        }
                    }
                    else {
                        clearChildren();
    
                        if (currentDialogAudio != null) currentDialogAudio.stop();
                        currentDialogAudio = audioArray.get(progress);
                        if (wolfMode && imageArray.get(progress) == sonImage) currentDialogAudio = bgm_woof;
                        currentDialogAudio.setVolume(sfx);
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
                        
                        var typingLabel = new TypingLabel(textArray.get(progress) + "\n{NORMAL}Click to continue...", skin);
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
        } else if (level.equals("level13")) {
            turn = Turn.STORY;
            var index = preferences.getInteger("final", 1);
            if (index > 7) {
                index = 1;
                preferences.putInteger("final", index);
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
                    audioArray.add(bgm_final01a);
                    audioArray.add(bgm_final01b);
                    audioArray.add(bgm_final01c);
                    textArray.add(jsonValue.getString("final01a"));
                    textArray.add(jsonValue.getString("final01b"));
                    textArray.add(jsonValue.getString("final01c"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 2:
                    audioArray.add(bgm_final02a);
                    audioArray.add(bgm_final02b);
                    textArray.add(jsonValue.getString("final02a"));
                    textArray.add(jsonValue.getString("final02b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 3:
                    audioArray.add(bgm_final03a);
                    audioArray.add(bgm_final03b);
                    textArray.add(jsonValue.getString("final03a"));
                    textArray.add(jsonValue.getString("final03b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 4:
                    audioArray.add(bgm_final04a);
                    audioArray.add(bgm_final04b);
                    textArray.add(jsonValue.getString("final04a"));
                    textArray.add(jsonValue.getString("final04b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 5:
                    audioArray.add(bgm_final05a);
                    audioArray.add(bgm_final05b);
                    textArray.add(jsonValue.getString("final05a"));
                    textArray.add(jsonValue.getString("final05b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    break;
                case 6:
                    audioArray.add(bgm_final06a);
                    audioArray.add(bgm_final06b);
                    textArray.add(jsonValue.getString("final06a"));
                    textArray.add(jsonValue.getString("final06b"));
                    imageArray.add(satanImage);
                    imageArray.add(sonImage);
                    imageArray.add(satanImage);
                    break;
                case 7:
                    audioArray.add(bgm_final07a);
                    audioArray.add(bgm_final07b);
                    textArray.add(jsonValue.getString("final07a"));
                    textArray.add(jsonValue.getString("final07b"));
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
                        preferences.putInteger("final", finalIndex + 1);
                        preferences.flush();
                
                        if (currentDialogAudio != null) currentDialogAudio.stop();
                        turn = Turn.PLAYER;
                
                        if (bgm_menu.isPlaying()) {
                            stage.addAction(new Action() {
                                @Override
                                public boolean act(float delta) {
                                    bgm_menu.setVolume(Utils.approach(bgm_menu.getVolume(), 0, .25f * delta));
                                    if (MathUtils.isZero(bgm_menu.getVolume())) {
                                        bgm_menu.stop();
                                        return true;
                                    } else return false;
                                }
                            });
                        }
                
                        if (!bgm_game.isPlaying()) {
                            bgm_game.play();
                            bgm_game.setPosition(bgm_menu.getPosition());
                            bgm_game.setVolume(0);
                            bgm_game.setLooping(true);
                            stage.addAction(new Action() {
                                @Override
                                public boolean act(float delta) {
                                    bgm_game.setVolume(Utils.approach(bgm_game.getVolume(), bgm, .25f * delta));
                                    return MathUtils.isEqual(bgm_game.getVolume(), bgm);
                                }
                            });
                        }
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
                
                        var typingLabel = new TypingLabel(textArray.get(progress) + "\n{NORMAL}Click to continue...", skin);
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
    
    private static final Vector2 temp = new Vector2();
    
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
        
        if (turn == Turn.PLAYER) {
            for (var ground : grounds) {
                var hex = hexUtils.pixelToGridHex(temp.set(ground.x, ground.y));
                var occupied = false;
                for (var character : characters) {
                    if (MathUtils.isEqual(character.x, ground.x) && MathUtils.isEqual(character.y, ground.y)) {
                        occupied = true;
                        break;
                    }
                }
                
                if (occupied) {
                    hex.weight = 100;
                } else {
                    hex.weight = 0;
                }
            }
            
            if (!player.destroy) player.takeTurn();
        } else if (turn == Turn.PLAYER_MOVING) {
            if (!player.destroy) {
                boolean done = player.completeMoving();
                if (done) for (var enemy : enemies) {
                    enemy.completeMoving();
                    if (enemy.moveTargetActivated) done = false;
                }
                if (done) {
                    if (skipEnemyTurn) {
                        skipEnemyTurn = false;
                        turn = Turn.PLAYER;
                    } else turn = Turn.ENEMY;
                }
            }
        } else if (turn == Turn.ENEMY) {
            for (int i = 0; i < enemies.size; i++) {
                var enemy = enemies.get(i);
                if (!enemy.destroy) enemy.takeTurn();
            }
            turn = Turn.ENEMY_MOVING;
        } else if (turn == Turn.ENEMY_MOVING) {
            boolean done = true;
            for (var enemy : enemies) {
                enemy.completeMoving();
                if (enemy.moveTargetActivated) done = false;
            }
            if (done) {
                turn = Turn.PLAYER;
                if (killedThisTurn) {
                    killStreak++;
                    if (killStreak >= 3) {
                        if (powers.contains(Power.BODY_OF_CHRIST, true)) {
                            health = Utils.approach(health, maxHealth, 1);
                            refreshHealthTable();
                        }
                        
                        if (powers.contains(Power.HOLY_TRINITY, true)) {
                            energy = maxEnergy;
                            refreshEnergyTable();
                            if (tridentEntity != null) {
                                tridentEntity.destroy = true;
                                tridentEntity = null;
                            }
                            hasTrident = true;
                            throwButton.setDisabled(false);
                        }
                        
                        if (powers.contains(Power.GODSPEED, true)) {
                            skipEnemyTurn = true;
                        }
                        
                        killStreak = 0;
                    }
                } else killStreak = 0;
                killedThisTurn = false;
            }
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
                var blessing = valuesMap.get("blessing1").asString();
                if (!blessing.equals("None")) blessings.add(blessing);
                blessing = valuesMap.get("blessing2").asString();
                if (!blessing.equals("None")) blessings.add(blessing);
                blessing = valuesMap.get("blessing3").asString();
                if (!blessing.equals("None")) blessings.add(blessing);
                blessing = valuesMap.get("blessing4").asString();
                if (!blessing.equals("None")) blessings.add(blessing);
                blessing = valuesMap.get("blessing5").asString();
                if (!blessing.equals("None")) blessings.add(blessing);
                
                shrineTitle = valuesMap.get("shrineTitle").asString();
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
                        nextLevel = valuesMap.get("nextRoom").asString();
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
                    case "shrine":
                        shrineEntity = new ShrineEntity();
                        shrineEntity.setPosition(x, y);
                        entityController.add(shrineEntity);
                        break;
                    case "grenade_dummy":
                        var grenadeDummyEntity = new GrenadeDummyEntity();
                        grenadeDummyEntity.setPosition(x, y);
                        entityController.add(grenadeDummyEntity);
                        characters.add(grenadeDummyEntity);
                        enemies.add(grenadeDummyEntity);
                        break;
                    case "glutton":
                        var glutton = new GluttonEntity();
                        glutton.setPosition(x, y);
                        entityController.add(glutton);
                        characters.add(glutton);
                        enemies.add(glutton);
                        break;
                    case "skeleton":
                        var skeleton = new SkeletonEntity();
                        skeleton.setPosition(x, y);
                        entityController.add(skeleton);
                        characters.add(skeleton);
                        enemies.add(skeleton);
                        break;
                    case "succubus":
                        var succubus = new SuccubusEntity();
                        succubus.setPosition(x, y);
                        entityController.add(succubus);
                        characters.add(succubus);
                        enemies.add(succubus);
                        break;
                    case "goat":
                        var goat = new GoatEntity();
                        goat.setPosition(x, y);
                        entityController.add(goat);
                        characters.add(goat);
                        enemies.add(goat);
                        break;
                    case "satan":
                        var satan = new SatanEntity();
                        satan.setPosition(x, y);
                        entityController.add(satan);
                        characters.add(satan);
                        enemies.add(satan);
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
    
    private void createStageElements() {
        strikeButton = new ImageButton(skin, "strike");
        dashButton = new ImageButton(skin, "dash");
        throwButton = new ImageButton(skin, "throw");
        waitButton = new ImageButton(skin, "wait");
        
        var root = (Table) stage.getRoot().getChild(0);
        root.left().bottom();
        
        root.defaults().left();
        healthTable = new Table();
        root.add(healthTable);
        refreshHealthTable();
        
        root.row();
        pipTable = new Table();
        root.add(pipTable);
        refreshEnergyTable();
        
        root.row();
        controlsTable = new Table();
        root.add(controlsTable);
        refreshControlsTable();
    }
    
    public static void refreshHealthTable() {
        healthTable.clear();
        
        for (int i = 0; i < maxHealth; i++) {
            var image = new Image(skin, i < health ? "heart" : "heart-disabled");
            healthTable.add(image);
        }
    }
    
    public static void refreshEnergyTable() {
        pipTable.clear();
        
        var stack = new Stack();
        pipTable.add(stack);
        
        var table = new Table();
        table.left();
        stack.add(table);
    
        for (int i = 0; i < maxEnergy; i += 2) {
            var image = new Image(skin, "pip-disabled-10");
            table.add(image);
        }
        
        table = new Table();
        table.left();
        stack.add(table);
        for (int i = 0; i < energy; i += 2) {
            var image = new Image(skin, "pip-10");
            table.add(image);
        }
        
        if (energy > 0 && energy % 2 != 0) {
            table.getCells().peek().height(15).bottom().expandY();
        }
        
        dashButton.setDisabled(energy < 2);
        strikeButton.setDisabled(energy < 1);
    }
    
    public static void refreshControlsTable() {
        controlsTable.clear();
        controlsTable.setBackground(skin.getDrawable("controls-bg"));
    
        controlsButtonGroup = new ButtonGroup<>();
        controlsButtonGroup.setMinCheckCount(0);
    
        controlsTable.add(strikeButton).size(50, 50);
        controlsButtonGroup.add(strikeButton);
        Utils.onChange(strikeButton, () -> {
            if (strikeButton.isChecked()) mode = Mode.STRIKE;
            else mode = Mode.MOVE;
        });
    
        controlsTable.add(dashButton).size(50, 50);
        controlsButtonGroup.add(dashButton);
        Utils.onChange(dashButton, () -> {
            if (dashButton.isChecked()) mode = Mode.DASH;
            else mode = Mode.MOVE;
        });
    
        controlsTable.add(throwButton).size(50, 50);
        controlsButtonGroup.add(throwButton);
        Utils.onChange(throwButton, () -> {
            if (throwButton.isChecked()) {
                if (hasTrident) mode = Mode.THROW;
                else {
                    hasTrident = true;
                    mode = Mode.WAIT;
                    turn = Turn.PLAYER_MOVING;
                    controlsButtonGroup.uncheckAll();
    
                    if (tridentEntity != null) {
                        var anim = new AnimationEntity(SpineTrident.skeletonData, SpineTrident.animationData, SpineTrident.animationAnimation, tridentEntity.x, tridentEntity.y);
                        anim.depth = DEPTH_PARTICLES;
                        entityController.add(anim);
                        tridentEntity.destroy = true;
                        tridentEntity = null;
                        
                        anim.moveTowardsTarget(600f, player.x, player.y);
                        anim.killOnCompletion = false;
                        stage.addAction(new Action() {
                            @Override
                            public boolean act(float delta) {
                                if (!anim.moveTargetActivated) {
                                    anim.destroy = true;
                                    return true;
                                } else return false;
                            }
                        });
                    }
                }
            } else mode = Mode.MOVE;
        });
    
        if (powers.contains(Power.PATIENCE_OF_JOB, true)) {
            controlsTable.add(waitButton).size(50, 50);
            controlsButtonGroup.add(waitButton);
            Utils.onChange(waitButton, () -> {
                if (waitButton.isChecked()) {
                    mode = Mode.WAIT;
                    turn = Turn.PLAYER_MOVING;
                    controlsButtonGroup.uncheckAll();
                } else mode = Mode.MOVE;
            });
        }
    }
}