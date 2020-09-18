package deco2800.thomas.entities.NPC;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.util.SquareVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TundraNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox tutorialDialogueBox;
    public static int speechStage;
    static boolean isActive = false;

    public TundraNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.tutorialDialogueBox = new NPCDialog(this,"Default");
        speechStage = 1;
    }

    public AbstractDialogBox getBox() {
        return tutorialDialogueBox;
    }

    public void onTick(){

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
        if (this.getName() == "TundraQuestNPC1" && speechStage >= 1) {
            ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("tundra"));
            tutorialDialogueBox.setShowing(true);
        } else if (this.getName() == "TundraQuestNPC2" && speechStage >= 1){
            ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("tundra"));
            tutorialDialogueBox.setShowing(true);
        }
    }
}
