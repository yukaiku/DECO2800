package deco2800.thomas.managers;

import deco2800.thomas.combat.skills.*;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.monsters.Orc;

import java.util.List;

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
//:todo Update player damage, and skill cooldown. Update spawnrate.
public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type = "";
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
        int enemiesMaxHealth = health;
        enemyManager.getEnemyConfig(this.type+"Orc").setMaxHealth(enemiesMaxHealth);
    }

    /***
     * Sets the wild spawn spawn rate
     */
    public void setWildSpawnRate(float spawnRate){
        EnemyPeon orc = enemyManager.getEnemyConfig(this.type+"Orc");
        Orc orc1 = (Orc)orc;
        orc1.setSpawnRate(spawnRate);
    }

    /***
     * Sets the world type
     * @param type String of the world to be set
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
     * @param difficulty int
     */
    public void setPlayerHealth(int difficulty) {
        //Sets the player max health
        playerPeon.setMaxHealth(25*difficulty);
        playerPeon.setCurrentHealthValue(25*difficulty);
    }

    /***
     * Changes the wizard skill damage
     */
    public void setWizardSkill(){
        List<AbstractSkill> wizardSkills = playerPeon.getWizardSkills();
        for(AbstractSkill wizardSkill : wizardSkills){
            switch (wizardSkill.getTexture()){
                case "iceballIcon": // Default 50
                    if(getWorldType().equals("desert")){
                        //More damage to desert with water skill
                        IceballSkill.setDamageMultiplier(((IceballSkill) wizardSkill).getDamageMultiplier()*2);
                    }
                    return;
                case "fireballIcon": //Default 20
                    if(getWorldType().equals("tundra")){
                        //More damage to tundra with fire skill
                        FireballSkill.setDamageMultiplier(((FireballSkill) wizardSkill).getDamageMultiplier()*2);
                    }
                    return;
                case "stingIcon": //Default 50
                    return;
                default:
                    return;

            }
        }
    }

    /***
     * Changes the mech cooldown time
     */
    public void setMechSkill(){
        AbstractSkill mechSkill = playerPeon.getMechSkill();
        if(mechSkill.getTexture() == "explosionIcon"){ //Default 160
            if(getWorldType().equals("tundra")){
                //More damage to tundra with fire skill
                FireBombSkill.setDamageMultiplier(((FireBombSkill) mechSkill).getDamageMultiplier()*2);
            }
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

        //Set skills damage multiplier
        setWizardSkill();
        setMechSkill();

        switch (getWorldType()) {
            // Difficulty Settings for each world
            case "swamp":
                setPlayerHealth(4);
                setWildSpawnRate(0.05f);
                enemyManager.getBoss().setMaxHealth(100);
                enemyManager.setWildEnemyCap(5);
                break;
            case "tundra":
                setPlayerHealth(4);
                setWildSpawnRate(0.06f);
                enemyManager.getBoss().setMaxHealth(150);
                enemyManager.setWildEnemyCap(6);
                break;
            case "desert":
                setPlayerHealth(4);
                setWildSpawnRate(0.07f);
                enemyManager.getBoss().setMaxHealth(300);
                break;
            case "volcano":
                setPlayerHealth(4);
                setWildSpawnRate(0.08f);
                enemyManager.getBoss().setMaxHealth(750);
                enemyManager.setWildEnemyCap(8);
                break;
        }

    }

    /***
     * Manages actions happening as time passes in game world.
     * @param i Current game Tick
     */
    @Override
    public void onTick(long i) {
        //No on tick methods needed for now
    }
}
