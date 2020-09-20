package deco2800.thomas.entities.npc;

import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.Tradeable;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.util.SquareVector;

import java.util.List;

public class MerchantNPC extends NonPlayablePeon implements Interactable, Tradeable {

    private List<Item> shop;

    public MerchantNPC(String name, SquareVector position, String texture, List<Item> shopList){
        super(name, position, texture);
        this.shop = shopList;
    }

    @Override
    public void interact(){

    }

    @Override
    public void getCurrencyValue() {

    }

    @Override
    public void setCurrency() {

    }

    @Override
    public void removeCurrency() {

    }

    @Override
    public void addCurrency() {

    }
}
