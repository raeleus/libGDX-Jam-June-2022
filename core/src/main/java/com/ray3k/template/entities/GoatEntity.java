package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineGoat.*;
import static com.ray3k.template.screens.GameScreen.*;

public class GoatEntity extends EnemyEntity {
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
    
    @Override
    public void hurt() {
        destroy = true;
    
        var anim = new AnimationEntity(SpineBlood.skeletonData, SpineBlood.animationData, SpineBlood.animationAnimation, x, y);
        entityController.add(anim);
    }
}
