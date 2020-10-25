package deco2800.thomas.entities.items;

import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.Tile;

public class Amulet extends Item{
    public static final String ENTITY_ID_STRING = "attack_amulet";
    private int attackDamage;

    public Amulet(Tile tile, boolean obstructed, PlayerPeon player,
                  String styleType, int attackDamage){
        super("Attack Amulet",1000, tile, RenderConstants.ITEM_RENDER,
                "attack_buff", obstructed, player);
        this.setObjectName(ENTITY_ID_STRING);
        this.attackDamage = attackDamage;
        super.display = new ItemBox(this, name, Integer.toString(super.getCurrencyValue()),
                "Increases player's damage by "+ this.attackDamage + ".", styleType);
    }

    public int getAttackDamage(){
        return this.attackDamage;
    }
}
