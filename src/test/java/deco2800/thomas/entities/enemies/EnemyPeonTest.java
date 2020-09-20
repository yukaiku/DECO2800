package deco2800.thomas.entities.enemies;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.util.SquareVector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnemyPeonTest extends BaseGDXTest {

    @Test
    public void testConstructor() {
        Orc enemy = new Orc(Variation.SWAMP, 1, 1);
        assertEquals(enemy.getSpeed(), 1f, 0.01);
    }

    @Test
    public void testEnemyName() {
        EnemyPeon enemy = new Goblin(Variation.SWAMP, 1, 1);
        assertEquals(enemy.getObjectName(), "Swamp Goblin");
    }

    @Test
    public void testGetMaxHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        assertEquals(enemy.getMaxHealth(), 100);
    }

    @Test
    public void testSetMaxHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        enemy.setMaxHealth(500);
        assertEquals(enemy.getMaxHealth(), 500);
    }

    @Test
    public void testGetCurrentHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 10);
        assertEquals(enemy.getCurrentHealth(), 100);
    }

    @Test
    public void testSetCurrentHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        enemy.setCurrentHealthValue(20);
        assertEquals(enemy.getCurrentHealth(), 20);
    }

    @Test
    public void testReduceHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        int damage = enemy.applyDamage(50, DamageType.COMMON);
        assertEquals(damage, 100 - enemy.getCurrentHealth());
        assertEquals(50, damage);
    }

    @Test
    public void testRegenHealth() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        enemy.setCurrentHealthValue(10);
        enemy.regenerateHealth(40);
        assertEquals(50, enemy.getCurrentHealth());
    }

    @Test
    public void testDeepCopy() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        EnemyPeon enemy2 = enemy.deepCopy();
        assertEquals(enemy.getHeight(), enemy2.getHeight());
        assertEquals(enemy.getSpeed(), enemy2.getSpeed(), 0.01);
        assertEquals(enemy.getMaxHealth(), enemy2.getMaxHealth());
    }

    @Test
    public void testGetTarget() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        PlayerPeon p = new PlayerPeon(1, 1, 1, 50);
        enemy.setTarget(p);
        assertEquals(enemy.getTarget(), p);
    }

    @Test
    public void testPosition() {
        EnemyPeon enemy =  new Orc(Variation.SWAMP, 100, 1);
        enemy.setPosition(10, 10);
        assertEquals(enemy.getPosition(), new SquareVector(10, 10));
    }
    // Need some formal way of testing that it approaches the player? For now
    // in-game testing has confirmed it
}