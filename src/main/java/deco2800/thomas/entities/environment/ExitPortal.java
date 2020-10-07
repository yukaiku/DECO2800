package deco2800.thomas.entities.environment;

import deco2800.thomas.worlds.Tile;

public class ExitPortal extends Portal {
    private static final String ENTITY_ID_STRING = "ExitPortal";

    /**
     * A constructor for the ExitPortal class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the Portal.
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public ExitPortal(Tile tile, boolean obstructed) {
        super(tile, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * A constructor for the ExitPortal class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the Portal.
     * @param texture The texture pattern for the Static entity
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public ExitPortal(Tile tile, boolean obstructed, String texture, String specificID) {
        super(tile, obstructed, texture, specificID);
    }
}
