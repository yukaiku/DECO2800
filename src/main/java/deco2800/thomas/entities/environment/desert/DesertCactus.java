package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A cactus plant which spawns in the Desert World.
 * Damages the player if they are on a neighbouring tile.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertCactus extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertCactus";

    /**
     * Creates a DesertCactus without a designated Tile or texture.
     */
    public DesertCactus() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertCactus with a designated Tile and texture.
     *
     * @param tile The tile that the cactus is spawned on.
     * @param texture The designated texture.
     */
    public DesertCactus(Tile tile, String texture) {
        super(tile, RenderConstants.DESERT_CACTUS, texture, true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
