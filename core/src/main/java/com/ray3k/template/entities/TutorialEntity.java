package com.ray3k.template.entities;

import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineTutorial.*;

public class TutorialEntity extends Entity {
    private String skin;
    
    public TutorialEntity(String skin) {
        this.skin = skin;
    }
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        depth = DEPTH_TUTORIAL;
        skeleton.setSkin(skin);
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
    
    public Array<TutorialEntity> findSurroundingTiles(Array<TutorialEntity> outputArray) {
//        gameScreen.
        return outputArray;
    }
}
