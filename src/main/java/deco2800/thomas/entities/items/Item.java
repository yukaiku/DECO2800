package deco2800.thomas.entities.items;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Currency;
import deco2800.thomas.entities.Tradeable;
import deco2800.thomas.managers.TextureManager;

public class Item extends AbstractEntity {
    protected String name;
    protected String texture;
    protected int goldValue;

    public Item(String name, int value, String texture){
        this.name = name;
        this.goldValue = value;
        this.texture = texture;
    }

    public String getItemName(){
        return this.name;
    }

    public int getCurrencyValue(){
        return this.goldValue;
    }

    @Override
    public void onTick(long i) {

    }
}
