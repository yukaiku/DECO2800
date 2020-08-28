package deco2800.thomas.managers;

import deco2800.thomas.entities.Orc;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.worlds.TestWorld;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertFalse;

public class EnemyManagerTest {

    @Test
    public void testEnemyManager() {
        TestWorld world = new TestWorld();
        PlayerPeon player = new PlayerPeon(0,0,1);
        EnemyManager em = new EnemyManager(world, player, 10);
        assertSame(em.getEnemies(), new ArrayList<>());
    }

    @Test
    public void testEnemyCap() {
        TestWorld world = new TestWorld();
        PlayerPeon player = new PlayerPeon(0,0,1);
        Orc orc = new Orc(1, 1, 100, player);
        EnemyManager em = new EnemyManager(world, player, 0);
        assertFalse(em.spawnEnemy(orc, 0, 0));
    }
}
