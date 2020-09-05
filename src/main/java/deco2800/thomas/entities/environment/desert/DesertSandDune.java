package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * A sand dune which spawns in the Desert World.
 * Used to create large walls which channel the player's movement.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertSandDune extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertSandDune";

    /**
     * Creates a DesertSandDune without a designated Tile.
     */
    public DesertSandDune() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates a DesertSandDune with a designated Tile.
     *
     * @param tile The tile that the dune is spawned on.
     */
    public DesertSandDune(Tile tile) {
        super(tile, RenderConstants.DESERT_SAND_DUNE, "desertSandDune", true);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
