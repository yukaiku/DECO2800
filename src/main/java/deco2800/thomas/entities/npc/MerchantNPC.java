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
        //Interacts with merchant npc
    }

    @Override
    public void getCurrencyValue() {
        //Get currency
    }

    @Override
    public void setCurrency() {
        //sets currency of merchant
    }

    @Override
    public void removeCurrency() {
        //removes currency from player
    }

    @Override
    public void addCurrency() {
        //adds currency to player
    }
}
