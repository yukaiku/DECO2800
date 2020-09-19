package deco2800.thomas.entities.NPC;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.util.SquareVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VolcanoNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox tutorialDialogueBox;
    public static int speechStage;
    static boolean isActive = false;

    public VolcanoNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.tutorialDialogueBox = new NPCDialog(this,"Default");
        speechStage = 1;
    }

    public AbstractDialogBox getBox() {
        return tutorialDialogueBox;
    }

    public void onTick(){

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
        ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("WASD"));
        tutorialDialogueBox.setShowing(true);
    }
}