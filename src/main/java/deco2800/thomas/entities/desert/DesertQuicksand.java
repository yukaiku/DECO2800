package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * Quicksand which spawns in parts of the Desert World. Damages and slows the player
 * if stood on.
 */
public class DesertQuicksand extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertQuicksand";

    /**
     * Creates a DesertQuicksand entity without a designated Tile.
     */
    public DesertQuicksand() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertQuicksand entity with a designated Tile.
     *
     * @param tile The tile that the quicksand is spawned on.
     */
    public DesertQuicksand(Tile tile) {
        super(tile, RenderConstants.DESERT_QUICKSAND, "desertQuicksand", false);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
