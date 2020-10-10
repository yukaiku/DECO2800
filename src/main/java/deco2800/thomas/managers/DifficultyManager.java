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
        playerMaxHealth = playerPeon.getMaxHealth();
        if(playerPeon.getCurrentHealth() > playerMaxHealth){
            //Sets max health based off number of orbs starting from 25 to 100
            playerPeon.setCurrentHealthValue(playerMaxHealth);
        }
    }

    /***
     * Changes the wizard skill cooldown and damage
     * @param coolDown cooldown time of skills
     */
    public void setWizardSkillCoolDown(int coolDown){
        List<AbstractSkill> wizardSkills = playerPeon.getWizardSkills();
        for(AbstractSkill wizardSkill : wizardSkills){
            switch (wizardSkill.getTexture()){
                case "iceballIcon": // Default 50
                    ((IceballSkill) wizardSkill).setMaxCooldown(coolDown*2);
                    if(getWorldType().equals("desert")){
                        //More damage to desert with water skill
                        ((IceballSkill)wizardSkill).setDamageMultiplier(((IceballSkill) wizardSkill).getDamageMultiplier()*2);
                    }
                    return;
                case "fireballIcon": //Default 20
                    ((FireballSkill) wizardSkill).setMaxCooldown(coolDown);
                    if(getWorldType().equals("tundra")){
                        //More damage to tundra with fire skill
                        ((FireballSkill)wizardSkill).setDamageMultiplier(((FireballSkill) wizardSkill).getDamageMultiplier()*2);
                    }
                    return;
                case "stingIcon": //Default 50
                    ((ScorpionStingSkill) wizardSkill).setMaxCooldown(coolDown*2);
                    return;

            }
        }
    }

    /***
     * Changes the mech cooldown time
     * @param coolDown cooldown time of skill
     */
    public void setMechSkillCoolDown(int coolDown){
        AbstractSkill mechSkill = playerPeon.getMechSkill();
        if(mechSkill.getTexture() == "explosionIcon"){ //Default 160
            ((FireBombSkill) mechSkill).setMaxCooldown(coolDown);
        }else{ //default 200
            ((WaterShieldSkill) mechSkill).setMaxCooldown(coolDown*2);
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
            case "swamp":
                setPlayerHealth(1);
                setWildSpawnRate(0.0001f);
                enemyManager.getBoss().setMaxHealth(100);
                setWizardSkillCoolDown(15);
                break;
            case "tundra":
                setPlayerHealth(2);
                setWildSpawnRate(0.0001f);
                enemyManager.getBoss().setMaxHealth(150);
                setWizardSkillCoolDown(15);
                setMechSkillCoolDown(150);
                break;
            case "desert":
                setPlayerHealth(3);
                setWildSpawnRate(0.0001f);
                enemyManager.getBoss().setMaxHealth(300);
                setWizardSkillCoolDown(15);
                setMechSkillCoolDown(50);
                break;
            case "volcano":
                setPlayerHealth(4);
                setWildSpawnRate(0.0001f);
                enemyManager.getBoss().setMaxHealth(750);
                setWizardSkillCoolDown(15);
                setMechSkillCoolDown(50);
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
