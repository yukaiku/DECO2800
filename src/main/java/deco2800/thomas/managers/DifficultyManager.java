package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;

public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type;

    public DifficultyManager(){

    }

    public int getDifficultyLevel() {
        return QuestTracker.orbTracker().size();
    }

    public void setPlayerEntity(PlayerPeon playerEntity) {
        playerPeon = playerEntity;
    }

    public void setDifficultyLevel(String type) {
        //Sets max health based off number of orbs starting from 25 to 100
        playerPeon.setCurrentHealthValue((100/4)*(getDifficultyLevel()+1));
        playerPeon.setMaxHealth((100/4)*(getDifficultyLevel()+1));
    }

    @Override
    public void onTick(long i) {

    }
}
