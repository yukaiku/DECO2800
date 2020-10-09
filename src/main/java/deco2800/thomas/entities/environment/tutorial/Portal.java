package deco2800.thomas.entities.environment.tutorial;
import deco2800.thomas.Tickable;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.entities.RenderConstants;

public class Portal extends StaticEntity implements Tickable {
    private static final String ENTITY_ID_STRING = "target";

    public Portal(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.PORTAL_RENDER, "portal", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {
    }
}
