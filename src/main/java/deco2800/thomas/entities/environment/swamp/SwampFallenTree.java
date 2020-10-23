package deco2800.thomas.entities.environment.swamp;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampFallenTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampFallenTree";

    public SwampFallenTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampFallenTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_FALLEN_TREE, "swamp_fallen_tree", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampFallenTree(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_FALLEN_TREE, entityParts);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
