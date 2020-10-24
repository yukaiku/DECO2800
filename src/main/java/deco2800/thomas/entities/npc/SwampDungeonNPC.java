package deco2800.thomas.entities.npc;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.util.SquareVector;

public class SwampDungeonNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox dialogueBox;
    public static final int SPEECH_STAGE = 1;
    static boolean isActive = false;

    public SwampDungeonNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.dialogueBox = new NPCDialog(this, "Default", "I am not a liar!");
    }

    public AbstractDialogBox getBox() {
        return dialogueBox;
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
        if (SPEECH_STAGE >= 1) {
            ((NPCDialog) dialogueBox).setString(PlayerPeon.getDialogue(this.getName()));
            dialogueBox.setShowing(true);
        }
    }

}
