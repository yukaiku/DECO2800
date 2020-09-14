package deco2800.thomas.entities.items;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class TestItem extends Item implements Tickable {
    public static final String ENTITY_ID_STRING = "test_potion";

    public TestItem(Tile tile, boolean obstructed){
        super("potionlarge",20, tile, RenderConstants.ITEM_RENDER, "potion-large", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {

    }
}