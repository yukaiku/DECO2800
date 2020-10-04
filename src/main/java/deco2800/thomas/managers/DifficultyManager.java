package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;

public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type = "";
    private int enemiesMaxHealth = 0;
    private int playerMaxHealth = 0;
    private EnemyManager enemyManager;
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
     * Sets the WildSpawnMaxHealth
     */
    public void setWildSpawnMaxHealth(int health){
        enemiesMaxHealth = health;
        enemyManager.getEnemyConfig(this.type+"Orc").setMaxHealth(enemiesMaxHealth);
    }

    /***
     * Getter for Wild Spawn Max Health
     * @return wildSpawnMaxHealth
     */
    public int getWildSpawnMaxHealth(){
        return enemyManager.getEnemyConfig(this.type+"Orc").getMaxHealth();
    }

    /***
     * Sets the difficulty of the game based off the current world
     */
    public void setDifficultyLevel(String type) {
        enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        if(!this.type.equals(type)){
            this.type = type.toLowerCase();
            int wildEnemyCap = enemyManager.getWildEnemyCap();

            //Set Wild Enemy Cap
            enemyManager.setWildEnemyCap(wildEnemyCap*getDifficultyLevel());
            //Set Wild Enemy Health
            setWildSpawnMaxHealth(enemyManager.getEnemyConfig(this.type+"Orc").getMaxHealth()/(5-getDifficultyLevel()));
            System.out.println(getWildSpawnMaxHealth());
            //Sets the player max health
            playerPeon.setMaxHealth((100/4)*(getDifficultyLevel()));
            playerMaxHealth = playerPeon.getMaxHealth();
            if(playerPeon.getCurrentHealth() > playerMaxHealth){
                //Sets max health based off number of orbs starting from 25 to 100
                playerPeon.setCurrentHealthValue(playerMaxHealth);
            }
        }

        switch (type) {
            // Difficulty Settings for each world
            //TODO: Update with more difficulty
            case "swamp":
                break;
            case "tundra":
                break;
            case "desert":
                break;
            case "volcano":
                break;
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
