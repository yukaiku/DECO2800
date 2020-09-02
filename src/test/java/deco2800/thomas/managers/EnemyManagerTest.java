package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.worlds.TestWorld;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EnemyManagerTest extends BaseGDXTest {

    @Test
    public void testEnemyManager() {
        TestWorld world = new TestWorld();
        EnemyManager em = new EnemyManager(world, 10, new ArrayList<>());
        assertEquals(em.getWildEnemiesAlive(), new ArrayList<>());
    }

    @Test
    public void testEnemyCap() {
        TestWorld world = new TestWorld();
        EnemyManager em = new EnemyManager(world, 10, new ArrayList<>());
        assertEquals(em.getWildEnemyCap(), 10);
    }
}
