package com.ray3k.template.entities;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
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

import java.util.ArrayList;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpinePlayer.*;
import static com.ray3k.template.screens.GameScreen.*;

public class PlayerEntity extends Entity {
    private Array<EnemyEntity> thrustEnemies = new Array<>();
    private Array<EnemyEntity> slashEnemies = new Array<>();
    private boolean shielding = false;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationSpawn, true);
        animationState.addAnimation(0, animationAnimation, true, 0);
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
        shielding = false;
        animationState.setAnimation(1, animationDeshield, false);
        
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
                        if (hasTrident && thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                            var thrustGround = (GroundEntity) thrustHex.userObject;
                            for (var enemy : enemies) {
                                if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                        enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
                                    thrustGround.skeleton.setColor(Color.RED);
                                    thrustEnemies.add(enemy);
                                    break;
                                }
                            }
                        }
                        
                        if (thrustEnemies.size > 0 && powers.contains(Power.LANCE_OF_LONGINUS, true)) {
                            thrustQ = pathHead.q + (targetHex.q - pathHead.q) * 3;
                            thrustR = pathHead.r + (targetHex.r - pathHead.r) * 3;
                            thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ - thrustR);
                            if (hasTrident && thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                                var thrustGround = (GroundEntity) thrustHex.userObject;
                                for (var enemy : enemies) {
                                    if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                            enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
                                        thrustGround.skeleton.setColor(Color.RED);
                                        thrustEnemies.add(enemy);
                                        break;
                                    }
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
                                            enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
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
                        continue outerLoop;
                    }
                }
                ground.skeleton.setColor(Color.BLUE);
            } else iter.remove();
        }
    
        var targetHexes = new ArrayList<HexTile>();
        var targetHex = hexUtils.pixelToGridHex(temp.set(mouseX, mouseY));
        targetHexes.add(targetHex);
        if (powers.contains(Power.PARTING_THE_RED_SEA, true) && targetHex != null) targetHexes.addAll(hexUtils.getHexesInRadius(targetHex, 1));
        
        for (var hex : targetHexes) {
            if (hex != null && hex.userObject instanceof GroundEntity) {
                if (adjacentHexes.contains(hex) && isButtonJustPressed(Buttons.LEFT)) {
                    sfx_gameSlash.play(sfx * .1f);
                    turn = Turn.PLAYER_MOVING;
                    controlsButtonGroup.uncheckAll();
                    
                    animationState.setAnimation(0, animationSmack, false);
                    animationState.addAnimation(0, animationAnimation, true, 0);
                    if (powers.contains(Power.HOLY_LIGHT, true)) {
                        animationState.setAnimation(1, animationShield, false);
                        shielding = true;
                    }
            
                    var q = hex.q;
                    var r = hex.r;
                    var deltaQ = MathUtils.clamp(hex.q - playerHex.q, -1, 1);
                    var deltaR = MathUtils.clamp(hex.r - playerHex.r, -1, 1);
            
                    energy--;
                    refreshEnergyTable();
            
                    EnemyEntity enemy = null;
                    var ground = (GroundEntity) hex.userObject;
                    for (var enemyCheck : enemies) {
                        if (MathUtils.isEqual(enemyCheck.x, ground.x) && MathUtils.isEqual(enemyCheck.y, ground.y)) {
                            enemy = enemyCheck;
                            break;
                        }
                    }
                    moveEnemy(enemy, q + deltaQ, r + deltaR);
                    var finalEnemy = enemy;
                    if (powers.contains(Power.STRENGTH_OF_SAMSON, true)) {
                        stage.addAction(Actions.delay(.5f, Actions.run(() -> {
                            moveEnemy(finalEnemy, q + 2 * deltaQ, r + 2 * deltaR);
                        })));
                    }
                }
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
                var ghost = new AnimationEntity(enemy.skeleton.getData(), enemy.animationState.getData(),
                        enemy.animationState.getCurrent(0).getAnimation(), enemy.x, enemy.y);
                hexUtils.hexToPixel(targetHex, temp);
                
                ghost.moveTowardsTarget(300.0f, temp.x, temp.y);
                entityController.add(ghost);
                stage.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (!ghost.moveTargetActivated) {
                            ghost.destroy = true;
                            sfx_gameBurn.play(sfx);
    
                            var anim = new AnimationEntity(SpineFlame.skeletonData, SpineFlame.animationData,
                                    SpineFlame.animationAnimation, ghost.x, ghost.y);
                            anim.animationState.getCurrent(0).setTimeScale(MathUtils.random(.5f, 1.5f));
                            anim.animationState.setAnimation(1, SpineFlame.animationFlames, true);
                            anim.animationState.getCurrent(1).setTrackTime(1.0f);
                            anim.depth = DEPTH_PARTICLES;
                            entityController.add(anim);
                            return true;
                        }
                        return false;
                    }
                });
                
                enemy.setPosition(temp.x, temp.y);
                enemy.destroy = true;
            }
        }
    }
    
    private void dashTurn() {
        var playerHex = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
        var adjacentHexes = hexUtils.getHexesInRadius(playerHex, 1);
        var dashHexes = hexUtils.getHexesInRadius(playerHex, powers.contains(Power.WINGS_OF_MICHAEL, true) ? 3 : 2);
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
                var deltaQ = MathUtils.clamp(targetHex.q - q, -1, 1);
                var deltaR = MathUtils.clamp(targetHex.r - r, -1, 1);
    
                //check for thrust
                if (hasTrident && (deltaQ != 0 || deltaR != 0)) {
                    var thrustQ = targetHex.q + deltaQ;
                    var thrustR = targetHex.r + deltaR;
                    var thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ - thrustR);
                    if (thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                        var thrustGround = (GroundEntity) thrustHex.userObject;
                        for (var enemy : enemies) {
                            if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                    enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
                                thrustGround.skeleton.setColor(Color.RED);
                                thrustEnemies.add(enemy);
                                break;
                            }
                        }
                    }
    
                    if (thrustEnemies.size > 0 && powers.contains(Power.LANCE_OF_LONGINUS, true)) {
                        thrustQ = targetHex.q + deltaQ * 2;
                        thrustR = targetHex.r + deltaR * 2;
                        thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ - thrustR);
                        if (thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                            var thrustGround = (GroundEntity) thrustHex.userObject;
                            for (var enemy : enemies) {
                                if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y,
                                        enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
                                    thrustGround.skeleton.setColor(Color.RED);
                                    thrustEnemies.add(enemy);
                                    break;
                                }
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
                                    enemy.y) && !(enemy instanceof GrenadeDummyEntity) && !(enemy instanceof  GrenadeEntity)) {
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
        var playerHex = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
        var throwHexes = hexUtils.getHexesInRadius(playerHex, powers.contains(Power.FAITH_OF_DAVID, true) ? 3 : 2);
        var iter = throwHexes.iterator();
        outerLoop : while (iter.hasNext()) {
            var hex = iter.next();
            if (hex != null && hex.userObject instanceof GroundEntity) {
                var ground = (GroundEntity) hex.userObject;
                if (hex.weight > 0) {
                    for (var enemy : enemies) {
                        if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                            ground.skeleton.setColor(Color.RED);
                            continue outerLoop;
                        }
                    }
                    iter.remove();
                } else {
                    ground.skeleton.setColor(Color.BLUE);
                }
            } else iter.remove();
        }
    
        var targetHex = hexUtils.pixelToGridHex(temp.set(mouseX, mouseY));
        if (targetHex != null && targetHex.userObject instanceof GroundEntity) {
            var ground = (GroundEntity) targetHex.userObject;
            if (throwHexes.contains(targetHex)) {
                if (isButtonJustPressed(Buttons.LEFT)) {
                    sfx_gameDash.play(sfx);
                    turn = Turn.PLAYER_MOVING;
                    controlsButtonGroup.uncheckAll();
                    hasTrident = false;
                    if (!powers.contains(Power.NOAHS_DOVE, true)) throwButton.setDisabled(true);
                    
                    tridentEntity = new TridentEntity();
                    tridentEntity.setPosition(player.x, player.y);
                    entityController.add(tridentEntity);
                    tridentEntity.skeleton.getRootBone().setRotation(Utils.pointDirection(player, ground));
                    tridentEntity.moveTowardsTarget(600f, ground.x, ground.y);
                    
                    for (var enemy : enemies) {
                        if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                            enemy.hurt();
                            sfx_gameSlash.play(sfx * .1f);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void completeMoving() {
        if (!moveTargetActivated) {
            if (thrustEnemies.size > 0 || slashEnemies.size > 0) {
                sfx_gameSlash.play(sfx * .1f);
                
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
                    stage.addAction(Actions.delay(2.0f, Actions.run(() -> core.transition(new GameScreen(nextLevel)))));
                }
            };
            entityController.add(anim);
            
            if (tridentEntity != null) {
                tridentEntity.moveTowardsTarget(600f, player.x, player.y);
                stage.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (!tridentEntity.moveTargetActivated) {
                            tridentEntity.destroy = true;
                        }
                        return false;
                    }
                });
            }
        }
    
        if (tridentEntity != null && MathUtils.isEqual(x, tridentEntity.x) && MathUtils.isEqual(y, tridentEntity.y)) {
            tridentEntity.destroy = true;
            tridentEntity = null;
            hasTrident = true;
            throwButton.setDisabled(false);
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
                        if (powers.contains(Power.STRENGTH_OF_SAMSON, true)) button = null;
                        break;
                    case "Blood of the Lamb":
                        image.setDrawable(skin, "icon-lamb");
                        descriptionLabel.setText("+1 energy pips\nCOSTS 1 MAX HEALTH");
                        if (health <= 1) button.setDisabled(true);
                        if (powers.contains(Power.BLOOD_OF_THE_LAMB, true)) button = null;
                        break;
                    case "Parting the Red Sea":
                        image.setDrawable(skin, "icon-parting");
                        descriptionLabel.setText("Strike affects enemies to the side as well");
                        if (powers.contains(Power.PARTING_THE_RED_SEA, true)) button = null;
                        break;
                    case "Patience of Job":
                        image.setDrawable(skin, "icon-patience");
                        descriptionLabel.setText("Ability to wait a turn");
                        if (powers.contains(Power.PATIENCE_OF_JOB, true)) button = null;
                        break;
                    case "Faith of David":
                        image.setDrawable(skin, "icon-david");
                        descriptionLabel.setText("+1 trident throw range");
                        if (powers.contains(Power.FAITH_OF_DAVID, true)) button = null;
                        break;
                    case "Body of Christ":
                        image.setDrawable(skin, "icon-body");
                        descriptionLabel.setText("Restore 1 health after 3 consecutive kills\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.BODY_OF_CHRIST, true)) button = null;
                        break;
                    case "Lance of Longinus":
                        image.setDrawable(skin, "icon-lance");
                        descriptionLabel.setText("Thrust penetrates to the next tile");
                        if (powers.contains(Power.LANCE_OF_LONGINUS, true)) button = null;
                        break;
                    case "Wings of Michael":
                        image.setDrawable(skin, "icon-wings");
                        descriptionLabel.setText("+1 dash distance");
                        if (powers.contains(Power.WINGS_OF_MICHAEL, true)) button = null;
                        break;
                    case "Holy Trinity":
                        image.setDrawable(skin, "icon-trinity");
                        descriptionLabel.setText("Restore trident, strike, and energy\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.HOLY_TRINITY, true)) button = null;
                        break;
                    case "Godspeed":
                        image.setDrawable(skin, "icon-godspeed");
                        descriptionLabel.setText("Additional action after 3 consecutive kills\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.GODSPEED, true)) button = null;
                        break;
                    case "Crown of Thorns":
                        image.setDrawable(skin, "icon-crown");
                        descriptionLabel.setText("Attacking enemies receive damage");
                        if (powers.contains(Power.CROWN_OF_THORNS, true)) button = null;
                        break;
                    case "Holy Light":
                        image.setDrawable(skin, "icon-holy-light");
                        descriptionLabel.setText("Block 1 attack after striking\nCOSTS 1 MAX HEALTH");
                        if (health <= 1) button.setDisabled(true);
                        if (powers.contains(Power.HOLY_LIGHT, true)) button = null;
                        break;
                    case "Noah's Dove":
                        image.setDrawable(skin, "icon-dove");
                        descriptionLabel.setText("Recall trident to your hand\nCOSTS 1 MAX HEALTH");
                        if (health <= 1) button.setDisabled(true);
                        if (powers.contains(Power.NOAHS_DOVE, true)) button = null;
                        break;
                    case "Mark of the Beast":
                        image.setDrawable(skin, "icon-beast");
                        descriptionLabel.setText("Stun surrounding enemies after landing a dash\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.MARK_OF_THE_BEAST, true)) button = null;
                        break;
                    case "Holy Grail":
                        image.setDrawable(skin, "icon-grail");
                        descriptionLabel.setText("Survive death once\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.HOLY_GRAIL, true)) button = null;
                        break;
                    case "Arc of the Covenant":
                        image.setDrawable(skin, "icon-arc");
                        descriptionLabel.setText("Use the raw power of God instead of the trident\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.ARC_OF_THE_COVENANT, true)) button = null;
                        break;
                    case "Christ the Redeemer":
                        image.setDrawable(skin, "icon-redeemer");
                        descriptionLabel.setText("Restore 1 health every level\nCOSTS 2 MAX HEALTH");
                        if (health <= 2) button.setDisabled(true);
                        if (powers.contains(Power.CHRIST_THE_REDEEMER, true)) button = null;
                        break;
                }
                
                if (button != null) {
                    table = new Table();
                    button.add(table);
    
                    table.defaults().left();
                    var buttonLabel = new Label(blessing, skin, "title");
                    table.add(buttonLabel);
                    
                    table.row();
                    table.add(descriptionLabel);
    
                    if (button.isDisabled()) {
                        button.setColor(Color.DARK_GRAY);
                        buttonLabel.setColor(Color.DARK_GRAY);
                        image.setColor(Color.DARK_GRAY);
                        descriptionLabel.setColor(Color.DARK_GRAY);
                    }
                    
                    var finalButton = button;
                    button.addListener(new ClickListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            super.enter(event, x, y, pointer, fromActor);
                            if (!finalButton.isDisabled()) {
                                finalButton.setColor(Color.RED);
                                buttonLabel.setColor(Color.RED);
                                image.setColor(Color.RED);
                                descriptionLabel.setColor(Color.RED);
                            }
                        }
        
                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            super.exit(event, x, y, pointer, toActor);
                            if (!finalButton.isDisabled()) {
                                finalButton.setColor(Color.WHITE);
                                buttonLabel.setColor(Color.WHITE);
                                image.setColor(Color.WHITE);
                                descriptionLabel.setColor(Color.WHITE);
                            }
                        }
        
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if (!finalButton.isDisabled()) {
                                pop.hide();
                                sfx_gameBlessing.play(sfx);
    
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
                                        powers.add(Power.STRENGTH_OF_SAMSON);
                                        break;
                                    case "Blood of the Lamb":
                                        powers.add(Power.BLOOD_OF_THE_LAMB);
                                        health--;
                                        maxHealth--;
                                        refreshHealthTable();
                                        break;
                                    case "Parting the Red Sea":
                                        powers.add(Power.PARTING_THE_RED_SEA);
                                        break;
                                    case "Patience of Job":
                                        powers.add(Power.PATIENCE_OF_JOB);
                                        refreshControlsTable();
                                        break;
                                    case "Faith of David":
                                        powers.add(Power.FAITH_OF_DAVID);
                                        break;
                                    case "Body of Christ":
                                        powers.add(Power.BODY_OF_CHRIST);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Lance of Longinus":
                                        powers.add(Power.LANCE_OF_LONGINUS);
                                        break;
                                    case "Wings of Michael":
                                        powers.add(Power.WINGS_OF_MICHAEL);
                                        break;
                                    case "Holy Trinity":
                                        powers.add(Power.HOLY_TRINITY);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Godspeed":
                                        powers.add(Power.GODSPEED);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Crown of Thorns":
                                        powers.add(Power.CROWN_OF_THORNS);
                                        break;
                                    case "Holy Light":
                                        powers.add(Power.HOLY_LIGHT);
                                        health--;
                                        maxHealth--;
                                        refreshHealthTable();
                                        break;
                                    case "Noah's Dove":
                                        powers.add(Power.NOAHS_DOVE);
                                        health--;
                                        maxHealth--;
                                        refreshHealthTable();
                                        break;
                                    case "Mark of the Beast":
                                        powers.add(Power.MARK_OF_THE_BEAST);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Holy Grail":
                                        powers.add(Power.HOLY_GRAIL);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Arc of the Covenant":
                                        powers.add(Power.ARC_OF_THE_COVENANT);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                    case "Christ the Redeemer":
                                        powers.add(Power.CHRIST_THE_REDEEMER);
                                        health -= 2;
                                        maxHealth -= 2;
                                        refreshHealthTable();
                                        break;
                                }
                            }
                        }
                    });
                    scrollTable.add(button);
                }
            }
            pop.show(stage);
            stage.setScrollFocus(scroll);
        }
    }
    
    public void hurt() {
        if (shielding) {
            shielding = false;
            animationState.setAnimation(1, animationDeshield, false);
        } else {
            sfx_gamePlayerHurt.play(sfx);
            health--;
            refreshHealthTable();
            if (health == 0) {
                destroy = true;
                sfx_gamePlayerDie.play(sfx);
                var anim = new AnimationEntity(skeletonData, animationData, animationDie, x, y);
                entityController.add(anim);
        
                anim = new AnimationEntity(SpineBlood.skeletonData, SpineBlood.animationData,
                        SpineBlood.animationAnimation, x, y);
                entityController.add(anim);
    
                stage.addAction(Actions.delay(5f, Actions.run(() -> core.transition(new MenuScreen()))));
        
                if (bgm_game.isPlaying()) {
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
                }
            }
        }
    }
}
