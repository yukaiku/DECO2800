package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class Crown extends Item{
    public static final String ENTITY_ID_STRING = "crown_stone";
    private int attackDamage;
    private int armour;

    public Crown(Tile tile, boolean obstructed, PlayerPeon player,
                  String styleType, int attackDamage, int armour){
        super("Crown Stone",0, tile, RenderConstants.ITEM_RENDER,
                "crown_buff", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        this.attackDamage = attackDamage;
        this.armour = armour;
        super.display = new ItemBox(this, name, Integer.toString(super.getCurrencyValue()),
                "Increases player's damage by "+ getAttackDamage() +
                        ", increases player armor by " + getArmourAmount() + "." , styleType);
    }

    public int getAttackDamage(){
        return this.attackDamage;
    }

    public int getArmourAmount(){return this.armour;}
}
