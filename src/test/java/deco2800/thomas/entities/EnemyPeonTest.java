package deco2800.thomas.entities;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.PlayerPeon;
import org.junit.Assert;
import org.junit.Before;

import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.entities.enemies.Orc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EnemyPeonTest extends BaseGDXTest {

    @Test
    public void testConstructor() {
        Orc enemy = new Orc(1, 1, 100, "orc_swamp");
        assertEquals(enemy.getTexture(), "orc_swamp_right");
    }

    @Test
    public void testEnemyTexture() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        assertEquals(enemy.getTexture(), "orc_swamp_right");
    }

    @Test
    public void testEnemyName() {
        EnemyPeon enemy =  new Goblin(1, 1, 100);
        assertEquals(enemy.getObjectName(), "Goblin");
    }

    @Test
    public void testGetMaxHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        assertEquals(enemy.getMaxHealth(), 100);
    }

    @Test
    public void testSetMaxHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        enemy.setMaxHealth(500);
        assertEquals(enemy.getMaxHealth(), 500);
    }

    @Test
    public void testGetCurrentHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        assertEquals(enemy.getCurrentHealth(), 100);
    }

    @Test
    public void testSetCurrentHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        enemy.setCurrentHealthValue(20);
        assertEquals(enemy.getCurrentHealth(), 20);
    }

    @Test
    public void testReduceHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        enemy.reduceHealth(50);
        assertEquals(enemy.getCurrentHealth(), 50);
    }

    @Test
    public void testRegenHealth() {
        EnemyPeon enemy =  new Orc(1, 1, 100);
        enemy.setCurrentHealthValue(10);
        enemy.regenerateHealth(40);
        assertEquals(enemy.getCurrentHealth(), 50);
    }
    // Need some formal way of testing that it approaches the player? For now
    // in-game testing has confirmed it
}
