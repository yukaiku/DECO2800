package deco2800.thomas.entities.environment.swamp;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampDeadTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampDeadTree";

    public SwampDeadTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampDeadTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_DEAD_TREE, "swamp_dead_tree", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampDeadTree(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_DEAD_TREE, entityParts);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
