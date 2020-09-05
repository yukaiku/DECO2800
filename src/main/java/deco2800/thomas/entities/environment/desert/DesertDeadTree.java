package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A dead tree entity which spawns in the Desert World.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertDeadTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertDeadTree";

    /**
     * Creates a DesertDeadTree without a designated Tile or texture.
     */
    public DesertDeadTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertDeadTree with a designated Tile and texture.
     *
     * @param tile The tile that the tree is spawned on.
     * @param texture The designated texture.
     */
    public DesertDeadTree(Tile tile, String texture) {
        super(tile, RenderConstants.DESERT_DEAD_TREE, texture, true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
