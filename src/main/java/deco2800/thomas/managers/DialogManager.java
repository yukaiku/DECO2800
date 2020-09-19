package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class DialogManager extends TickableManager {

	public List<AbstractDialogBox> boxes;
	private AbstractWorld world;
	boolean ready;
	PlayerPeon player; 
	int wait;

	public DialogManager(AbstractWorld world,
			PlayerPeon player,  List<AbstractDialogBox> boxes) {
		this.world = world;
		this.player = player;
		this.boxes = boxes;
		wait = 0; 
	}
	
	public void showDialog(AbstractDialogBox dialogBox) {
		GameManager.get().getStage().addActor(dialogBox.getBox());
	}
	
	public void hideDialog(AbstractDialogBox dialogBox) {
		dialogBox.getBox().remove();
	}
	

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
					world.removeEntity((AbstractEntity) a.getEntity());
					a.setRemove(false);
					hideDialog(a);
				}
			}
		}
	}
}