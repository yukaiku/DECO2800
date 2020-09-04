package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoBones extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoBones";

    public VolcanoBones() {
        super();
    }

    public VolcanoBones(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_BONES, texture, obstructed);
    }

    public VolcanoBones(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_BONES, entityParts);
    }
}
