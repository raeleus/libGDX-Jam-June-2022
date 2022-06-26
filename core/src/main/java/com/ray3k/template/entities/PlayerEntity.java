package com.ray3k.template.entities;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.lol.fraud.HexTile;
import com.ray3k.stripe.PopTable;
import com.ray3k.template.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpinePlayer.*;
import static com.ray3k.template.screens.GameScreen.*;

public class PlayerEntity extends Entity {
    private Array<EnemyEntity> thrustEnemies = new Array<>();
    private Array<EnemyEntity> slashEnemies = new Array<>();
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        depth = DEPTH_PLAYER;
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
    
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
    
    }
    
    Vector2 temp = new Vector2();
    
    public void takeTurn() {
        thrustEnemies.clear();
        slashEnemies.clear();
        
        switch (mode) {
            case MOVE:
                moveTurn();
                break;
            case STRIKE:
                strikeTurn();
                break;
            case DASH:
                dashTurn();
                break;
            case THROW:
                throwTurn();
                break;
        }
    }
    
    private void moveTurn() {
        temp.set(player.x, player.y);
        var pathHead = hexUtils.pixelToGridHex(temp);
        temp.set(mouseX, mouseY);
        var pathTail = hexUtils.pixelToGridHex(temp);
        if (pathTail != null && pathTail.weight == 0) {
            var obj = pathTail.userObject;
            if (obj instanceof GroundEntity) {
                var path = hexUtils.getPath(pathTail, pathHead);
                
                if (path.get(pathHead) != null) {
                    HexTile targetHex = path.get(pathHead);
                    if (targetHex.weight == 0) {
                        var ground = (GroundEntity) targetHex.userObject;
                        ground.skeleton.setColor(Color.GREEN);
                        
                        //check for thrust
                        var thrustQ = pathHead.q + (targetHex.q - pathHead.q) * 2;
                        var thrustR = pathHead.r + (targetHex.r - pathHead.r) * 2;
                        var thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ - thrustR);
                        if (thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                            var thrustGround = (GroundEntity) thrustHex.userObject;
                            for (var enemy : enemies) {
                                if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                        enemy.y) && !(enemy instanceof GrenadeDummyEntity)) {
                                    thrustGround.skeleton.setColor(Color.RED);
                                    thrustEnemies.add(enemy);
                                    break;
                                }
                            }
                        }
                        
                        //check for slash
                        var adjacentHexes = hexUtils.getHexesInRadius(pathHead, 1);
                        var slashHexes = hexUtils.getHexesInRadius(targetHex, 1);
                        var iter = slashHexes.iterator();
                        while (iter.hasNext()) {
                            var hex = iter.next();
                            if (!adjacentHexes.contains(hex)) iter.remove();
                        }
                        for (var slashHex : slashHexes) {
                            if (slashHex != null && slashHex.userObject instanceof GroundEntity) {
                                var slashGround = (GroundEntity) slashHex.userObject;
                                for (var enemy : enemies) {
                                    if (MathUtils.isEqual(slashGround.x, enemy.x) && MathUtils.isEqual(slashGround.y,
                                            enemy.y) && !(enemy instanceof GrenadeDummyEntity)) {
                                        slashGround.skeleton.setColor(Color.RED);
                                        slashEnemies.add(enemy);
                                        break;
                                    }
                                }
                            }
                        }
                        
                        if (isButtonJustPressed(Buttons.LEFT)) {
//                            System.out.println("pathHead = " + pathHead.q + " " + pathHead.r + " " + pathHead.s);
//                            System.out.println("current = " + current.q + " " + current.r + " " + current.s);
                            
                            player.moveTowardsTarget(300f, ground.x, ground.y);
                            sfx_gameWalk.play(sfx);
                            pathHead.weight = 0;
                            targetHex.weight = 100;
                            turn = Turn.PLAYER_MOVING;
                        }
                        //                        for (int i = 1; i < path.size() && current != pathTail; i++) {
                        //                            current = path.get(current);
                        //                        }
                    }
                }
            }
        }
    }
    
    private void strikeTurn() {
        var playerHex = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
        var adjacentHexes = hexUtils.getHexesInRadius(playerHex, 1);

        var iter = adjacentHexes.iterator();
        outerLoop : while (iter.hasNext()) {
            var hex = iter.next();
            if (hex != null && hex.userObject instanceof GroundEntity) {
                var ground = (GroundEntity) hex.userObject;
                for (var enemy : enemies) {
                    if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                        ground.skeleton.setColor(Color.RED);
                        break outerLoop;
                    }
                }
                iter.remove();
            } else {
                iter.remove();
            }
        }
    
        var targetHex = hexUtils.pixelToGridHex(temp.set(mouseX, mouseY));
        if (targetHex != null && targetHex.userObject instanceof GroundEntity) {
            if (adjacentHexes.contains(targetHex) && isButtonJustPressed(Buttons.LEFT)) {
                sfx_gameSlash.play(sfx * .5f);
                turn = Turn.PLAYER_MOVING;
                controlsButtonGroup.uncheckAll();
                
                var q = targetHex.q;
                var r = targetHex.r;
                var deltaQ = MathUtils.clamp(targetHex.q - playerHex.q, -1, 1);
                var deltaR = MathUtils.clamp(targetHex.r - playerHex.r, -1, 1);
                
                energy--;
                refreshEnergyTable();
                
                EnemyEntity enemy = null;
                var ground = (GroundEntity) targetHex.userObject;
                for (var enemyCheck : enemies) {
                    if (MathUtils.isEqual(enemyCheck.x, ground.x) && MathUtils.isEqual(enemyCheck.y, ground.y)) {
                        enemy = enemyCheck;
                        break;
                    }
                }
                moveEnemy(enemy, q + deltaQ, r + deltaR);
            }
        }
    }
    
    private void moveEnemy(EnemyEntity enemy, int q, int r) {
        if (enemy != null) {
            var startHex = hexUtils.pixelToGridHex(temp.set(enemy.x, enemy.y));
            var targetHex = hexUtils.getTile(q, r, -q - r);
            if (targetHex != null && targetHex.userObject instanceof GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                
                EnemyEntity nextEnemy = null;
                for (var enemyCheck : enemies) {
                    if (MathUtils.isEqual(enemyCheck.x, ground.x) && MathUtils.isEqual(enemyCheck.y, ground.y)) {
                        nextEnemy = enemyCheck;
                        break;
                    }
                }
    
                enemy.moveTowardsTarget(300f, ground.x, ground.y);
                startHex.weight = 0;
                targetHex.weight = 100;
                
                if (nextEnemy != null) {
                    var deltaQ = MathUtils.clamp(targetHex.q - startHex.q, -1, 1);
                    var deltaR = MathUtils.clamp(targetHex.r - startHex.r, -1, 1);
                    moveEnemy(nextEnemy, q + deltaQ, r + deltaR);
                }
            } else {
                hexUtils.hexToPixel(targetHex, temp);
                enemy.setPosition(temp.x, temp.y);
                enemy.destroy = true;
                sfx_gameBurn.play(sfx);
                hexUtils.hexToPixel(targetHex, temp);
                var anim = new AnimationEntity(SpineFlame.skeletonData, SpineFlame.animationData,
                        SpineFlame.animationAnimation, temp.x, temp.y);
                anim.animationState.getCurrent(0).setTimeScale(MathUtils.random(.5f, 1.5f));
                anim.animationState.setAnimation(1, SpineFlame.animationFlames, true);
                anim.animationState.getCurrent(1).setTrackTime(1.0f);
                anim.depth = DEPTH_PARTICLES;
                entityController.add(anim);
            }
        }
    }
    
    private void dashTurn() {
        var playerHex = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
        var adjacentHexes = hexUtils.getHexesInRadius(playerHex, 1);
        var dashHexes = hexUtils.getHexesInRadius(playerHex, 2);
        dashHexes.removeAll(adjacentHexes);
        var iter = dashHexes.iterator();
        while (iter.hasNext()) {
            var hex = iter.next();
            if (hex.weight > 0) iter.remove();
        }
        
        for (var hex : dashHexes) {
            if (hex.userObject instanceof GroundEntity) {
                var ground = (GroundEntity) hex.userObject;
                ground.skeleton.setColor(Color.GREEN);
            }
        }
        
        var targetHex = hexUtils.pixelToGridHex(temp.set(mouseX, mouseY));
        if (targetHex != null && targetHex.userObject instanceof GroundEntity) {
            var ground = (GroundEntity) targetHex.userObject;
            if (dashHexes.contains(targetHex)) {
                var q = playerHex.q;
                var r = playerHex.r;
                var deltaQ = MathUtils.clamp(targetHex.q - playerHex.q, -1, 1);
                var deltaR = MathUtils.clamp(targetHex.r - playerHex.r, -1, 1);
    
                //check for thrust
                if (deltaQ != 0 || deltaR != 0) {
                    var thrustQ = playerHex.q + deltaQ * 3;
                    var thrustR = playerHex.r + deltaR * 3;
                    var thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ - thrustR);
                    if (thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                        var thrustGround = (GroundEntity) thrustHex.userObject;
                        for (var enemy : enemies) {
                            if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                    enemy.y) && !(enemy instanceof GrenadeDummyEntity)) {
                                thrustGround.skeleton.setColor(Color.RED);
                                thrustEnemies.add(enemy);
                                break;
                            }
                        }
                    }
                }
    
                //check for slash
                var slashHexes = hexUtils.getHexesInRadius(targetHex, 1);
                iter = slashHexes.iterator();
                while (iter.hasNext()) {
                    var hex = iter.next();
                    if (!adjacentHexes.contains(hex)) iter.remove();
                }
                for (var slashHex : slashHexes) {
                    if (slashHex != null && slashHex.userObject instanceof GroundEntity) {
                        var slashGround = (GroundEntity) slashHex.userObject;
                        for (var enemy : enemies) {
                            if (MathUtils.isEqual(slashGround.x, enemy.x) && MathUtils.isEqual(slashGround.y,
                                    enemy.y) && !(enemy instanceof GrenadeDummyEntity)) {
                                slashGround.skeleton.setColor(Color.RED);
                                slashEnemies.add(enemy);
                                break;
                            }
                        }
                    }
                }
    
                if (isButtonJustPressed(Buttons.LEFT)) {
                    energy -= 2;
                    refreshEnergyTable();
                    player.moveTowardsTarget(600f, ground.x, ground.y);
                    sfx_gameDash.play(sfx);
                    playerHex.weight = 0;
                    targetHex.weight = 100;
                    turn = Turn.PLAYER_MOVING;
                    controlsButtonGroup.uncheckAll();
                    
                    var flameX = player.x;
                    var flameY = player.y;
                    
                    temp.set(ground.x,ground.y).sub(flameX, flameY).setLength(50);
                    do {
                        var anim = new AnimationEntity(SpineFlame.skeletonData, SpineFlame.animationData,
                                    SpineFlame.animationAnimation, flameX, flameY);
                        anim.animationState.getCurrent(0).setTimeScale(MathUtils.random(.5f, 1.5f));
                        anim.animationState.setAnimation(1, SpineFlame.animationFlames, true);
                        anim.animationState.getCurrent(1).setTrackTime(1.0f);
                        anim.depth = DEPTH_PARTICLES;
                        entityController.add(anim);
                        flameX = Utils.approach(flameX, ground.x, temp.x);
                        flameY = Utils.approach(flameY, ground.y, temp.y);
                        
                    } while (!MathUtils.isEqual(flameX, ground.x) || !MathUtils.isEqual(flameY, ground.y));
                }
            }
        }
    }
    
    private void throwTurn() {
    
    }
    
    public void completeMoving() {
        if (!moveTargetActivated) {
            if (thrustEnemies.size > 0 || slashEnemies.size > 0) {
                sfx_gameSlash.play(sfx * .5f);
                
                if (thrustEnemies.size > 0) {
                    animationState.setAnimation(0, animationTrident, false);
                } else if (slashEnemies.size > 0) {
                    animationState.setAnimation(0, animationSlash, false);
                }
                animationState.addAnimation(0, animationAnimation, true, 0);
                
                for (var enemy : thrustEnemies) {
                    enemy.hurt();
                }
                thrustEnemies.clear();
                
                for (var enemy : slashEnemies) {
                    enemy.hurt();
                }
                slashEnemies.clear();
            }
            
            turn = Turn.ENEMY;
        }
        
        if (pentagramEntity != null && MathUtils.isEqual(x, pentagramEntity.x) && MathUtils.isEqual(y, pentagramEntity.y)) {
            destroy = true;
            sfx_gameTeleport.play(sfx);
            var anim = new AnimationEntity(skeletonData, animationData, animationTeleport, x, y) {
                @Override
                public void destroy() {
                    super.destroy();
                    stage.addAction(Actions.delay(2.0f, Actions.run(() -> {
                        String level = "home";
                        var tutorial = preferences.getInteger("tutorial", 1);
                        if (tutorial <= 5) {
                            tutorial++;
                            preferences.putInteger("tutorial", tutorial);
                            preferences.flush();
                            level = "tutorial" + Utils.intToTwoDigit(tutorial);
                        }
                        core.transition(new GameScreen(level));
                    })));
                }
            };
            entityController.add(anim);
        }
    
        if (shrineEntity != null && MathUtils.isEqual(x, shrineEntity.x) && MathUtils.isEqual(y, shrineEntity.y)) {
            shrineEntity.destroy = true;
            shrineEntity = null;
            var pop = new PopTable();
            
            var table = new Table();
            table.setBackground(skin.getDrawable("ability-bar-10"));
            pop.add(table);
            
            var label = new Label(shrineTitle, skin, "title");
            table.add(label);
            
            pop.row();
            table = new Table();
            table.setBackground(skin.getDrawable("ability-window-10"));
            pop.add(table);
            
            var scrollTable = new Table();
            var scroll = new ScrollPane(scrollTable, skin);
            scroll.setFadeScrollBars(false);
            table.add(scroll).grow();
            
            for (var blessing : blessings) {
                scrollTable.row();
                var button = new Button(skin);
                button.left();
                var image = new Image();
                button.add(image);
                var descriptionLabel = new Label("", skin);
                switch (blessing) {
                    case "Holy Blessing":
                        image.setDrawable(skin, "icon-blessing");
                        descriptionLabel.setText("Restore all health");
                        break;
                    case "Blood of Christ":
                        image.setDrawable(skin, "icon-blood");
                        descriptionLabel.setText("+1 max health");
                        break;
                    case "Strength of Samson":
                        image.setDrawable(skin, "icon-strike");
                        descriptionLabel.setText("+1 strike pushback");
                        break;
                    case "Blood of the Lamb":
                        image.setDrawable(skin, "icon-lamb");
                        descriptionLabel.setText("+1 energy pips");
                        break;
                    case "Parting the Red Sea":
                        image.setDrawable(skin, "icon-parting");
                        descriptionLabel.setText("Strike affects enemies to the side as well");
                        break;
                    case "Patience of Job":
                        image.setDrawable(skin, "icon-patience");
                        descriptionLabel.setText("Ability to wait a turn");
                        break;
                    case "Faith of David":
                        image.setDrawable(skin, "icon-david");
                        descriptionLabel.setText("+1 trident throw range");
                        break;
                    case "Body of Christ":
                        image.setDrawable(skin, "icon-body");
                        descriptionLabel.setText("Restore 1 health after 3 consecutive kills");
                        break;
                    case "Lance of Longinus":
                        image.setDrawable(skin, "icon-lance");
                        descriptionLabel.setText("Thrust penetrates to the next tile");
                        break;
                    case "Wings of Michael":
                        image.setDrawable(skin, "icon-wings");
                        descriptionLabel.setText("+1 dash distance");
                        break;
                    case "Holy Trinity":
                        image.setDrawable(skin, "icon-trinity");
                        descriptionLabel.setText("Restore trident, strike, and energy");
                        break;
                    case "Godspeed":
                        image.setDrawable(skin, "icon-godspeed");
                        descriptionLabel.setText("Additional action after 3 consecutive kills");
                        break;
                    case "Crown of Thorns":
                        image.setDrawable(skin, "icon-crown");
                        descriptionLabel.setText("Attacking enemies receive damage");
                        break;
                    case "Holy Light":
                        image.setDrawable(skin, "icon-holy-light");
                        descriptionLabel.setText("Shield while striking");
                        break;
                    case "Noah's Dove":
                        image.setDrawable(skin, "icon-dove");
                        descriptionLabel.setText("Recall trident to your hand");
                        break;
                    case "Mark of the Beast":
                        image.setDrawable(skin, "icon-beast");
                        descriptionLabel.setText("Stun surrounding enemies after landing a dash");
                        break;
                    case "Holy Grail":
                        image.setDrawable(skin, "icon-grail");
                        descriptionLabel.setText("Survive death once");
                        break;
                    case "Arc of the Covenant":
                        image.setDrawable(skin, "icon-arc");
                        descriptionLabel.setText("Use the raw power of God instead of the trident");
                        break;
                    case "Christ the Redeemer":
                        image.setDrawable(skin, "icon-redeemer");
                        descriptionLabel.setText("Restore 1 health every level");
                        break;
                }
                table = new Table();
                button.add(table);
                
                table.defaults().left();
                var buttonLabel = new Label(blessing, skin, "title");
                table.add(buttonLabel);
                
                table.row();
                table.add(descriptionLabel);
                
                button.addListener(new ClickListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event,x, y, pointer, fromActor);
                        button.setColor(Color.RED);
                        buttonLabel.setColor(Color.RED);
                        image.setColor(Color.RED);
                        descriptionLabel.setColor(Color.RED);
                    }
    
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        super.exit(event,x, y, pointer, toActor);
                        button.setColor(Color.WHITE);
                        buttonLabel.setColor(Color.WHITE);
                        image.setColor(Color.WHITE);
                        descriptionLabel.setColor(Color.WHITE);
                    }
    
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        pop.hide();
    
                        switch (blessing) {
                            case "Holy Blessing":
                                health = maxHealth;
                                refreshHealthTable();
                                break;
                            case "Blood of Christ":
                                health++;
                                maxHealth++;
                                refreshHealthTable();
                                break;
                            case "Strength of Samson":
                                break;
                            case "Blood of the Lamb":
                                break;
                            case "Parting the Red Sea":
                                break;
                            case "Patience of Job":
                                break;
                            case "Faith of David":
                                break;
                            case "Body of Christ":
                                break;
                            case "Lance of Longinus":
                                break;
                            case "Wings of Michael":
                                break;
                            case "Holy Trinity":
                                break;
                            case "Godspeed":
                                break;
                            case "Crown of Thorns":
                                break;
                            case "Holy Light":
                                break;
                            case "Noah's Dove":
                                break;
                            case "Mark of the Beast":
                                break;
                            case "Holy Grail":
                                break;
                            case "Arc of the Covenant":
                                break;
                            case "Christ the Redeemer":
                                break;
                        }
                    }
                });
                scrollTable.add(button);
            }
            pop.show(stage);
        }
    }
}
