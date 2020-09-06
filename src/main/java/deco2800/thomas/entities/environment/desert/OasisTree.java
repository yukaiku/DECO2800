package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A palm tree which spawns in the Desert World's oasis area.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class OasisTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "OasisTree";

    /**
     * Creates the OasisTree without a designated Tile or texture.
     */
    public OasisTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates the OasisTree with a designated Tile and texture.
     *
     * @param tile The tile that the tree is spawned on.
     * @param texture The designated texture.
     */
    public OasisTree(Tile tile, String texture) {
        super(tile, RenderConstants.OASIS_TREE, texture, true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
