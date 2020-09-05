package deco2800.thomas.worlds;

public class VolcanoBurnTile extends Tile {
    private float burnDamage;

    public VolcanoBurnTile(String texture, float col, float row) {
        super(texture, col, row);
    }

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
