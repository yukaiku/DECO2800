package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoLavaPool extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoLavaPool";

    public VolcanoLavaPool() {
        super();
    }

    public VolcanoLavaPool(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_LAVA_POOL, texture, obstructed);
    }

    public VolcanoLavaPool(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_LAVA_POOL, entityParts);
    }

}
