package deco2800.thomas.worlds.desert;

import deco2800.thomas.worlds.Tile;

public class CactusTile extends Tile {

    public CactusTile(String texture) {
        super(texture);
    }

    public CactusTile(String texture, float col, float row) {
        super(texture, col, row);
    }

    public CactusTile() {
    }

    @Override
    public String getType() {
        return "Cactus";
    }
}
