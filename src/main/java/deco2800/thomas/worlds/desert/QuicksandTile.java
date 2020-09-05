package deco2800.thomas.worlds.desert;

import deco2800.thomas.worlds.Tile;

/**
 * A quicksand tile, which will hold a DesertQuicksand entity in the Desert World.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class QuicksandTile extends Tile {

    /**
     * Creates a QuicksandTile with a designated texture.
     *
     * @param texture The designated texture.
     */
    public QuicksandTile(String texture) {
        super(texture);
    }

    /**
     * Creates a QuicksandTile with a designated texture, row and column.
     *
     * @param texture The designated texture.
     * @param col The designated column.
     * @param row The designated row.
     */
    public QuicksandTile(String texture, float col, float row) {
        super(texture, col, row);
    }

    /**
     * Creates a CactusTile without any designated params.
     */
    public QuicksandTile() {
    }

    /**
     * Returns the type of this Tile.
     *
     * @return The type of this Tile.
     */
    @Override
    public String getType() {
        return "Quicksand";
    }
}
