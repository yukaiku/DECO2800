package deco2800.thomas.entities.items;

import deco2800.thomas.entities.Currency;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.Tradeable;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.worlds.Tile;
import java.util.Random;

public class Item extends StaticEntity {

    public static final String ENTITY_ID_STRING = "item";
    protected String name;
    protected int goldValue;

    public Item(String name, int value, Tile tile, int renderOrder, String texture, boolean obstructed){
        super(tile, RenderConstants.ITEM_RENDER, texture, obstructed);
        this.name = name;
        this.goldValue = value;
        this.setObjectName(ENTITY_ID_STRING);
    }

    public String getItemName(){
        return this.name;
    }

    public int getCurrencyValue(){
        return this.goldValue;
    }

    public static int randomItemPositionGenerator (int min, int max){
        return new Random().nextInt((max/2) - (min/2)) - min;
    }

    @Override
    public void onTick(long i) {

    }
}
