package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.lol.fraud.HexTile;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineSuccubus.*;
import static com.ray3k.template.screens.GameScreen.*;

public class SuccubusEntity extends EnemyEntity {
    private static final Vector2 temp = new Vector2();
    public enum Status {
        EMPTY, LOADED, CHARGING
    }
    public Status status;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        depth = DEPTH_ENEMY;
        status = Status.EMPTY;
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
    public void takeTurn() {
        var pathHead = hexUtils.pixelToGridHex(temp.set(x, y));
        var pathTail = hexUtils.pixelToGridHex(temp.set(player.x, player.y));
    
        float distance = Integer.MAX_VALUE;
        if (pathHead.r == pathTail.r || pathHead.q == pathTail.q || pathHead.s == pathTail.s) {
            distance = pathHead.distance(pathTail);
        }
        
        if (distance > 5) {
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
        }
        
        var adjacentTiles = hexUtils.getHexesInRadius(pathHead, 3);
        adjacentTiles.remove(pathTail);
        var iter = adjacentTiles.iterator();
        HexTile closest = null;
        int closestDistance = Integer.MAX_VALUE;
        while (iter.hasNext()) {
            var hex = iter.next();
            
            if (hex == null || hex.weight > 0 || hex.distance(pathHead) <= 1 || hex.distance(pathTail) > 1 || !(hex.userObject instanceof GroundEntity)) {
                iter.remove();
                continue;
            }

            var newDistance = hex.distance(pathTail);
            if (newDistance < closestDistance) {
                closestDistance = newDistance;
                closest = hex;
            }
        }
        
        if (closest != null && status == Status.LOADED) {
            var ground = (GroundEntity) closest.userObject;
            status = Status.EMPTY;
            animationState.setEmptyAnimation(1, 0);
            
            var grenade = new GrenadeEntity();
            entityController.add(grenade);
            enemies.add(grenade);
            characters.add(grenade);
            grenade.setPosition(x, y);
            closest.weight = 100;
            stage.addAction(Actions.delay(.5f, Actions.run(() -> {
                grenade.moveTowardsTarget(600.0f, ground.x, ground.y);
            })));
        } else {
            switch (status) {
                case EMPTY:
                    status = Status.CHARGING;
                    break;
                case CHARGING:
                    status = Status.LOADED;
                    animationState.setAnimation(1, animationGrenade, false);
                    break;
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
