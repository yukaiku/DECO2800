package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class DesertQuicksand extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertQuicksand";

    public DesertQuicksand() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public DesertQuicksand(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.DESERT_QUICKSAND, "desertQuicksand", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
