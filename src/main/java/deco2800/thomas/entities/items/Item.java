package deco2800.thomas.entities.items;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.combat.skills.*;
import deco2800.thomas.combat.skills.IceBreathSkill;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

import java.security.SecureRandom;
import java.util.Random;

public class Item extends StaticEntity implements TouchDownObserver {
    // Entity ID string
    public static final String ENTITY_ID_STRING = "item";

    // Item name
    protected String name;

    // Item goldvalue
    protected int goldValue;

    // Item dialogue
    protected AbstractDialogBox display;

    // Player character
    protected PlayerPeon player;

    // Setup boolean check
    boolean ready;

    /**
     * The default constructor for an item with a name and a gold value.
     * @param name The name of the item.
     * @param value The gold value of the item.
     */
    public Item(String name, int value){
        this.name = name;
        this.goldValue = value;
    }

    /**
     * Creates an item at a given position, texture, render order, and obstruction in the currently loaded world.
     * @param name The name of the item.
     * @param value The gold value of the item.
     * @param tile The given tile position in the current world.
     * @param renderOrder The render order of the item, higher means topmost, lower means bottommost
     * @param texture The texture of the item
     * @param obstructed Obstruction of the item, true prevents it being stepped on, false allows it to be stepped on.
     * @param player The player character in the current world.
     */
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

    /**
     * Returns the item name.
     * @return Returns name.
     */
    public String getItemName(){
        return this.name;
    }

    /**
     * Returns the item gold value.
     * @return Returns goldvalue
     */
    public int getCurrencyValue(){
        return this.goldValue;
    }

    /**
     * Pseudorandomly generates a number between the negative side of the max boundary to the positive side of the
     * max boundary.
     * @param max The max boundary of the number, a value of 25 would mean it generates a number between -25 to +25.
     * @return
     */
    public static int randomItemPositionGenerator (int max){
        return new Random().nextInt(2*max - 0) - max;
    }

    /**
     * Returns the dialogue box of an item
     * @return Returns display
     */
    public AbstractDialogBox getDisplay() {
        return this.display;
    }

    /**
     * Sets the player character interacting with the item
     * @param pl The player character
     */
    public void setPlayer(PlayerPeon pl) { 
        player = pl; 
    }

    /**
     * Charges the player based on the value of the item, then provides effects to the player based on the item
     * that was consumed/purchased.
     */
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
                case "Mysterious Vial":
                    int rng = new SecureRandom().nextInt(100-1);
                    if (rng <= 16){
                        this.player.reduceHealth(10);
                    } else if (rng > 16 && rng <= 32){
                        this.player.reduceHealth(20);
                    } else if (rng > 32 && rng <= 48){
                        this.player.reduceHealth(30);
                    } else if (rng > 48 && rng <= 64){
                        this.player.reduceHealth(40);
                    } else if (rng > 64 && rng <= 80){
                        this.player.reduceHealth(50);
                    } else if (rng > 80 && rng <= 90){
                        this.player.regenerateHealth(50);
                        this.player.addDamage(10);
                        this.player.addArmour(200);
                    } else if (rng > 90 && rng <= 100){
                        this.player.regenerateHealth(100);
                        this.player.addDamage(10);
                        this.player.addArmour(200);
                    }
                    break; 
                case "Iron Armour":
                    if (this.player.getArmour() < 2000){
                        this.player.addArmour(((IronArmour) this).getArmourValue());
                        PlayerPeon.addBuffArmourTotal(((IronArmour) this).getArmourValue());
                    }
                    break;
                case "Wooden Armour":
                    if (this.player.getArmour() < 2000){
                        this.player.addArmour(((WoodenArmour) this).getArmourValue());
                        PlayerPeon.addBuffArmourTotal(((WoodenArmour) this).getArmourValue());
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

    /**
     * Ontick iterator
     * @param i Long
     */
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

    /**
     * Interacts with an item, gets the dialogue associated with the itemn and displays it
     */
    public void interact() {
        this.display.getBox().setVisible(true);
        this.display.setVisibleTime(0);
        this.display.setShowing(true);
    }

    /**
     * Noftify touchdown checks mouse clicks
     * @param screenX the x position the mouse was pressed at
     * @param screenY the y position the mouse was pressed at
     * @param pointer
     * @param button  the button which was pressed
     */
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
}
