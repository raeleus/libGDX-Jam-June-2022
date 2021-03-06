package com.ray3k.template.entities;

import com.badlogic.gdx.math.Vector2;
import com.ray3k.template.*;

import static com.ray3k.template.screens.GameScreen.*;

public abstract class EnemyEntity extends Entity {
    public abstract void colorIntentTiles();
    public abstract void takeTurn();
    public abstract void completeMoving();
    public abstract void hurt();
    private static Vector2 temp = new Vector2();
    
    @Override
    public void destroy() {
        var hex = hexUtils.pixelToGridHex(temp.set(x, y));
        hex.weight = 0;
        
        enemies.removeValue(this, true);
        characters.removeValue(this, true);
        if (!(this instanceof GrenadeEntity)) {
            energy = Utils.approach(energy, maxEnergy, 1);
            refreshEnergyTable();
        }
        killedThisTurn = true;
    }
}
