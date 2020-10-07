package deco2800.thomas.entities.environment.volcano;

import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.worlds.Tile;

public class VolcanoPortal extends Portal {
    private static final String ENTITY_ID_STRING = "VolcanoPortal";

    /**
     * A constructor for the VolcanoPortal class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the Portal.
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public VolcanoPortal(Tile tile, boolean obstructed) {
        super(tile, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * A constructor for the VolcanoPortal class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the Portal.
     * @param texture The texture pattern for the Static entity
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public VolcanoPortal(Tile tile, boolean obstructed, String texture, String specificID) {
        super(tile, obstructed, texture, specificID);
    }


}
