package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoDragonSkull extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoDragonSkull";

    public VolcanoDragonSkull() {
        super();
    }

    public VolcanoDragonSkull(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile, renderOrder, texture, obstructed);
    }

    public VolcanoDragonSkull(float col, float row, int renderOrder, List<Part> entityParts) {
        super(col, row, renderOrder, entityParts);
    }
}
