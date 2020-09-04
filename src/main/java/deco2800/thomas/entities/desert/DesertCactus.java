package deco2800.thomas.entities.desert;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class DesertCactus extends StaticEntity {
    private static final String ENTITY_ID_STRING = "DesertCactus";

    public DesertCactus() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public DesertCactus(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.DESERT_CACTUS, "desertCactus", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
