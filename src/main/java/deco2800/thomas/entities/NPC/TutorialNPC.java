package deco2800.thomas.entities.NPC;

import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.util.SquareVector;

public class TutorialNPC extends NonPlayablePeon implements Interactable {
    public TutorialNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
    }

    @Override
    public void interact() {
        // tutorial vibes
        String message = "";
        message = PlayerPeon.getDialogue("welcome");
        //Display message

        String message1 = PlayerPeon.getDialogue("WASD");
        String message2 = PlayerPeon.getDialogue("attack");
        String message3 = PlayerPeon.getDialogue("orb");
        String message4 = PlayerPeon.getDialogue("congrats");

        System.out.println(message1);
        System.out.println(message2);
        System.out.println(message3);
        System.out.println(message4);
    }
}
