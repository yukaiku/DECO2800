package deco2800.thomas.worlds.volcano;

import deco2800.thomas.worlds.Tile;

/**
 * A VolcanoBurn tile, which will hold replace lava textures in the volcano world
 * & invoke burn status on agent entities that come into contact with the this tile.
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoBurnTile extends Tile {

    //Damage to be dealt to entities.
    private float burnDamage;

    /**
     * Constructor for the burn tile
     * @param texture - Specified texture of the tile
     * @param col - Specified column of the tile
     * @param row - Specified row of the tile
     * @param damage - Specified damage to be dealt by the tile
     */
    public VolcanoBurnTile(String texture, float col, float row, float damage) {
        super(texture, col, row);
        this.burnDamage = damage;
        this.setType("BurnTile");
        this.setStatusEffect(true);
    }

    /**
     * Returns the damage to be dealt by the tile
     * @return (Damage to be dealt upon Tile-Entity collision
     */
    public float getTileDamage()  {
        return this.burnDamage;
    }

    /**
     * Sets the damage to be Dealt by the tile
     * @param damage - Damage dealt (In hitpoints)
     */
    public void setTileDamage(float damage) {
        this.burnDamage = damage;
    }
}
