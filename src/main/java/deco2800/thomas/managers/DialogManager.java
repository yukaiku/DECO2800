package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.managers.GameManager;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.List;

public class DialogManager extends TickableManager {
	public Window box; 
	public boolean show; 
	
	public DialogManager() {
		// create all the boxes. 
		box = new Window("Item", GameManager.get().getSkin());
		show = false;
	}
	
	public void showDialog(AbstractDialogBox dialogBox){
		// add name, description etc. 
		box.add(dialogBox.getDialog());
		show = true; 
		drawDialog();
	}
	
	public void hideDialog(){
		box.remove();
	}
	
	public void drawDialog(){
		GameManager.get().getStage().addActor(box);
	}
	
	@Override
	public void onTick(long i) {
		if (!show) {
			hideDialog();
		}
	}
}