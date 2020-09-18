package deco2800.thomas.entities.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.*;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import java.util.Random;

public class Item extends StaticEntity implements TouchDownObserver {

    public static final String ENTITY_ID_STRING = "item";
    protected String name;
    protected int goldValue;
    protected AbstractDialogBox display; 
    protected PlayerPeon player; 
    boolean ready;

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
        return display; 
    }
    
    public void setPlayer(PlayerPeon pl) { 
        player = pl; 
    }

    public void setObstructions() {
        try {
            Tile feet = GameManager.get().getWorld().getTile(this.getCol(),
                    this.getRow());
            feet.setObstructed(true);
            GameManager.get().getWorld().updateTile(feet);
            ready = true;
            GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        } catch (NullPointerException e) {
            // do nothing, it'll work eventually.
        }
    }
    
    @Override
    public void onTick(long i) {
        if (GameManager.get().getWorld() != null && !ready) {
            setObstructions();
        }
        if (display.isShowing()) {
            display.setVisibleTime(display.getVisibleTime() + 1);
        }
    }
    
    public void interact() {
        display.setVisibleTime(0);
        display.setShowing(true);
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
                        || clickedPosition[0] <= this.getRow() - 1
                        || clickedPosition[0] <= this.getRow() + 1);

        boolean isCloseRow = (clickedPosition[1] == this.getRow() ||
                clickedPosition[1] <= this.getRow() - 1 ||
                clickedPosition[1] <= this.getRow() + 1);

        // if click is close enough, then player should also be somewhat close. 
        if (isCloseCol && isCloseRow) {
            
            if (((itemCol + 1) <= playerCol) && ((itemRow+1) <= playerRow)) {
                System.out.println("Interacting 1!");
                interact();
            }
            
            if ((itemCol - 1) <= playerCol && ((itemRow -1) <= playerRow)) {
                System.out.println("Interacting 2!");
                interact();
            }
            
            if ((itemCol - 1) <= playerCol && ((itemRow + 1) <= playerRow)) {
                System.out.println("Interacting 2!");
                interact();
            }
            
            if ((itemCol + 1) <= playerCol && ((itemRow - 1) <= playerRow)) {
                System.out.println("Interacting 2!");
                interact();
            }
        }
    }
    
}
