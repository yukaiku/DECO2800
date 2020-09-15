package deco2800.thomas.worlds.desert;

import deco2800.thomas.worlds.Tile;

/**
 * A cactus tile, which will hold a DesertCactus entity in the Desert World.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class CactusTile extends Tile {

    /**
     * Creates a CactusTile with a designated texture.
     *
     * @param texture The designated texture.
     */
    public CactusTile(String texture) {
        super(texture);
    }

    /**
     * Creates a CactusTile with a designated texture, row and column.
     *
     * @param texture The designated texture.
     * @param col The designated column.
     * @param row The designated row.
     */
    public CactusTile(String texture, float col, float row) {
        super(texture, col, row);
    }

    /**
     * Creates a CactusTile without any designated params.
     */
    public CactusTile() {
    }

    /**
     * Returns the type of this Tile.
     *
     * @return The type of this Tile.
     */
    @Override
    public String getType() {
        return "Cactus";
    }

    @Override
    public boolean hasStatusEffect() {
        // the cactus does not damage players - its neighbours do
        return false;
    }
}
