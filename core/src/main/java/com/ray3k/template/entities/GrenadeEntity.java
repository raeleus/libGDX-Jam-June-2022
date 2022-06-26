package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineGrenade.*;
import static com.ray3k.template.screens.GameScreen.*;

public class GrenadeEntity extends EnemyEntity {
    private final static Vector2 temp = new Vector2();
    public int timer;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        depth = DEPTH_ENEMY;
        timer = 1;
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
        sfx_gameExplosion.play(sfx);
        
        var currentHex = hexUtils.pixelToGridHex(temp.set(x, y));
        if (currentHex != null) {
            var targetHexes = hexUtils.getHexesInRing(currentHex, 1);
            targetHexes.add(currentHex);
            
            for (var hex : targetHexes) {
                if (hex != null) {
                    hexUtils.hexToPixel(hex, temp);
                    var anim = new AnimationEntity(SpineExplosion.skeletonData, SpineExplosion.animationData,
                            SpineExplosion.animationAnimation, temp.x, temp.y);
                    entityController.add(anim);
                    
                    if (hex.userObject instanceof GroundEntity) {
                        var ground = (GroundEntity) hex.userObject;
                        for (var enemy : enemies) {
                            if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                                enemy.hurt();
                            }
                        }
                        if (player != null) {
                            if (MathUtils.isEqual(player.x, ground.x) && MathUtils.isEqual(player.y, ground.y)) {
                                player.hurt();
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
    
    }
    
    @Override
    public void takeTurn() {
        if (timer > 0) {
            timer--;
        } else {
            stage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    if (!moveTargetActivated) {
                        destroy = true;
                        return true;
                    } else return false;
                }
            });
        }
    }
    
    @Override
    public void completeMoving() {
    
    }
    
    @Override
    public void hurt() {
    
    }
}
