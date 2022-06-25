package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineLavaBubble.*;

public class BubbleEntity extends Entity {
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.addAnimation(0, animationAnimation, false, MathUtils.random(.5f));
        depth = DEPTH_LAVA_BUBBLE;
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                destroy = true;
            }
        });
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
}
