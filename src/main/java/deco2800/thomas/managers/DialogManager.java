package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.managers.GameManager;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.List;

public class DialogManager extends TickableManager {
	
	public List<AbstractDialogBox> boxes;
	
	public DialogManager(List<AbstractDialogBox> boxes) {
		// list of all abstract dialog boxes for each item.
		this.boxes  = boxes;
	}

	public void showDialog(AbstractDialogBox dialogBox){
		// add name, description etc.
		dialogBox.getBox().add(dialogBox.getName());
		drawDialog(dialogBox);
	}

	// check doesn't throw an exception for boxes that aren't showing.
	public void hideDialog(AbstractDialogBox dialogBox){
		dialogBox.getBox().remove();
	}

	// adds dialog box onto stage. 
	public void drawDialog(AbstractDialogBox dialogBox){
		GameManager.get().getStage().addActor(dialogBox.getBox());
	}
	
	@Override
	public void onTick(long i) {
		for (AbstractDialogBox a: boxes) {
			if (a.getVisible()) {
				showDialog(a);
			}
			else if (!a.getVisible()) {
				hideDialog(a);
			}
		}
	}
}