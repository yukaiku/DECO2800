package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoBurningTree extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoBurningTree";

    public VolcanoBurningTree() {
        super();
    }

    public VolcanoBurningTree(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_BURNING_TREE, texture, obstructed);
    }

    public VolcanoBurningTree(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_BURNING_TREE, entityParts);
    }
}
