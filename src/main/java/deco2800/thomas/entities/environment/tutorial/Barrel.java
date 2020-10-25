package deco2800.thomas.entities.environment.tutorial;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class Barrel extends StaticEntity implements Tickable {
    public static final String ENTITY_ID_STRING = "barrel";

    public Barrel(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.BARREL_RENDER, "barrel", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        // do nothing
    }
}
