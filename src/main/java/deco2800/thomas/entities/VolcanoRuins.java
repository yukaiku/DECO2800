package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoRuins extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoRuins";

    public VolcanoRuins() {
        super();
    }

    public VolcanoRuins(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_RUINS, texture, obstructed);
    }

    public VolcanoRuins(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_RUINS, entityParts);
    }
}
