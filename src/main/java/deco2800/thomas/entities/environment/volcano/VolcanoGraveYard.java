package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoGraveYard extends StaticEntity {
    private static final String ENTITY_ID_STRING = "VolcanoGraveYard";

    public VolcanoGraveYard() {
        super();
    }

    public VolcanoGraveYard(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_GRAVEYARD, entityParts);
    }
}
