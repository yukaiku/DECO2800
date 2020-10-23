package deco2800.thomas.entities.items;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.combat.skills.*;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

import java.util.Random;

public class Item extends StaticEntity implements TouchDownObserver {

    public static final String ENTITY_ID_STRING = "item";
    protected String name;
    protected int goldValue;
    protected AbstractDialogBox display; 
    protected PlayerPeon player; 
    boolean ready;

    public Item(String name, int value){
        this.name = name;
        this.goldValue = value;
    }

    public Item(String name, int value, Tile tile, int renderOrder,
                String texture, boolean obstructed, PlayerPeon player){
        super(tile, RenderConstants.ITEM_RENDER, texture, obstructed);
        this.name = name;
        this.goldValue = value;
        this.setObjectName(ENTITY_ID_STRING);
        this.setTexture(texture);
        this.player = player; 
        ready = false;
    }

    public String getItemName(){
        return this.name;
    }

    public int getCurrencyValue(){
        return this.goldValue;
    }

    public static int randomItemPositionGenerator (int max){
        return new Random().nextInt(2*max - 0) - max;
    }

    public AbstractDialogBox getDisplay() {
        return this.display;
    }
    
    public void setPlayer(PlayerPeon pl) { 
        player = pl; 
    }

    public void chargePlayer() {
        if (PlayerPeon.checkBalance() >= this.getCurrencyValue()) {
            PlayerPeon.debit(this.getCurrencyValue());

            switch (this.getItemName()){
                case "Health Potion":
                    this.player.regenerateHealth(40);
                    break;
                case "Small Health Potion":
                    this.player.regenerateHealth(20);
                    break;
                case "Iron Armour":
                    if (this.player.getArmour() < 2000){
                        this.player.addArmour(((IronArmour) this).getArmourValue());
                    }
                    break;
                case "Wooden Armour":
                    if (this.player.getArmour() < 2000){
                        this.player.addArmour(((WoodenArmour) this).getArmourValue());
                    }
                    break;
                case "Attack Amulet":
                    this.player.addDamage(((Amulet) this).getAttackDamage());
                    PlayerPeon.addBuffDamageTotal(((Amulet) this).getAttackDamage());
                    break;
                case "Cooldown Ring":
                    PlayerPeon.setCooldownBuff(true);
                    FireballSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    FireBombSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    HealSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    IceballSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    IceBreathSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    SandTornadoSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    ScorpionStingSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    WaterShieldSkill.reduceCooldownMax(((CooldownRing) this).getReductionvalue());
                    break;
                default: //Do nothing
            }
        }
    }
    
    @Override
    public void onTick(long i) {
        if (GameManager.get().getWorld() != null && !ready) {
            ready = true;
            GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        }
        if (this.display.isShowing()) {
            this.display.setVisibleTime(display.getVisibleTime() + 1);
        }
    }
    
    public void interact() {
        this.display.getBox().setVisible(true);
        this.display.setVisibleTime(0);
        this.display.setShowing(true);
    }
    
    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(),
                Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        boolean isCloseCol = clickedPosition[0] == this.getCol();
        boolean isCloseRow = clickedPosition[1] == this.getRow();

        if (isCloseCol && isCloseRow) {
            if (this.player.getPosition().isCloseEnoughToBeTheSame(this.getPosition(),1.25f)) {
                interact();
            }
        }
    }
    
    /**
     * Remove touch down listener.
     */
    /*@Override
    public void dispose() {
        GameManager.getManagerFromInstance(InputManager.class).removeTouchDownListener(this);
    }*/
}
