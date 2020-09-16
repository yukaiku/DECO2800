package deco2800.thomas.worlds.tundra;

import deco2800.thomas.worlds.Tile;

/**
 * A Tundra ice tile, which will take hold of blue/icy coloured tiles in the tundra world
 * & invoke a speed status on agent entities that come into contact with the this tile.
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class TundraWorldIceTile extends Tile {
    /**
     * Constructor for the ice tile
     * @param texture - Specified texture of the tile
     * @param col - Specified column of the tile
     * @param row - Specified row of the tile
     */
    public TundraWorldIceTile(String texture, float col, float row) {
        super(texture, col, row);
        this.setType("TundraIceTile");
        this.setStatusEffect(true);
    }
}
