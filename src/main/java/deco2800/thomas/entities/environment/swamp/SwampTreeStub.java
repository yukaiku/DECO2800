package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampTreeStub extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampTreeStub";

    public SwampTreeStub() {
        super();
    }

    public SwampTreeStub(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_TREE_STUB, "swamp_tree_stub", obstructed);
    }

    public SwampTreeStub(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_TREE_STUB, entityParts);
    }
}
