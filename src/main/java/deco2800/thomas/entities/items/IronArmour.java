package deco2800.thomas.entities.items;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.worlds.Tile;

public class IronArmour extends Item {
    public static final String ENTITY_ID_STRING = "shield";

    public IronArmour(Tile tile, boolean obstructed, PlayerPeon player,
                      String styleType){
        super("Iron Armour",80, tile, RenderConstants.ITEM_RENDER,
                "armour_iron", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        super.display = new ItemBox(this, name, "80", "Protects " +
                "against fireballs", styleType);
    }
}
