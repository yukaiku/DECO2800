package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.PlayerPeon;
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
        assertEquals(em.getEnemies(), new ArrayList<>());
    }

    @Test
    public void testEnemyCap() {
        TestWorld world = new TestWorld();
        Orc orc = new Orc(1, 1, 100);
        EnemyManager em = new EnemyManager(world, 0, new ArrayList<>());
        assertFalse(em.spawnEnemy(orc, 0, 0));
    }
}
