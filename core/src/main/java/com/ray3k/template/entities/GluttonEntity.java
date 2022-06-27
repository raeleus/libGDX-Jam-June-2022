package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.HexTile;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineGlutton.*;
import static com.ray3k.template.screens.GameScreen.*;

public class GluttonEntity extends EnemyEntity {
    private static final Vector2 temp = new Vector2();
    
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
        System.out.println("color");
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
        if (!moveTargetActivated) {
            var pathHead = hexUtils.pixelToGridHex(temp.set(x, y));
            var pathTail = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
            if (pathTail != null) {
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
                        } else if (MathUtils.isEqual(ground.x, player.x) && MathUtils.isEqual(ground.y, player.y)) {
                            player.hurt();
                            if (powers.contains(Power.CROWN_OF_THORNS, true)) hurt();
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void completeMoving() {
    
    }
    
    @Override
    public void hurt() {
        destroy = true;
    
        var anim = new AnimationEntity(SpineBlood.skeletonData, SpineBlood.animationData, SpineBlood.animationAnimation, x, y);
        entityController.add(anim);
    }
}
