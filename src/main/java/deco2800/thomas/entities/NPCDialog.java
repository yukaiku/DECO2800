package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.agent.LoadedPeon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.npc.*;

public class NPCDialog extends AbstractDialogBox {

	// Text button
	private TextButton button;

	/**
	 * Constructs NPC dialogue with a string
	 * @param entity The NPC entity
	 * @param string The string in the dialog
	 */
	public NPCDialog(Object entity, String string) {
		super(entity, "PolyHedron: Tutorial", "tutorial");
		// add first dialogue.
		box.add(string).expand().center();
		box.row();
		box.setKeepWithinStage(true);
		button = new TextButton("Next",skin);
		box.add(button).expand().center();
		box.addListener(a);
		time = 0;
		setShowing(false);
		box.pack();
		box.setPosition((Gdx.graphics.getWidth() - box.getWidth())/2,(Gdx.graphics.getHeight() - box.getHeight())/2 );
	}

	/**
	 * Constructs NPC dialogue with a string and a box title
	 * @param entity The NPC entity
	 * @param string The string in the dialog
	 * @param boxName The box title
	 */
	public NPCDialog(Object entity, String string, String boxName) {
		super(entity, boxName, "tutorial");
		// add first dialogue.
		box.add(string).expand().center();
		box.row();
		box.setKeepWithinStage(true);
		button = new TextButton("Next",skin);
		box.add(button).expand().center();
		box.addListener(b);
		time = 0;
		setShowing(false);
		box.pack();
		box.setPosition((Gdx.graphics.getWidth() - box.getWidth())/2,(Gdx.graphics.getHeight() - box.getHeight())/2 );
	}

	/**
	 * Sets the string in the dialogue box
	 * @param str The string in the dialogue box
	 */
	public void setString(String str) {
		box.reset();
		box.add(str).expand().center();
		button = new TextButton("Next", skin);
		box.row();
		box.add(button).expand().center();
		box.pack();
	}

	/**
	 * Adds a healer button to the dialogue box
	 */
	public void addHealer(){
		TextButton button2 = new TextButton("Heal: 100 Gold", skin);
		if (PlayerPeon.checkBalance() >= 100) {
			button2.addListener(c);
		}
		box.row();
		box.add(button2).expand().center();
		box.pack();
	}

	/**
	 * Changes listener based on clicking the text button "Next"
	 */
	ChangeListener a = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (TutorialNPC.getIsActive()) {
				if (TutorialNPC.getSpeechStage() < 4) {
					TutorialNPC.increaseSpeechStage();
				}
				switch (TutorialNPC.getSpeechStage()) {
					case 1:
						setString(PlayerPeon.getDialogue("WASD"));
						button.setText("next");
						break;
					case 2:
						setString(PlayerPeon.getDialogue("attack"));
						break;
					case 3:
						setString(PlayerPeon.getDialogue("orb"));
						break;
					case 4:
						setString(PlayerPeon.getDialogue("congrats"));
						box.removeListener(this);
						box.addListener(b);
						break;
					default:
						setString(PlayerPeon.getDialogue("congrats"));
						break;
				}
			}

			if (TundraNPC.getIsActive() && TundraNPC.SPEECH_STAGE >= 1) {
				setString(PlayerPeon.getDialogue("tundra"));
				box.removeListener(this);
				box.addListener(b);
			}

			if (VolcanoNPC.getIsActive() && VolcanoNPC.SPEECH_STAGE >= 1){
				setString(PlayerPeon.getDialogue("volcano"));
				box.removeListener(this);
				box.addListener(b);
			}

			if (SwampNPC.getIsActive() && SwampNPC.SPEECH_STAGE >= 1){
				setString(PlayerPeon.getDialogue("swamp"));
				box.removeListener(this);
				box.addListener(b);
			}

			if (SwampDungeonNPC.getIsActive() && SwampDungeonNPC.SPEECH_STAGE >= 1) {
				box.removeListener(this);
				box.addListener(b);
			}

			if (DesertNPC.getIsActive() && DesertNPC.SPEECH_STAGE >= 1){
				setString(PlayerPeon.getDialogue("desert"));
				box.removeListener(this);
				box.addListener(b);
			}
		}
	};

	/**
	 * Changes listener based on interacting with the text button "Close"
	 */
	ChangeListener b = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			setShowing(false);
			box.reset();
			box.remove();
			DesertNPC.setIsActive(false);
			TundraNPC.setIsActive(false);
			VolcanoNPC.setIsActive(false);
			SwampNPC.setIsActive(false);
			SwampDungeonNPC.setIsActive(false);
			TundraNPC.setIsActive(false);
		}
	};

	/**
	 * changes listener based on clicking the text button "Heal"
	 */
	ChangeListener c = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (PlayerPeon.checkBalance() >= 100) {
				LoadedPeon.healPlayer(100);
				LoadedPeon.debit(100);
				setShowing(false);
				box.reset();
				box.remove();
			}
		}
	};
}