package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

/**
 * The Desert Orb which must be collected by the player.
 * Spawns in the Desert World.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertOrb extends StaticEntity implements Tickable {
    private static final String ENTITY_ID_STRING = "DesertOrb";

    /**
     * Creates the DesertOrb without a designated Tile.
     */
    public DesertOrb() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * Creates the DesertOrb with a designated Tile.
     *
     * @param tile The designated Tile.
     */
    public DesertOrb(Tile tile) {
        super(tile, RenderConstants.DESERT_ORB, "desertOrb", false);
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * On tick is called periodically (time dependant on the world settings).
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {

    }
}
