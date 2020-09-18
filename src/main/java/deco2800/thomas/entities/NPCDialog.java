package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.NPC.TutorialNPC;

import java.util.HashMap;

public class NPCDialog extends AbstractDialogBox {

	TextButton button;
	HashMap<String, String> tutorialDialogue = new HashMap<>();
	// constructs NPC DailgoBox. 
	public NPCDialog(Object entity, String string) {
		super(entity, "PolyHedron: Tutorial", "tutorial");
		// add first dialogue.
		this.tutorialDialogue.put("WASD", PlayerPeon.getDialogue("WASD"));
		this.tutorialDialogue.put("attack", PlayerPeon.getDialogue("attack"));
		this.tutorialDialogue.put("orb", PlayerPeon.getDialogue("orb"));
		this.tutorialDialogue.put("congrats", PlayerPeon.getDialogue("congrats"));
		this.tutorialDialogue.put("plot", PlayerPeon.getDialogue("plot"));
		box.add(string).expand().center();
		box.row();
		box.setKeepWithinStage(true);
		button = new TextButton("Next",skin);
		box.add(button).expand().center();
		box.addListener(a);
		show = false;
		time = 0;
		box.pack();
		box.setPosition((Gdx.graphics.getWidth() - box.getWidth())/2,(Gdx.graphics.getHeight() - box.getHeight())/2 );
	}

	public void setString(String str){
		box.reset();
		box.add(str).expand().center();
		button = new TextButton("Next",skin);
		box.add(button).expand().center();
		box.pack();
	}

	public String getLine(String str){
		return this.tutorialDialogue.get(str);
	}

	ChangeListener a = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (TutorialNPC.speechStage < 4) {
				TutorialNPC.speechStage += 1;
			}
			switch (TutorialNPC.speechStage) {
				case 1:
					setString(getLine("attack"));
					break;
				case 2:
					setString(getLine("orb"));
					break;
				case 3:
					setString(getLine("plot"));
					break;
				case 4:
					setString(getLine("congrats"));
					box.removeActor(button);
					button = new TextButton("Close",skin);
					box.add(button).expand().center();
					box.addListener(b);
					box.pack();
					break;
				default:
					setString(getLine("congrats"));
			}
		}
	};
	ChangeListener b = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			box.remove();
		}
	};
	
}