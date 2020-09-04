package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A shrub entity which spawns in the Desert World's oasis area.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class OasisShrub extends StaticEntity {
    private static final String ENTITY_ID_STRING = "OasisShrub";

    /**
     * Creates an OasisShrub without a designated Tile.
     */
    public OasisShrub() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates an OasisShrub with a designated Tile.
     *
     * @param tile The tile that the shrub is spawned on.
     */
    public OasisShrub(Tile tile) {
        super(tile, RenderConstants.OASIS_SHRUB, "oasisShrub", false);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
