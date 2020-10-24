package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class WoodenArmour extends Item{
    public static final String ENTITY_ID_STRING = "wooden_armour";
    private int armourValue;

    public WoodenArmour(Tile tile, boolean obstructed, PlayerPeon player,
                      String styleType, int armourValue){
        super("Wooden Armour",0, tile, RenderConstants.ITEM_RENDER,
                "armour_wood", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        this.armourValue = armourValue;
        super.display = new ItemBox(this, name, Integer.toString(super.getCurrencyValue()), "Adds "
                + getArmourValue() + "armour to player", styleType);
    }

    public int getArmourValue(){
        return this.armourValue;
    }
}
