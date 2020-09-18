package deco2800.thomas.entities.NPC;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.util.SquareVector;

public class TutorialNPC extends NonPlayablePeon implements Interactable {
    
    AbstractDialogBox dialogue;
    
    public TutorialNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.dialogue = new NPCDialog(this);
        // create linked list of dialogue? 
        
    }
    
    public AbstractDialogBox getBox() {
        return dialogue;
    }
    
    public void onTick(){
        dialogue.setShowing(false);
    }
    
    @Override
    public void interact() {
        // tutorial vibes
        String message1 = "";
        message1 = PlayerPeon.getDialogue("welcome");
        
        
        //Display message
        dialogue.setShowing(true);
        String message2 = PlayerPeon.getDialogue("WASD");
        String message3 = PlayerPeon.getDialogue("attack");
        String message4 = PlayerPeon.getDialogue("orb");
        String message5 = PlayerPeon.getDialogue("congrats");

        System.out.println(message1);
        System.out.println(message2);
        System.out.println(message3);
        System.out.println(message4);
    }
}
