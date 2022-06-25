package com.ray3k.template.entities;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.lol.fraud.HexTile;
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
                                        enemy.y)) {
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
                                            enemy.y)) {
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
                                    enemy.y)) {
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
                                    enemy.y)) {
                                slashGround.skeleton.setColor(Color.RED);
                                slashEnemies.add(enemy);
                                break;
                            }
                        }
                    }
                }
    
                if (isButtonJustPressed(Buttons.LEFT)) {
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
        if (MathUtils.isEqual(x, moveTargetX) && MathUtils.isEqual(y, moveTargetY)) {
            if (thrustEnemies.size > 0 || slashEnemies.size > 0) {
                sfx_gameSlash.play(sfx);
                
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
        
        if (MathUtils.isEqual(x, pentagramEntity.x) && MathUtils.isEqual(y, pentagramEntity.y)) {
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
    }
}
