package deco2800.thomas.entities;

import deco2800.thomas.BaseGDXTest;

import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Orc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EnemyPeonTest extends BaseGDXTest {
    @Test
    public void testEnemyTexture() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        assertEquals(enemy.getTexture(), "spacman_blue");
    }

    @Test
    public void testEnemyName() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        assertEquals(enemy.getObjectName(), "Orc");
    }

    //Need some formal way of testing that it approaches the player? For now
    // ingame testing has confirmed it
}
