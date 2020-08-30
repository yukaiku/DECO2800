package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class SwampTreeStub extends StaticEntity {
    private static final String ENTITY_ID_STRING = "SwampTreeStub";

    public SwampTreeStub() {
        super();
    }

    public SwampTreeStub(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile, renderOrder, texture, obstructed);
    }

    public SwampTreeStub(float col, float row, int renderOrder, List<Part> entityParts) {
        super(col, row, renderOrder, entityParts);
    }
}
