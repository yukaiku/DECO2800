package deco2800.thomas.entities.environment.tutorial;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class Target extends StaticEntity implements Tickable {
    private static final String ENTITY_ID_STRING = "target";

    public Target() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Target(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.TARGET_RENDER, ENTITY_ID_STRING, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        // do nothing
    }
}
