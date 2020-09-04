package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class OasisTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "OasisTree";

    public OasisTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public OasisTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.OASIS_TREE, "oasisTree", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
