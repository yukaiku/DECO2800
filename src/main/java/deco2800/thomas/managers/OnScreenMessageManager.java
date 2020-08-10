package deco2800.thomas.managers;

import deco2800.thomas.entities.Peon;
import deco2800.thomas.handlers.KeyboardManager;
import deco2800.thomas.observers.KeyTypedObserver;

import java.util.ArrayList;
import java.util.List;

public class OnScreenMessageManager extends AbstractManager implements KeyTypedObserver {
    private List<String> messages = new ArrayList<String>();
    boolean isTyping = false;
    String unsentMessage = "";

    public OnScreenMessageManager() {
        GameManager.get().getManager(KeyboardManager.class).registerForKeyTyped(this);
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
			GameManager.get().getCamera().setPotate(true);
			return;
		}

		if (isTyping) {
			if (character == '`') {
				isTyping = false;
				GameManager.get().getCamera().setPotate(false);
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

				} else	if (unsentMessage.startsWith("/1")) { // enable GOD mode
					GameManager.get().getWorld().addEntity(new Peon(0f, 0f, 0.05f));
				} else if (unsentMessage.startsWith("/resources")) {
					// Display resources in the console
					this.addMessage(String.format("Stone: %s", GameManager.getManagerFromInstance(InventoryManager.class).getStone()));
				} else {
					GameManager.get().getManager(NetworkManager.class).sendChatMessage(unsentMessage);
				}
				GameManager.get().getCamera().setPotate(false);
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
