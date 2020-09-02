package deco2800.thomas.managers;

import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.handlers.KeyboardManager;
import deco2800.thomas.observers.KeyTypedObserver;

import java.util.ArrayList;
import java.util.List;

public class OnScreenMessageManager extends AbstractManager implements KeyTypedObserver {
	private List<String> messages = new ArrayList<String>();
	private List<String> dialogues = new ArrayList<String>();
	boolean isTyping = false;
	String unsentMessage = "";

	public OnScreenMessageManager() {
		GameManager.get().getManager(KeyboardManager.class).registerForKeyTyped(this);
	}

	public String getDialogue(int target) {
    	dialogues.add("Welcome to (Game Name) the world has been devastated with the " +
				"re-emergence of the five pythagoras orbs. In order to save this world, " +
				"you will need to collect all the orbs and restore balance to the world.");
    	dialogues.add("To move your character press W for up, S for down, A for left, D for right, please move to the " +
				"checkpoint marked with a flag to proceed.");
    	dialogues.add("An enemy is in front of you, get closer and click (attack key) to kill the monster");
    	dialogues.add("There is an orb in front of you, pick it up by interacting with it.");
    	dialogues.add("Congratulations on completing the tutorial, would you like to move to the next stage or " +
				"redo the tutorial?");
    	dialogues.add("I am a Pyromancer,, pick me and I'll burn all that stands before you to ashes.");
    	dialogues.add("I am a Hydromancer , pick me and I'll drown all our enemies.");
    	dialogues.add("I am a Anemancer, pick me and I'll unleash a hurricane on our foes.");
    	dialogues.add("I am a Geomancer, pick me and I'll crush all monsters with mother earth.");
    	dialogues.add("I am the shield knight, nothing shall get past my shield.");
    	dialogues.add("I am the sword knight, i will make quick work of your enemies.");
    	dialogues.add("Welcome adventure to (zone name) , to complete this stage, you " +
				"will have to locate the orb of (depend on zone). The monsters here are " +
				"vulnerable to (element) and have high (attack/defense) but low (attack/defense). " +
				"Choose your character wisely.");
    	dialogues.add("Congratulations for collecting the orb and completing the quest, you will now proceed on " +
				"to the next stage. ");
    	dialogues.add("ROAR!!!");
    	dialogues.add("GRRRRRR");
    	dialogues.add("Too bad, you died, would you like to restart from your previous checkpoint or start anew?");
    	dialogues.add("Congratulations hero, you have collected all the orbs and restored peace to the world.");
    	return dialogues.get(target);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void addMessage(String message) {
		if (messages.size() > 20) {
			messages.remove(0);
		}
		messages.add(message);
	}

	public boolean isTyping() {
		return isTyping;
	}

	public String getUnsentMessage() {
		if (unsentMessage.equals("")) {
			return "Type your message";
		}
		return unsentMessage;
	}

	@Override
	public void notifyKeyTyped(char character) {
		if (character == 't' && !isTyping) {
			isTyping = true;
			return;
		}

		if (isTyping) {
			if (character == '`') {
				isTyping = false;
			} else if (character == '\b') {
				if (unsentMessage.length() > 0) {
					unsentMessage = unsentMessage.substring(0, unsentMessage.length() - 1); // Backspace
				}
			} else if (character == '\r') {
				isTyping = false;
				if (unsentMessage.startsWith("/duck")) { // enable GOD mode
					for (int i = 0; i < 1000; ++i) {
						GameManager.get().getWorld().addEntity(new Peon(0f, 0f, 0.05f));
					}

				} else if (unsentMessage.startsWith("/1")) { // enable GOD mode
					GameManager.get().getWorld().addEntity(new Peon(0f, 0f, 0.05f));
				} else if (unsentMessage.startsWith("/resources")) {
					// Display resources in the console
					this.addMessage(String.format("Stone: %s", GameManager.getManagerFromInstance(InventoryManager.class).getStone()));
				} else {
					GameManager.get().getManager(NetworkManager.class).sendChatMessage(unsentMessage);
				}
				unsentMessage = "";
			} else {
				// Accept input
				if (character < '!' || character > 'z') {
					return;
				}
				unsentMessage += character;
			}
		}
	}
}
