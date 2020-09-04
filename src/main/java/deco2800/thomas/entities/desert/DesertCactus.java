package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A cactus plant which spawns in the Desert World.
 * Damages the player if they are on a neighbouring tile.
 */
public class DesertCactus extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertCactus";

    /**
     * Creates a DesertCactus without a designated Tile.
     */
    public DesertCactus() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertCactus with a designated Tile.
     *
     * @param tile The tile that the cactus is spawned on.
     */
    public DesertCactus(Tile tile) {
        super(tile, RenderConstants.DESERT_CACTUS, "desertCactus", true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
