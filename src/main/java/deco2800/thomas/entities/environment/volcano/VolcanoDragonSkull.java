package deco2800.thomas.entities.Environment.volcano;

import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.Part;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class VolcanoDragonSkull extends StaticEntity {
    private static final String ENTITY_ID_STRING = "VolcanoDragonSkull";

    public VolcanoDragonSkull() {
        super();
    }

    public VolcanoDragonSkull(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_DRAGON_SKULL, texture, obstructed);
    }

    public VolcanoDragonSkull(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_DRAGON_SKULL, entityParts);
    }
}
