package deco2800.thomas.entities.items;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.entities.*;
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
        System.out.println("-- Charge player");
        if (PlayerPeon.checkBalance() > 0) {
            System.out.println(PlayerPeon.checkBalance());
            PlayerPeon.debit(goldValue);

            if (this.getItemName().equals("Health Potion")) {
                this.player.regenerateHealth(40);
            }
            if (this.getItemName().equals("Iron IronArmour") && this.player.getArmour() < 2000) {
                this.player.addArmour(1000);
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
        this.display.setVisibleTime(0);
        this.display.setShowing(true);
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(),
                Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
        float playerCol = this.player.getPosition().getCol();
        float playerRow = this.player.getPosition().getRow();
        float itemCol = this.getPosition().getCol();
        float itemRow = this.getPosition().getRow();
        
        boolean isCloseCol =
                (clickedPosition[0] == this.getCol()
                        || clickedPosition[0] == this.getRow() - 1
                        || clickedPosition[0] == this.getRow() + 1);

        boolean isCloseRow = (clickedPosition[1] == this.getRow() ||
                clickedPosition[1] == this.getRow() - 1 ||
                clickedPosition[1] == this.getRow() + 1);
        
        if (isCloseCol && isCloseRow) {
            if (((itemCol + 1) <= playerCol) && ((itemRow+1) <= playerRow)) {
                interact();
            }
            if ((itemCol - 1) <= playerCol && ((itemRow -1) <= playerRow)) {
                interact();
            }
            if ((itemCol - 1) <= playerCol && ((itemRow + 1) <= playerRow)) {
                interact();
            }
            if ((itemCol + 1) <= playerCol && ((itemRow - 1) <= playerRow)) {
                interact();
            }
        }
    }

    /**
     * Remove touch down listener.
     */
    @Override
    public void dispose() {
        GameManager.getManagerFromInstance(InputManager.class).removeTouchDownListener(this);
    }
}
