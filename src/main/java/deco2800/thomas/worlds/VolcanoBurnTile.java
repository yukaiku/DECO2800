package deco2800.thomas.worlds;

public class VolcanoBurnTile extends Tile {
    private int burnDamage;
    public VolcanoBurnTile(String texture, float col, float row, int damage) {
        super(texture, col, row);
        this.burnDamage = damage;
    }
    public int getTileDamage()  {
        return this.burnDamage;
    }
}
