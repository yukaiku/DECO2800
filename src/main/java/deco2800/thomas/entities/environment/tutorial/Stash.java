package deco2800.thomas.entities.environment.tutorial;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class Stash extends StaticEntity implements Tickable {
    public static final String ENTITY_ID_STRING = "stash";

    public Stash(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.STASH_RENDER, "stash", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
        // do nothing
    }
}
