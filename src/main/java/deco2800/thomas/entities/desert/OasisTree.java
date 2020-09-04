package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A palm tree which spawns in the Desert World's oasis area.
 */
public class OasisTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "OasisTree";

    /**
     * Creates the OasisTree without a designated Tile.
     */
    public OasisTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates the OasisTree with a designated Tile.
     *
     * @param tile The tile that the tree is spawned on.
     */
    public OasisTree(Tile tile) {
        super(tile, RenderConstants.OASIS_TREE, "oasisTree", true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
