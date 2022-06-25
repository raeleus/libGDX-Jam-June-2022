package com.ray3k.template.entities;

import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineTileGround.*;

public class GroundEntity extends Entity {
    public final static int TILE_WIDTH = 72;
    public final static int TILE_HEIGHT = 64;
    public final static int COLUMN_OFFSET = TILE_WIDTH * 3 / 4;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        depth = DEPTH_GROUND;
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
    
//    public Array<GroundEntity> findSurroundingTiles(Array<GroundEntity> outputArray) {
//        for (int i = 0; i < gameScreen.grounds.size; i++) {
//            var ground = gameScreen.grounds.get(i);
//            if (MathUtils.isEqual(ground.x, x) && MathUtils.isEqual(ground.y, y + TILE_HEIGHT) ||
//                    MathUtils.isEqual(ground.x, x) && MathUtils.isEqual(ground.y, y - TILE_HEIGHT) ||
//                    MathUtils.isEqual(ground.x - COLUMN_OFFSET, x) && MathUtils.isEqual(ground.y, y + TILE_HEIGHT) ||
//            )
//        }
//        return outputArray;
//    }
}
