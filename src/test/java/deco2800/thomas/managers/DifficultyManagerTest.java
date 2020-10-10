package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.InvalidEnemyException;
import deco2800.thomas.worlds.swamp.SwampWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DifficultyManagerTest extends BaseGDXTest {
    private DifficultyManager difficultyManager;
    private EnemyManager enemyManager;
    private SwampWorld swampWorld;
    private PlayerPeon playerPeon;

    /***
     * Sets up the entities and info needed for testing the manager
     * @throws InvalidEnemyException when invalid enemy is created
     */
    @Before
    public void setUp() throws InvalidEnemyException {
        difficultyManager = new DifficultyManager();
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
        difficultyManager.setDifficultyLevel("Swamp");
        difficultyManager.setWildSpawnMaxHealth(0);
        assertEquals(0,difficultyManager.getWildSpawnMaxHealth());
        assertEquals(50,playerPeon.getMaxHealth());
    }

    /***
     * Testing swamp difficulty settings
     */
    @Test
    public void testSwampDifficulty(){
        difficultyManager.setDifficultyLevel("Swamp");
        assertEquals("swamp",difficultyManager.getWorldType());
        assertEquals(12,difficultyManager.getWildSpawnMaxHealth());
    }

    /***
     * Testing tundra difficulty settings
     */
    @Test
    public void testTundraDifficulty(){
        difficultyManager.setDifficultyLevel("Tundra");
        assertEquals("tundra",difficultyManager.getWorldType());
        assertEquals(25,difficultyManager.getWildSpawnMaxHealth());
    }

    /***
     * Testing desert difficulty settings
     */
    @Test
    public void testDesertDifficulty(){
        difficultyManager.setDifficultyLevel("Desert");
        assertEquals("desert",difficultyManager.getWorldType());
        assertEquals(12,difficultyManager.getWildSpawnMaxHealth());
    }

    /***
     * Testing volcano difficulty settings
     */
    @Test
    public void testVolcanoDifficulty(){
        difficultyManager.setDifficultyLevel("Volcano");
        assertEquals("volcano",difficultyManager.getWorldType());
        assertEquals(12,difficultyManager.getWildSpawnMaxHealth());
    }

    @Test
    public void testPlayerHealth(){
        difficultyManager.playerHealth(playerPeon, 2);
        assertEquals(playerPeon.getMaxHealth(), 25*2);
        assertEquals(playerPeon.getCurrentHealth(), playerPeon.getMaxHealth());
    }

    @After
    public void tearDown() {
        difficultyManager = null;
    }
}
