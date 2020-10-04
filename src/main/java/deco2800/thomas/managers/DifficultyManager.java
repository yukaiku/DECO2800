package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.entities.enemies.EnemyPeon;

import java.util.List;

public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type = "";
    private int enemiesMaxHealth;
    private int playerMaxHealth = 0;
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
     */
    public void setDifficultyLevel(String type) {
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        List<EnemyPeon> wildEnemiesAlive = enemyManager.getWildEnemiesAlive();
        int wildEnemyCap = enemyManager.getWildEnemyCap();
        switch (type) {
            // Difficulty Settings for each world
            //TODO: Update with more difficulty
            case "Swamp":
                enemiesMaxHealth = enemyManager.getEnemyConfig("swampOrc").getMaxHealth();
                break;
            case "Tundra":
                enemiesMaxHealth = enemyManager.getEnemyConfig("tundraOrc").getMaxHealth();
                break;
            case "Desert":
                enemiesMaxHealth = enemyManager.getEnemyConfig("desertOrc").getMaxHealth();
                break;
            case "Volcano":
                enemiesMaxHealth = enemyManager.getEnemyConfig("volcanoOrc").getMaxHealth();
                break;
        }
        enemiesMaxHealth = enemiesMaxHealth/(5-getDifficultyLevel());
        this.type=type;
        //Adjusts Enemy Health based on stage at
        for (EnemyPeon wildEnemies: wildEnemiesAlive) {
            if(wildEnemies.getCurrentHealth() > enemiesMaxHealth){
                wildEnemies.setCurrentHealthValue(enemiesMaxHealth);
                wildEnemies.setMaxHealth(enemiesMaxHealth);
            }
        }
        playerPeon.setMaxHealth((100/4)*(getDifficultyLevel()));
        playerMaxHealth = playerPeon.getMaxHealth();
        if(playerPeon.getCurrentHealth() > playerMaxHealth){
            //Sets max health based off number of orbs starting from 25 to 100
            playerPeon.setCurrentHealthValue(playerMaxHealth);
        }

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
