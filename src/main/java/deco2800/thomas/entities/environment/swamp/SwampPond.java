package deco2800.thomas.entities.environment.swamp;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampPond extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampPond";

    public SwampPond() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampPond(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_POND, "swamp_pond", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampPond(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_POND, entityParts);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
