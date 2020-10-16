package deco2800.thomas.entities.npc;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.util.SquareVector;

public class SwampNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox tutorialDialogueBox;
    public static final int SPEECH_STAGE = 1;
    static boolean isActive = false;

    public SwampNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.tutorialDialogueBox = new NPCDialog(this,"Default");
    }

    public AbstractDialogBox getBox() {
        return tutorialDialogueBox;
    }


    public static boolean getIsActive(){
        return isActive;
    }

    public static void setIsActive(boolean value){
        isActive = value;
    }

    @Override
    public void interact() {
        setIsActive(true);
        if ((this.getName().equals("SwampQuestNPC1") || this.getName().equals("SwampQuestNPC2")) && SPEECH_STAGE >= 1) {
            ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("swamp"));
            ((NPCDialog) tutorialDialogueBox).addHealer();
            tutorialDialogueBox.setShowing(true);
        }
    }
}
