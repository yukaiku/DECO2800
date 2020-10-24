package deco2800.thomas.entities.enemies;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.util.EnemyUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyUtilTest extends BaseGDXTest {
    private PlayerPeon playerPeon;
    private SwampDragon dragon;

    @Test
    public void testTooFarAway() {
        playerPeon = new PlayerPeon(20, 20, 10);
        dragon = new SwampDragon(1000, 0.1f, 1);
        assertFalse(EnemyUtil.playerInRadius(dragon, playerPeon, 1));
        assertFalse(EnemyUtil.playerInRange(dragon, playerPeon, 1));
        assertEquals(-1.0f, EnemyUtil.playerLRDistance(dragon, playerPeon), 0.01);
    }

    @Test
    public void testClose() {
        playerPeon = new PlayerPeon(0, 0, 10);
        dragon = new SwampDragon(1000, 0.1f, 1);
        assertTrue(EnemyUtil.playerInRadius(dragon, playerPeon, 1));
        assertTrue(EnemyUtil.playerInRange(dragon, playerPeon, 1));
        assertEquals(0f, EnemyUtil.playerLRDistance(dragon, playerPeon), 0.01);
    }
}
