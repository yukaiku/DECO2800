package deco2800.thomas.entities.environment;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class Portal extends StaticEntity implements Tickable {
    private static final String ENTITY_ID_STRING = "portal";

    public Portal(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.PORTAL_RENDER, "portal", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * A constructor for the VolcanoPortal class that inherits from Portal
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the Portal.
     * @param texture The texture pattern for the Static entity
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public Portal(Tile tile, boolean obstructed, String texture, String name) {
        super(tile, RenderConstants.PORTAL_RENDER, texture, obstructed);
        this.setObjectName(name);
    }

}
