package deco2800.thomas.worlds.volcano;

import deco2800.thomas.tasks.status.BurnStatus;
import deco2800.thomas.tasks.status.StatusEffect;
import deco2800.thomas.worlds.Tile;

public class VolcanoBurnTile extends Tile {
    private float burnDamage;

    public VolcanoBurnTile(String texture, float col, float row, float damage) {
        super(texture, col, row);
        this.burnDamage = damage;
    }

    public float getTileDamage()  {
        return this.burnDamage;
    }

    public void setTileDamage(float damage) {
        this.burnDamage = damage;
    }

}
