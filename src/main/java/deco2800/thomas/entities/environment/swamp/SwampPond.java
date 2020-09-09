package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampPond extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampPond";

    public SwampPond() {
        super();
    }

    public SwampPond(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_POND, "swamp_pond", obstructed);
    }

    public SwampPond(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_POND, entityParts);
    }
}
