package deco2800.thomas.entities.items;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.worlds.Tile;

public class Shield extends Item {
    public static final String ENTITY_ID_STRING = "shield";

    public Shield(Tile tile, boolean obstructed, PlayerPeon player,
            String styleType){
        super("Iron Shield",200, tile, RenderConstants.ITEM_RENDER, 
                "shield_iron", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        super.display = new ItemBox(this, name, "80", "Protects " +
                "against fireballs", styleType);
    }
}
