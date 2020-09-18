package deco2800.thomas.entities;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class NPCDialog extends AbstractDialogBox {
	
	// constructs NPC DailgoBox. 
	public NPCDialog(Object entity) {
		super(entity, "PolyHedron: Tutorial", "tutorial");
		// add first dialogue. 
		
		box.add("Dialogue Test").expand().center();
		box.row();
		box.setKeepWithinStage(true);
		show = false;
		time = 0;
		box.pack();
	}
	
}