package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A dead tree entity which spawns in the Desert World.
 */
public class DesertDeadTree extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertDeadTree";

    /**
     * Creates a DesertDeadTree without a designated Tile.
     */
    public DesertDeadTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertDeadTree with a designated Tile.
     *
     * @param tile The tile that the tree is spawned on.
     */
    public DesertDeadTree(Tile tile) {
        super(tile, RenderConstants.DESERT_DEAD_TREE, "desertDeadTree", true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
