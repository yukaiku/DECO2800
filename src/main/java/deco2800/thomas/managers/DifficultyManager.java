package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;

/**
 * DifficultyManager handles the difficulty curve of the game
 * DifficultyManager is intialised once and the instance is get every time a new world is
 * made to update the variables
 *
 * It handles the manipulation of:
 * Wildspawn health
 * Player health
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/Difficulty%20Curve
 */
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
     * Sets the world type
     * @param type
     */
    public void setWorldType(String type){
        this.type = type.toLowerCase();
    }

    /***
     * Returns world type
     * @return world type
     */
    public String getWorldType(){
        return this.type;
    }

    /***
     * Changes the player max health based on difficulty
     * @param playerPeon    PlayerEntity
     */
    public void playerHealth(PlayerPeon playerPeon, int difficulty) {
        //Sets the player max health
        playerPeon.setMaxHealth(25*difficulty);
        playerMaxHealth = playerPeon.getMaxHealth();
        if(playerPeon.getCurrentHealth() > playerMaxHealth){
            //Sets max health based off number of orbs starting from 25 to 100
            playerPeon.setCurrentHealthValue(playerMaxHealth);
        }
    }

    /***
     * Sets the difficulty of the game based off the current world
     */
    public void setDifficultyLevel(String type) {
        enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        if(!getWorldType().equals(type)){
            setWorldType(type);
            int wildEnemyCap = enemyManager.getWildEnemyCap();
            String orcType = getWorldType()+"Orc";

            //Set Wild Enemy Cap
            enemyManager.setWildEnemyCap(wildEnemyCap+getDifficultyLevel());

            //Set Wild Enemy Health
            setWildSpawnMaxHealth(enemyManager.getEnemyConfig(orcType).getMaxHealth() /(5-getDifficultyLevel()));
        }

        switch (getWorldType()) {
            // Difficulty Settings for each world
            //TODO: Update with more difficulty
            case "swamp":
                playerHealth(playerPeon, 2);
                break;
            case "tundra":
                playerHealth(playerPeon, 3);
                break;
            case "desert":
                playerHealth(playerPeon, 4);
                break;
            case "volcano":
                playerHealth(playerPeon, 5);
                break;
        }

    }

    /***
     * Manages actions happening as time passes in game world.
     * @param i Current game Tick
     */
    @Override
    public void onTick(long i) {
    }
}
