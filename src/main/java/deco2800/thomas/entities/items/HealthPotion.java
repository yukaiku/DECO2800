package deco2800.thomas.entities.items;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

public class HealthPotion extends Item {
    public static final String ENTITY_ID_STRING = "health_potion";

    public HealthPotion(Tile tile, boolean obstructed, PlayerPeon player,
            String styleType){
        super("Health Potion",50, tile, RenderConstants.ITEM_RENDER, 
                "potion_large", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        super.display = new ItemBox(this, name, "50", 
                "Adds +40 to Player Health", styleType);
    }
}
    
