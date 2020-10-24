package deco2800.thomas.entities.environment.swamp;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampTreeStub extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampTreeStub";

    public SwampTreeStub() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampTreeStub(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_TREE_STUB, "swamp_tree_stub", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampTreeStub(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_TREE_STUB, entityParts);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
