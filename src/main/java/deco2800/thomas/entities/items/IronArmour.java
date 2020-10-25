package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class IronArmour extends Item {
    public static final String ENTITY_ID_STRING = "iron_armour";
    private int armourValue;

    public IronArmour(Tile tile, boolean obstructed, PlayerPeon player,
                      String styleType, int armourValue){
        super("Iron Armour",250, tile, RenderConstants.ITEM_RENDER,
                "armour_iron", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        this.armourValue = armourValue;
        super.display = new ItemBox(this, name, Integer.toString(super.getCurrencyValue()),
                "Adds " + getArmourValue() +  "armour to the player", styleType);
    }

    public int getArmourValue(){
        return this.armourValue;
    }
}
