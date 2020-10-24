package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.Knight;
import deco2800.thomas.combat.Wizard;
import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.combat.skills.FireBombSkill;
import deco2800.thomas.combat.skills.FireballSkill;
import deco2800.thomas.combat.skills.IceballSkill;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.InvalidEnemyException;
import deco2800.thomas.entities.enemies.monsters.Orc;
import deco2800.thomas.worlds.swamp.SwampWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DifficultyManagerTest extends BaseGDXTest {
    private DifficultyManager difficultyManager;
    private EnemyManager enemyManager;
    private PlayerManager playerManager;
    private SwampWorld swampWorld;
    private PlayerPeon playerPeon;

    /***
     * Sets up the entities and info needed for testing the manager
     * @throws InvalidEnemyException when invalid enemy is created
     */
    @Before
    public void setUp() throws InvalidEnemyException {
        difficultyManager = new DifficultyManager();
        playerManager = GameManager.getManagerFromInstance(PlayerManager.class);
        playerPeon = new PlayerPeon(10f,5f,0.15f);
        swampWorld = new SwampWorld();
        swampWorld.setPlayerEntity(playerPeon);
        swampWorld.addEntity(swampWorld.getPlayerEntity());
        enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.addEnemyConfigs("swampOrc");
        enemyManager.addEnemyConfigs("tundraOrc");
        enemyManager.addEnemyConfigs("desertOrc");
        enemyManager.addEnemyConfigs("volcanoOrc");
        difficultyManager.setPlayerEntity((PlayerPeon) swampWorld.getPlayerEntity());
    }

    /***
     * Test if the difficulty level is returning the right value
     */
    @Test
    public void testDifficultyLevel(){
        assertEquals(1,difficultyManager.getDifficultyLevel());
    }

    /***
     * Test if the setting of max health is working
     */
    @Test
    public void testSetMaxHealth(){
        assertEquals(100,playerPeon.getMaxHealth());
        assertEquals(100,playerPeon.getCurrentHealth());
        difficultyManager.setDifficultyLevel("Swamp");
        difficultyManager.setWildSpawnMaxHealth(0);
        assertEquals(0,enemyManager.getEnemyConfig("swampOrc").getMaxHealth());
        assertEquals(100,playerPeon.getMaxHealth());
        assertEquals(100,playerPeon.getCurrentHealth());
        difficultyManager.setPlayerHealth(2);
        assertEquals(50,playerPeon.getMaxHealth());
        assertEquals(50,playerPeon.getCurrentHealth());
    }

    /***
     * Testing the method of setting player health
     */
    @Test
    public void testPlayerHealth(){
        assertEquals(100, playerPeon.getCurrentHealth()); //Checking health before modifying
        difficultyManager.setPlayerHealth(2);
        assertEquals(50, playerPeon.getMaxHealth());
        assertEquals(playerPeon.getCurrentHealth(), playerPeon.getMaxHealth());
    }

    /***
     * Test mech skills
     */
    @Test
    public void testMechSkills(){
        playerManager.setKnight(Knight.FIRE);
        playerPeon.updatePlayerSkills();
        difficultyManager.setDifficultyLevel("Swamp");
        AbstractSkill mechSkill = playerPeon.getMechSkill();
        assertEquals(0.4, ((FireBombSkill) mechSkill).getDamageMultiplier(), 0.01);
        difficultyManager.setDifficultyLevel("Tundra");
        AbstractSkill mechSkill2 = playerPeon.getMechSkill();
        assertEquals(0.8, ((FireBombSkill) mechSkill).getDamageMultiplier(), 0.01);
    }

    /***
     * Test wizard skills
     */
    @Test
    public void testOriginalWizardSkills() {
        playerManager.setWizard(Wizard.FIRE);
        playerPeon.updatePlayerSkills();
        List<AbstractSkill> wizardSkills = playerPeon.getWizardSkills();
        for (AbstractSkill wizardSkill : wizardSkills) {
            if(wizardSkill.getTexture().equals("fireballIcon")) {
                assertEquals(0.4, ((FireballSkill) wizardSkill).getDamageMultiplier(), 0.01);
            }
        }
        playerManager.setWizard(Wizard.WATER);
        playerPeon.updatePlayerSkills();
        List<AbstractSkill> wizardSkills2 = playerPeon.getWizardSkills();
        for (AbstractSkill wizardSkill : wizardSkills2) {
            if(wizardSkill.getTexture().equals("iceballIcon")) {
                assertEquals(0.4, ((IceballSkill) wizardSkill).getDamageMultiplier(), 0.01);
            }
        }
    }

    /***
     * Testing swamp difficulty settings
     */
    @Test
    public void testSwampDifficulty(){
        difficultyManager.setDifficultyLevel("Swamp");
        assertEquals("swamp",difficultyManager.getWorldType());
        assertEquals(12,enemyManager.getEnemyConfig("swampOrc").getMaxHealth());
        EnemyPeon orc = enemyManager.getEnemyConfig("swampOrc");
        Orc orc1 = (Orc)orc;
        assertEquals(0.05f,orc1.getSpawnRate(), 0.01);
        assertEquals(5,enemyManager.getWildEnemyCap());
    }

    /***
     * Testing tundra difficulty settings
     */
    @Test
    public void testTundraDifficulty(){
        difficultyManager.setDifficultyLevel("Tundra");
        assertEquals("tundra",difficultyManager.getWorldType());
        assertEquals(25,enemyManager.getEnemyConfig("tundraOrc").getMaxHealth());
        EnemyPeon orc = enemyManager.getEnemyConfig("tundraOrc");
        Orc orc1 = (Orc)orc;
        assertEquals(0.06f,orc1.getSpawnRate(), 0.01);
        assertEquals(6,enemyManager.getWildEnemyCap());

        //Test Wizard Skill in Tundra
        List<AbstractSkill> wizardSkills3 = playerPeon.getWizardSkills();
        for(AbstractSkill wizardSkill : wizardSkills3){
            switch (wizardSkill.getTexture()){
                case "fireballIcon": //Default 20
                    assertEquals(0.8,((FireballSkill) wizardSkill).getDamageMultiplier(), 0.01);

            }
        }
    }

    /***
     * Testing desert difficulty settings
     */
    @Test
    public void testDesertDifficulty(){
        playerManager.setWizard(Wizard.WATER);
        playerPeon.updatePlayerSkills();
        System.out.println(playerPeon.getWizardSkills());
        difficultyManager.setPlayerEntity(playerPeon);
        difficultyManager.setDifficultyLevel("Desert");
        assertEquals("desert",difficultyManager.getWorldType());
        assertEquals(12,enemyManager.getEnemyConfig("desertOrc").getMaxHealth());
        EnemyPeon orc = enemyManager.getEnemyConfig("desertOrc");
        Orc orc1 = (Orc)orc;
        assertEquals(0.07f,orc1.getSpawnRate(), 0.01);

        //Test Wizard Skill in Desert
        List<AbstractSkill> wizardSkills4 = playerPeon.getWizardSkills();
        for(AbstractSkill wizardSkill : wizardSkills4){
            switch (wizardSkill.getTexture()){
                case "iceballIcon":
                    assertEquals(0.8, ((IceballSkill) wizardSkill).getDamageMultiplier(), 0.01);
            }
        }
    }

    /***
     * Testing volcano difficulty settings
     */
    @Test
    public void testVolcanoDifficulty(){
        difficultyManager.setDifficultyLevel("Volcano");
        assertEquals("volcano",difficultyManager.getWorldType());
        assertEquals(12,enemyManager.getEnemyConfig("volcanoOrc").getMaxHealth());
        EnemyPeon orc = enemyManager.getEnemyConfig("volcanoOrc");
        Orc orc1 = (Orc)orc;
        assertEquals(0.08f,orc1.getSpawnRate(), 0.01);
        assertEquals(8,enemyManager.getWildEnemyCap());
    }

    @After
    public void tearDown() {
        difficultyManager = null;
        enemyManager = null;
        playerManager = null;
    }
}
