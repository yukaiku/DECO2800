package deco2800.thomas.entities.environment.swamp;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampTreeLog extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampTreeLog";

    public SwampTreeLog() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampTreeLog(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_TREE_LOG, "swamp_tree_log", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampTreeLog(float col, float row, int renderOrder, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_TREE_LOG, entityParts);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
