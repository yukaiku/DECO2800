package deco2800.thomas.entities.NPC;

import deco2800.thomas.util.SquareVector;

public class TutorialNPC extends NonPlayablePeon {
    public TutorialNPC(String name, SquareVector position) {
        super(name, position);
    }

    @Override
    public void interact() {
        // tutorial vibes
        String message = "";
        message = PlayerPeon.getDialogue("welcome");
        //Display message

        //PlayerPeon.getDialogue("WASD");
        //PlayerPeon.getDialogue("attack");
        //PlayerPeon.getDialogue("orb");
        //PlayerPeon.getDialogue("congrats");
    }
}
