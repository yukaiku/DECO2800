package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampDeadTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampDeadTree";

    public SwampDeadTree() {
        super();
    }

    public SwampDeadTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.SWAMP_DEAD_TREE, "swamp_dead_tree", obstructed);
    }

    public SwampDeadTree(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.SWAMP_DEAD_TREE, entityParts);
    }
}