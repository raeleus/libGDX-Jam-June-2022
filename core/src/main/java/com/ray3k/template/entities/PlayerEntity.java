package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpinePlayer.*;
import static com.ray3k.template.screens.GameScreen.*;

public class PlayerEntity extends Entity {
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        depth = DEPTH_PLAYER;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        if (MathUtils.isEqual(x, pentagramEntity.x) && MathUtils.isEqual(y, pentagramEntity.y)) {
            destroy = true;
            sfx_gameTeleport.play(sfx);
            var anim = new AnimationEntity(skeletonData, animationData, animationTeleport, x, y) {
                @Override
                public void destroy() {
                    super.destroy();
                    stage.addAction(Actions.delay(2.0f, Actions.run(() -> {
                        String level = "home";
                        var tutorial = preferences.getInteger("tutorial", 1);
                        if (tutorial <= 5) {
                            tutorial++;
                            preferences.putInteger("tutorial", tutorial);
                            level = "tutorial" + Utils.intToTwoDigit(tutorial);
                        }
                        core.transition(new GameScreen(level));
                    })));
                }
            };
            entityController.add(anim);
        }
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
