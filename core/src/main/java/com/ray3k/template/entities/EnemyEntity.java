package com.ray3k.template.entities;

import com.badlogic.gdx.math.Vector2;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.screens.GameScreen.*;

public abstract class EnemyEntity extends Entity {
    public abstract void takeTurn();
    public abstract void completeMoving();
    public abstract void hurt();
    private static Vector2 temp = new Vector2();
    
    @Override
    public void destroy() {
        var hex = hexUtils.pixelToGridHex(temp.set(x, y));
        hex.weight = 0;
        
        var anim = new AnimationEntity(SpineBlood.skeletonData, SpineBlood.animationData, SpineBlood.animationAnimation, x, y);
        entityController.add(anim);
        
        enemies.removeValue(this, true);
        energy = Utils.approach(energy, maxEnergy, 1);
        refreshEnergyTable();
    }
}
