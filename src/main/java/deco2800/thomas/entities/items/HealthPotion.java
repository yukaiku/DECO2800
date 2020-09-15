package deco2800.thomas.entities.items;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.worlds.Tile;

public class HealthPotion extends Item {
    public static final String ENTITY_ID_STRING = "health_potion";

    public HealthPotion(Tile tile, boolean obstructed){
        super("Healh Potion",50, tile, RenderConstants.ITEM_RENDER, "potion_large", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

}
