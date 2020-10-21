package deco2800.thomas.entities.environment;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class TripWire extends StaticEntity implements Tickable {
    public static final String ENTITY_ID_STRING = "tripwire";

    public TripWire(Tile tile, boolean obstructed, String texture) {
        super(tile, RenderConstants.CHEST_RENDER, texture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
