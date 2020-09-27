package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.worlds.TestWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;

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
