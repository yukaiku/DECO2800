package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.worlds.TestWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class EnemyManagerTest extends BaseGDXTest {
    private TestWorld world;

    @Before
    public void setUp() {
        world = new TestWorld();
    }

    @Test
    public void testConstructors() {
        EnemyManager em1 = new EnemyManager(world);
        assertEquals(em1.getWildEnemyCap(), 0);
        EnemyManager em2 = new EnemyManager(world, 10, new ArrayList<>());
        assertEquals(em2.getWildEnemyCap(), 10);
        assertEquals(em2.getWildEnemiesAlive(), new ArrayList<>());
        Dragon boss = new Dragon(1, 1, 1);
        EnemyManager em3 = new EnemyManager(world, 10, new ArrayList<>(), boss);
        assertSame(em3.getBoss(), boss);
    }

    @Test
    public void testEnemySpawning() {
        EnemyManager em = new EnemyManager(world);
        assertFalse(em.checkWildEnemySpawning());
        em.enableWildEnemySpawning();
        assertTrue(em.checkWildEnemySpawning());
        em.disableWildEnemySpawning();
        assertFalse(em.checkWildEnemySpawning());

        Goblin enemy = new Goblin(1, 1,1);
        em.spawnSpecialEnemy(enemy, 1, 1);
        assertEquals(em.getSpecialEnemiesAlive(), Collections.singletonList(enemy));
    }

    @Test
    public void testWildEnemyCap() {
        EnemyManager em = new EnemyManager(world, 10, new ArrayList<>());
        em.setWildEnemyCap(20);
        assertEquals(em.getWildEnemyCap(), 20);
    }

    @Test
    public void testBoss() {
        EnemyManager em = new EnemyManager(world, 10, new ArrayList<>());
        Dragon boss = new Dragon(1, 1, 1);
        em.setBoss(boss);
        assertSame(em.getBoss(), boss);
        em.spawnBoss(0, 0);
        assertEquals(em.getBoss().getCol(), 0, 0.5);
        assertEquals(em.getBoss().getRow(), 0, 0.5);
    }

    @After
    public void tearDown() {
        world = null;
    }
}
