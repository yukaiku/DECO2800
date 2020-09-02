package deco2800.thomas.entities;

import deco2800.thomas.BaseGDXTest;

import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.entities.enemies.Orc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EnemyPeonTest extends BaseGDXTest {
    @Test
    public void testEnemyTexture() {
        EnemyPeon enemy =  new Goblin(1, 1, 100);
        assertEquals(enemy.getTexture(), "goblin");

    }

    @Test
    public void testEnemyName() {
        EnemyPeon enemy =  new Goblin(1, 1, 100);
        assertEquals(enemy.getObjectName(), "Goblin");

    }

    //Need some formal way of testing that it approaches the player? For now
    // ingame testing has confirmed it
}
