package deco2800.thomas.entities.items;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.worlds.Tile;

public class Shield extends Item {
    public static final String ENTITY_ID_STRING = "shield";

    public Shield(Tile tile, boolean obstructed){
        super("Iron Shield",200, tile, RenderConstants.ITEM_RENDER, "shield_iron", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }
}
