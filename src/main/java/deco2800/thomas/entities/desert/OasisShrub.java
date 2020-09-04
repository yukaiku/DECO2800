package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class OasisShrub extends StaticEntity {
    private static final String ENTITY_ID_STRING = "OasisShrub";

    public OasisShrub() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public OasisShrub(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.OASIS_SHRUB, "oasisShrub", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
