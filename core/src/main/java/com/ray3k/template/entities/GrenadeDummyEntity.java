package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineGrenade.*;
import static com.ray3k.template.screens.GameScreen.*;

public class GrenadeDummyEntity extends EnemyEntity {
    private final static Vector2 temp = new Vector2();
    
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
    public void colorIntentTiles() {
        var hex = hexUtils.pixelToGridHex(temp.set(x, y));
        var targetHexes = hexUtils.getHexesInRadius(hex, 1);
        for (var targetHex : targetHexes) {
            if (targetHex != null && targetHex.userObject instanceof  GroundEntity) {
                var ground = (GroundEntity) targetHex.userObject;
                ground.skeleton.setColor(Color.YELLOW);
            }
        }
    }
    
    @Override
    public void takeTurn() {
    
    }
    
    @Override
    public void completeMoving() {
    
    }
    
    @Override
    public void hurt() {
    
    }
}
