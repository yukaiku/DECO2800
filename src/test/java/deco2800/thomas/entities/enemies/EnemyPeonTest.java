package deco2800.thomas.entities.enemies;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.entities.enemies.monsters.Orc;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.util.WorldUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(WorldUtil.class)
public class EnemyPeonTest extends BaseGDXTest {
    @Before
    public void setup() {
        FloatingDamageComponent fdc = mock(FloatingDamageComponent.class);
        PowerMockito.mockStatic(WorldUtil.class);
        when(WorldUtil.getFloatingDamageComponent()).thenReturn(fdc);
    }

    @Test
    public void testConstructor() {
        Orc enemy = new Orc(EnemyIndex.Variation.SWAMP, 1, 1, 1, 1, 1, 1);
        assertEquals(enemy.getSpeed(), 1f, 0.01);
    }

    @Test
    public void testEnemyName() {
        EnemyPeon enemy = new Goblin(EnemyIndex.Variation.SWAMP, 1, 1, 1, 1, 1);
        assertEquals(enemy.getObjectName(), "Swamp Goblin");
    }

    @Test
    public void testGetMaxHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        assertEquals(enemy.getMaxHealth(), 100);
    }

    @Test
    public void testSetMaxHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        enemy.setMaxHealth(500);
        assertEquals(enemy.getMaxHealth(), 500);
    }

    @Test
    public void testGetCurrentHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 10, 1, 1, 1, 1);
        assertEquals(enemy.getCurrentHealth(), 100);
    }

    @Test
    public void testSetCurrentHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        enemy.setCurrentHealthValue(20);
        assertEquals(enemy.getCurrentHealth(), 20);
    }

    @Test
    public void testReduceHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        int damage = enemy.applyDamage(50, DamageType.COMMON);
        assertEquals(damage, 100 - enemy.getCurrentHealth());
        assertEquals(50, damage);
    }

    @Test
    public void testRegenHealth() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        enemy.setCurrentHealthValue(10);
        enemy.regenerateHealth(40);
        assertEquals(50, enemy.getCurrentHealth());
    }

    @Test
    public void testDeepCopy() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        EnemyPeon enemy2 = enemy.deepCopy();
        assertEquals(enemy.getHeight(), enemy2.getHeight());
        assertEquals(enemy.getSpeed(), enemy2.getSpeed(), 0.01);
        assertEquals(enemy.getMaxHealth(), enemy2.getMaxHealth());
    }

    @Test
    public void testGetTarget() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        PlayerPeon p = new PlayerPeon(1, 1, 1, 50);
        enemy.setTarget(p);
        assertEquals(enemy.getTarget(), p);
    }

    @Test
    public void testPosition() {
        EnemyPeon enemy =  new Orc(EnemyIndex.Variation.SWAMP, 100, 1, 1, 1, 1, 1);
        enemy.setPosition(10, 10);
        assertEquals(enemy.getPosition(), new SquareVector(10, 10));
    }

    @Test
    public void testEnemyIndex() {
        try {
            EnemyPeon enemy = EnemyIndex.getEnemy("testOrc");
            assertEquals(100, enemy.getMaxHealth());
            assertEquals(1, enemy.getDamage());
        } catch (InvalidEnemyException e) {
            fail();
        }
    }

    @Test(expected = InvalidEnemyException.class)
    public void testEnemyIndexException() throws InvalidEnemyException {
        EnemyPeon enemy = EnemyIndex.getEnemy("testWizard");
    }
    // Need some formal way of testing that it approaches the player? For now
    // in-game testing has confirmed it
}
