package deco2800.thomas.entities.npc;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.NPCDialog;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.util.SquareVector;

public class TutorialNPC extends NonPlayablePeon implements Interactable {

    AbstractDialogBox tutorialDialogueBox;
    private static int speechStage;
    static boolean isActive = false;

    public TutorialNPC(String name, SquareVector position, String texture) {
        super(name, position, texture);
        this.tutorialDialogueBox = new NPCDialog(this,"Default");
        speechStage = 0;
    }
    
    public AbstractDialogBox getBox() {
        return this.tutorialDialogueBox;
    }
    
    public void onTick(){
        // tick tick tick 
    }

    public static boolean getIsActive(){
        return isActive;
    }

    public static void setIsActive(boolean value){
        isActive = value;
    }

    public static int getSpeechStage(){ return speechStage; }

    public static void increaseSpeechStage(){speechStage += 1;}

    @Override
    public void interact() {
        setIsActive(true);
        ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("plot"));
        tutorialDialogueBox.setShowing(true);
        if (speechStage >= 4){
            setIsActive(true);
            ((NPCDialog) tutorialDialogueBox).setString(PlayerPeon.getDialogue("congrats"));
            tutorialDialogueBox.setShowing(true);
        }
    }
}
