package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampFallenTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampFallenTree";

    public SwampFallenTree() {
        super();
    }

    public SwampFallenTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_FALLEN_TREE, "swamp_fallen_tree", obstructed);
    }

    public SwampFallenTree(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_FALLEN_TREE, entityParts);
    }
}