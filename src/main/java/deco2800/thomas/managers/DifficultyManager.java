package deco2800.thomas.managers;

import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.combat.skills.FireBombSkill;
import deco2800.thomas.combat.skills.FireballSkill;
import deco2800.thomas.combat.skills.IceballSkill;
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
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/Difficulty%20Curve
 */
public class DifficultyManager extends TickableManager{
    private PlayerPeon playerPeon;
    private String type = "";
    private EnemyManager enemyManager;
    private Float originalDamageMultiplier = 0.4f;
    private static final String TUNDRA = "tundra";
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
            if(wizardSkill.getTexture().equals("iceballIcon")){
                if(getWorldType().equals("desert")){
                    //More damage to desert with water skill
                    IceballSkill.setDamageMultiplier(originalDamageMultiplier*2);
                }else{
                    IceballSkill.setDamageMultiplier(originalDamageMultiplier);
                }
            }else if(wizardSkill.getTexture().equals("fireballIcon")) {
                if(getWorldType().equals(TUNDRA)){
                    //More damage to tundra with fire skill
                    FireballSkill.setDamageMultiplier(originalDamageMultiplier*2);
                }else{
                    FireballSkill.setDamageMultiplier(originalDamageMultiplier);
                }
            }
        }
    }

    /***
     * Changes the mech cooldown time
     */
    public void setMechSkill(){
        AbstractSkill mechSkill = playerPeon.getMechSkill();
        if(mechSkill.getTexture() == "explosionIcon" && getWorldType().equals(TUNDRA)){
            //More damage to tundra with fire skill
            FireBombSkill.setDamageMultiplier(originalDamageMultiplier*2);
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
            String orcType = getWorldType().toLowerCase() + "Orc";

            //Set Wild Enemy Cap
            enemyManager.setWildEnemyCap(wildEnemyCap+getDifficultyLevel());

            //Set Wild Enemy Health
            setWildSpawnMaxHealth(enemyManager.getEnemyConfig(orcType).getMaxHealth() /(5-getDifficultyLevel()));
        }

        //Set skills damage multiplier
        setWizardSkill();
        setMechSkill();
        String worldType = getWorldType();
        setPlayerHealth(4);
        if(worldType.equals("swamp")){
            setWildSpawnRate(0.09f);
            enemyManager.getBoss().setMaxHealth(100);
            enemyManager.setWildEnemyCap(5);
        }else if(worldType.equals(TUNDRA)){
            setWildSpawnRate(0.1f);
            enemyManager.getBoss().setMaxHealth(150);
            enemyManager.setWildEnemyCap(6);
        }else if(worldType.equals("desert")){
            setWildSpawnRate(0.12f);
            enemyManager.getBoss().setMaxHealth(300);
        }else if(worldType.equals("volcano")){
            setWildSpawnRate(0.12f);
            enemyManager.getBoss().setMaxHealth(750);
            enemyManager.setWildEnemyCap(8);
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
