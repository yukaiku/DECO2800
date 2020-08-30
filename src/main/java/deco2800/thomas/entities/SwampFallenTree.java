package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampFallenTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampFallenTree";

    public SwampFallenTree() {
        super();
    }

    public SwampFallenTree(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile, renderOrder, texture, obstructed);
    }

    public SwampFallenTree(float col, float row, int renderOrder, List<Part> entityParts) {
        super(col, row, renderOrder, entityParts);
    }
}
