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
                    if (current.weight == 0) {
                        var ground = (GroundEntity) current.userObject;
                        ground.skeleton.setColor(Color.GREEN);
                        
                        //check for thrust
                        var thrustQ = pathHead.q + (current.q - pathHead.q) * 2;
                        var thrustR = pathHead.r + (current.r - pathHead.r) * 2;
                        var thrustHex = hexUtils.getTile(thrustQ, thrustR, -thrustQ-thrustR);
                        if (thrustHex != null && thrustHex.userObject instanceof GroundEntity) {
                            var thrustGround = (GroundEntity) thrustHex.userObject;
                            for (var enemy : enemies) {
                                if (MathUtils.isEqual(thrustGround.x, enemy.x) && MathUtils.isEqual(thrustGround.y, enemy.y)) {
                                    thrustGround.skeleton.setColor(Color.RED);
                                    thrustEnemies.add(enemy);
                                    break;
                                }
                            }
                        }
                        
                        if (isButtonJustPressed(Buttons.LEFT)) {
//                            System.out.println("pathHead = " + pathHead.q + " " + pathHead.r + " " + pathHead.s);
//                            System.out.println("current = " + current.q + " " + current.r + " " + current.s);
                            
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
        }
    }
    
    public void completeMoving() {
        if (MathUtils.isEqual(x, moveTargetX) && MathUtils.isEqual(y, moveTargetY)) {
            if (thrustEnemies.size > 0 || slashEnemies.size > 0) {
                sfx_gameSlash.play(sfx);
    
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
