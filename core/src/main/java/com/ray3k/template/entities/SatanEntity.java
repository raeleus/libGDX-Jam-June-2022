package com.ray3k.template.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Scaling;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.stripe.PopTable;
import com.ray3k.template.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineSatan.*;
import static com.ray3k.template.screens.GameScreen.*;

public class SatanEntity extends EnemyEntity {
    private static final Vector2 temp = new Vector2();
    public float health = 5;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        depth = DEPTH_ENEMY;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
    
    }
    
    @Override
    public void draw(float delta) {
    
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
    
    }
    
    @Override
    public void colorIntentTiles() {
        var hex = hexUtils.pixelToGridHex(temp.set(x, y));
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q + delta;
            var r = hex.r;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q - delta;
            var r = hex.r;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q;
            var r = hex.r + delta;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q;
            var r = hex.r - delta;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q - delta;
            var r = hex.r + delta;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
        
        for (int delta = 1; delta <= 5; delta++) {
            var q = hex.q + delta;
            var r = hex.r - delta;
            var s = -q-r;
            var targetHex = hexUtils.getTile(q, r, s);
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    }
    
    @Override
    public void takeTurn() {
        var pathHead = hexUtils.pixelToGridHex(temp.set(x, y));
        var pathTail = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
    
        float distance = Integer.MAX_VALUE;
        if (pathHead.r == pathTail.r || pathHead.q == pathTail.q || pathHead.s == pathTail.s) {
            distance = pathHead.distance(pathTail);
        }
        
        if (distance > 5) {
            if (MathUtils.randomBoolean()) {
                if (pathTail.userObject instanceof GroundEntity) {
                    var path = hexUtils.getPath(pathTail, pathHead);
                    if (path.get(pathHead) != null) {
                        HexTile targetHex = path.get(pathHead);
                        var ground = (GroundEntity) targetHex.userObject;
                        if (targetHex.weight == 0) {
                            stage.addAction(Actions.delay(.25f, Actions.run(() -> {
                                moveTowardsTarget(300f, ground.x, ground.y);
                            })));
                            pathHead.weight = 0;
                            targetHex.weight = 100;
                        }
                    }
                }
            } else {
                var ground = grounds.random();
                var spawn = true;
                if (MathUtils.isEqual(player.x, ground.x) && MathUtils.isEqual(player.y, ground.y)) spawn = false;
                var hex = hexUtils.pixelToGridHex(temp.set(ground.x, ground.y));
                var playerHex = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
                if (hex.distance(playerHex) < 3) spawn = false;
                for (int i = 0; i < enemies.size; i++) {
                    var enemy = enemies.get(i);
                    if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                        spawn = false;
                        break;
                    }
                }
                if (spawn) {
                    var glutton = new GluttonEntity();
                    glutton.setPosition(ground.x, ground.y);
                    entityController.add(glutton);
                    characters.add(glutton);
                    enemies.add(glutton);
                }
            }
        } else {
            sfx_gameExplosion.play(sfx);
            player.hurt();
    
            var orb = new OrbEntity();
            entityController.add(orb);
            orb.skeleton.getRootBone().setRotation(Utils.pointDirection(this, player));
            orb.setPosition(x, y);
            orb.moveTowardsTarget(600.0f, player.x, player.y);
            stage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    if (!orb.moveTargetActivated) {
                        orb.destroy = true;
                        return true;
                    } else return false;
                }
            });
        }
    }
    
    @Override
    public void completeMoving() {
    
    }
    
    public Music currentDialogAudio;
    
    @Override
    public void hurt() {
        health--;
//        health -= 10;
        if (health != 2) {
            sfx_gameSatanHurt.play(sfx);
        } else {
            Array<Music> musics = new Array<>(new Music[] {bgm_second01, bgm_second02, bgm_second03, bgm_second04, bgm_second05});
            var music = musics.random();
            music.setVolume(sfx);
            music.play();
        }
        if (health <= 0) {
            stage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    bgm_game.setVolume(Utils.approach(bgm_game.getVolume(), 0, .25f * delta));
                    if (MathUtils.isZero(bgm_game.getVolume())) {
                        bgm_game.stop();
                        return true;
                    } else return false;
                }
            });
            
            turn = Turn.STORY;
            
            var audioArray = new Array<Music>();
            var textArray = new Array<String>();
            var imageArray = new Array<Image>();
            var jsonReader = new JsonReader();
            var jsonValue = jsonReader.parse(Gdx.files.internal("dialogs.json"));
            var satanImage = new Image(skin, "portrait-satan");
            satanImage.setScaling(Scaling.fit);
            var sonImage = new Image(skin, "portrait-son");
            sonImage.setScaling(Scaling.fit);
            
            audioArray.add(bgm_ending01a);
            audioArray.add(bgm_ending01b);
            audioArray.add(bgm_ending01c);
            audioArray.add(bgm_ending01d);
            audioArray.add(bgm_ending01e);
            audioArray.add(bgm_ending01f);
            audioArray.add(bgm_ending01g);
            audioArray.add(bgm_ending01h);
            textArray.add(jsonValue.getString("ending01a"));
            textArray.add(jsonValue.getString("ending01b"));
            textArray.add(jsonValue.getString("ending01c"));
            textArray.add(jsonValue.getString("ending01d"));
            textArray.add(jsonValue.getString("ending01e"));
            textArray.add(jsonValue.getString("ending01f"));
            textArray.add(jsonValue.getString("ending01g"));
            textArray.add(jsonValue.getString("ending01h"));
            imageArray.add(satanImage);
            imageArray.add(sonImage);
            imageArray.add(satanImage);
            imageArray.add(sonImage);
            imageArray.add(satanImage);
            imageArray.add(sonImage);
            imageArray.add(satanImage);
            imageArray.add(sonImage);
            
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
                
                        if (currentDialogAudio != null) currentDialogAudio.stop();
                        animationState.setAnimation(0, animationDie, false);
                        stage.addAction(Actions.delay(5, Actions.run(() -> {
                            core.transition(new HeavenScreen());
                        })));
                    } else {
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
}
