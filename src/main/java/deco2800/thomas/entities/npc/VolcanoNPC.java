package deco2800.thomas.entities.npc;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.util.SquareVector;

public class VolcanoNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox tutorialDialogueBox;
    public static final int SPEECH_STAGE = 1;
    static boolean isActive = false;

    public VolcanoNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.tutorialDialogueBox = new NPCDialog(this,"Default");
    }

    public AbstractDialogBox getBox() {
        return tutorialDialogueBox;
    }


    public static void setIsActive(boolean value){
        isActive = value;
    }

    public static boolean getIsActive(){
        return isActive;
    }

    @Override
    public void interact() {
        setIsActive(true);
        if ((this.getName().equals("VolcanoQuestNPC1") || this.getName().equals("VolcanoQuestNPC2")) && SPEECH_STAGE >= 1) {
            ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("volcano"));
            ((NPCDialog) tutorialDialogueBox).addHealer();
            tutorialDialogueBox.setShowing(true);
        }
    }
}
