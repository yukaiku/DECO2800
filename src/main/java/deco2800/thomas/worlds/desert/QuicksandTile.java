package deco2800.thomas.worlds.desert;

import deco2800.thomas.worlds.Tile;

public class QuicksandTile extends Tile {

    public QuicksandTile(String texture) {
        super(texture);
    }

    public QuicksandTile(String texture, float col, float row) {
        super(texture, col, row);
    }

    public QuicksandTile() {
    }

    @Override
    public String getType() {
        return "Quicksand";
    }
}
