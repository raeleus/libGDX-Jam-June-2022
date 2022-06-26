package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.HexTile;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineSkeleton.*;
import static com.ray3k.template.screens.GameScreen.*;

public class SkeletonEntity extends EnemyEntity {
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
    
            var deltaQ = MathUtils.clamp(pathTail.q - pathHead.q, -1, 1);
            var deltaR = MathUtils.clamp(pathTail.r - pathHead.r, -1, 1);
            var deltaS = MathUtils.clamp(pathTail.s - pathHead.s, -1, 1);
            var q = pathHead.q;
            var r = pathHead.r;
            var s = pathHead.s;
            
            var blocked = false;
            while (q != pathTail.q || r != pathTail.r || s != pathTail.s) {
                q = Utils.approach(q, pathTail.q, Math.abs(deltaQ));
                r = Utils.approach(r, pathTail.r, Math.abs(deltaR));
                s = Utils.approach(s, pathTail.s, Math.abs(deltaS));
                var hex = hexUtils.getTile(q, r, s);
                if (hex.userObject instanceof GroundEntity) {
                    var ground = (GroundEntity) hex.userObject;
                    for (var enemy : enemies) {
                        if (MathUtils.isEqual(enemy.x, ground.x) && MathUtils.isEqual(enemy.y, ground.y)) {
                            blocked = true;
                            break;
                        }
                    }
                }
            }
            
            if (!blocked) {
                sfx_gameBow.play(sfx);
                player.hurt();
    
                var arrow = new ArrowEntity();
                entityController.add(arrow);
                arrow.skeleton.getRootBone().setRotation(Utils.pointDirection(this, player));
                arrow.setPosition(x, y);
                arrow.moveTowardsTarget(600.0f, player.x, player.y);
                stage.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (!arrow.moveTargetActivated) {
                            arrow.destroy = true;
                            return true;
                        } else return false;
                    }
                });
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
