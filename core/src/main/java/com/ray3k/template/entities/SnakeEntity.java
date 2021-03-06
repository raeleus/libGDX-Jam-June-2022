package com.ray3k.template.entities;

import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineSnake.*;

public class SnakeEntity extends EnemyEntity {
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
    
    }
    
    @Override
    public void takeTurn() {
    
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
