package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class CooldownRing extends Item{
    public static final String ENTITY_ID_STRING = "cooldown_reduction_buff";
    private float reductionvalue;

    public CooldownRing(Tile tile, boolean obstructed, PlayerPeon player,
                  String styleType, float reductionvalue){
        super("Cooldown Ring",1000, tile, RenderConstants.ITEM_RENDER,
                "cdreduction_buff", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        this.reductionvalue = reductionvalue;
        super.display = new ItemBox(this, name, Integer.toString(super.goldValue),
                "Reduces the cooldown of player's skill by "+ (this.reductionvalue * 100) + "%.", styleType);
    }

    public float getReductionvalue(){
        return this.reductionvalue;
    }
}
