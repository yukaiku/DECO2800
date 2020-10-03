package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.entities.enemies.EnemyPeon;
import org.lwjgl.Sys;

import java.util.List;

public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type;

    /***
     * Constructs a DifficultyManager manager.
     */
    public DifficultyManager(){
    }

    /***
     * Returns the difficulty level 1-4
     * @return difficulty level
     */
    public int getDifficultyLevel() {
        return QuestTracker.orbTracker().size()+1;
    }

    /***
     * Update the player entity as a new entity is created in each world
     * @param playerEntity - entity of the player
     */
    public void setPlayerEntity(PlayerPeon playerEntity) {
        playerPeon = playerEntity;
    }

    /***
     * Sets the difficulty of the game based off the current world
     * @param type
     */
    public void setDifficultyLevel(String type) {
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        List<EnemyPeon> wildEnemiesAlive = enemyManager.getWildEnemiesAlive();
        int wildEnemyCap = enemyManager.getWildEnemyCap();
        this.type=type;
        switch (type) {
            // Difficulty Settings for each world
            //TODO: Update with more difficulty
            case "Swamp":
                break;
            case "Tundra":
                break;
            case "Desert":
                break;
            case "Volcano":
                break;
        }
        //Adjusts Enemy Health based on stage at
        for (EnemyPeon wildEnemies: wildEnemiesAlive) {
            wildEnemies.setCurrentHealthValue(wildEnemies.getCurrentHealth()/(5-getDifficultyLevel()));
        }
        //Sets max health based off number of orbs starting from 25 to 100
        playerPeon.setCurrentHealthValue((100/4)*(getDifficultyLevel()));
        playerPeon.setMaxHealth((100/4)*(getDifficultyLevel()));
    }

    /***
     * Manages actions happening as time passes in game world.
     * @param i Current game Tick
     */
    @Override
    public void onTick(long i) {
        setDifficultyLevel(type);
    }
}
