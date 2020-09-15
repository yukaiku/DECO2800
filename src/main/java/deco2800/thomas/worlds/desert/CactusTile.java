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
        this.setType("Cactus");
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
        this.setType("Cactus");
    }

    /**
     * Creates a CactusTile without any designated params.
     */
    public CactusTile() {
        this.setType("Cactus");
    }
}
