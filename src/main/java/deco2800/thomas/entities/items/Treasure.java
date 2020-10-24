package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class Treasure extends Item{
	public static final String ENTITY_ID_STRING = "treasure";
	
	public Treasure(Tile tile, boolean obstructed, PlayerPeon player,
			String styleType) {
		super("Treasure", 0, tile, RenderConstants.ITEM_RENDER, "treasure_box",
				obstructed, player);
		this.setObjectName(ENTITY_ID_STRING);
		
		super.display = new ItemBox(this, ENTITY_ID_STRING, "0", "Treasure Box",
				styleType);
	}
}
