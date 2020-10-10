package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class DialogManager extends TickableManager {

	public List<AbstractDialogBox> boxes;
	private AbstractWorld world;
	boolean ready;
	PlayerPeon player; 
	

	/**
	 * Constructs a dialog manager. 
	 * @param world the game world 
	 * @param player player
	 * @param boxes List of AbstractDialogBoxes in this world. 
	 */
	public DialogManager(AbstractWorld world,
			PlayerPeon player,  List<AbstractDialogBox> boxes) {
		this.world = world;
		this.player = player;
		this.boxes = boxes;
	}

	/**
	 * Displays the given dialogBox on Game Screen. 
	 * @param dialogBox dialogBox to display on Screen.
	 */
	public void showDialog(AbstractDialogBox dialogBox) {
		GameManager.get().getStage().addActor(dialogBox.getBox());
	}

	/**
	 * Hides the dialogBox from the Game Screen. 
	 * @param dialogBox dialogBox to hide from Screen. 
	 */
	public void hideDialog(AbstractDialogBox dialogBox) {
		dialogBox.getBox().remove();
	}
	
	/**
	 * Returns whether the DialogManager is ready to show AbstractDialogBoxes.
	 */
	public boolean getReady(){
		return ready; 
	}
	/**
	 * Manages actions happening as time passes in game world. 
	 * @param i Current game Tick 
	 */
	@Override
	public void onTick(long i) {
		if (GameManager.get().getStage() != null && !ready) {
			ready = true;
		}
		if (ready) {
			int numShowing = 0;
			for (AbstractDialogBox a: boxes) {
				if (a.isShowing() && numShowing < 1){
					showDialog(a);
					numShowing++;
				}
				if (a.isShowing() && a.getVisibleTime() >= 750) {
					a.setShowing(false);
					hideDialog(a);
				}
				if (a.getRemove()){
					ItemBox b = (ItemBox) a;
					world.removeEntity(b.getItem());
					a.setRemove(false);
					hideDialog(a);
				}
			}
		}
	}
}