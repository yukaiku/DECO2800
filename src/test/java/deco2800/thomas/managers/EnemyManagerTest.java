package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.enemies.DragonTest;
import deco2800.thomas.entities.enemies.InvalidEnemyException;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.tundra.TundraWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EnemyManagerTest extends BaseGDXTest {
    private TestWorld world;

    @Before
    public void setUp() {
        world = new TestWorld();
        TextureManager textureManager = mock(TextureManager.class);
    }

    @Test
    public void testConstructors() {
        EnemyManager em1 = new EnemyManager(world);
        assertEquals(em1.getWildEnemyCap(), 0);
        EnemyManager em2 = new EnemyManager(world, null, 10);
        assertEquals(em2.getWildEnemyCap(), 10);
        assertEquals(em2.getWildEnemiesAlive(), new ArrayList<>());
        EnemyManager em3 = new EnemyManager(world, "swampDragon", 10);
        assertEquals(em3.getBoss().getClass(), SwampDragon.class);
        EnemyManager em4 = new EnemyManager(world, 10, "tundraOrc");
        assertEquals(em4.getWildEnemyCap(), 10);
        EnemyManager em5 = new EnemyManager(world, "badBossName", 10, "badWildEnemy");
        assertEquals(em5.getEnemyCount(), 0);
        try {
            em5.addEnemyConfigs("tundraOrc");
        } catch (InvalidEnemyException io) {
            fail();
        }
        for (int i = 0; i < 6; i++) {
            em4.onTick(0);
        }
    }

    @Test
    public void testRemoval() {
        EnemyManager em4 = new EnemyManager(world, 10, "tundraOrc");
        em4.removeEnemyConfigs("tundraOrc");
        assertNull(em4.getEnemyConfig("tundraOrc"));
        em4.spawnSpecialEnemy("swampGoblin", 1, 1);
        em4.removeSpecialEnemy(em4.getSpecialEnemiesAlive().get(0));
    }

    @Test
    public void testCount() {
        EnemyManager em4 = new EnemyManager(world, 10, "tundraOrc");
        assertEquals(0, em4.getEnemyCount());
        EnemyManager em6 = new EnemyManager(world, "swampDragon", 10, "swampOrc");
        em6.spawnBoss(0, 0);
        assertEquals(1, em6.getEnemyCount());
    }

    @Test
    public void testSetBoss() {
        EnemyManager em4 = new EnemyManager(world, 10, "tundraOrc");
        SwampDragon dragon = new SwampDragon(100, 0.1f, 1);
        em4.spawnBoss(0, 0);
        assertEquals(0, em4.getEnemyCount());
        em4.setBoss(dragon);
        em4.spawnBoss(0, 0);
        assertTrue(world.getEntities().contains(dragon));
        em4.removeBoss();
        em4.removeBoss();
        assertFalse(world.getEntities().contains(dragon));
        assertEquals(em4.getBoss().getCol(), 0, 0.001);
        assertEquals(em4.getBoss().getRow(), 0, 0.001);
    }

    @Test
    public void testEnemySpawning() {
        EnemyManager em = new EnemyManager(world);
        assertFalse(em.checkWildEnemySpawning());
        em.enableWildEnemySpawning();
        assertTrue(em.checkWildEnemySpawning());
        em.disableWildEnemySpawning();
        assertFalse(em.checkWildEnemySpawning());

        em.spawnSpecialEnemy("testGoblin", 1, 1);
        assertEquals(em.getSpecialEnemiesAlive().get(0).getClass(), Goblin.class);
    }

    @Test
    public void testSpecialEnemies() {
        EnemyManager em = new EnemyManager(world);
        em.spawnSpecialEnemy("dd", 1, 1);
        assertEquals(0, em.getEnemyCount());
        em.spawnSpecialEnemy("testGoblin", 1, 1);
        assertEquals(1, em.getEnemyCount());
        em.spawnSpecialEnemy("swampGoblin", 1, 1);
        em.spawnSpecialEnemy("tundraGoblin", 1, 1);
        em.spawnSpecialEnemy("desertGoblin", 1, 1);
        em.spawnSpecialEnemy("immuneOrc", 1, 1);
        em.spawnSpecialEnemy("volcanoGoblin", 1, 1);
        em.spawnSpecialEnemy("dummy", 1, 1);
        em.spawnSpecialEnemy("summonGoblin", 1, 1);
        em.spawnSpecialEnemy("testDragon", 1, 1);
        assertEquals(9, em.getEnemyCount());
        EnemyManager em2 = new EnemyManager(world, "swampDragon",
                10, "swampGoblin");
        em2.spawnSpecialEnemy("swampGoblin", 1, 1);
        assertEquals(1, em2.getSpecialEnemiesAlive().size());
    }

    @Test
    public void testWildEnemyCap() {
        EnemyManager em = new EnemyManager(world, null, 10);
        em.setWildEnemyCap(20);
        assertEquals(em.getWildEnemyCap(), 20);
    }

    @Test
    public void testBoss() {
        EnemyManager em = new EnemyManager(world, "swampDragon", 10);
        em.spawnBoss(0, 0);
        assertEquals(em.getBoss().getCol(), 0, 0.001);
        assertEquals(em.getBoss().getRow(), 0, 0.001);
    }

    @After
    public void tearDown() {
        world = null;
    }
}
