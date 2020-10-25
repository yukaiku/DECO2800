package deco2800.thomas.entities.agent;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.StatusEffect;
import deco2800.thomas.util.WorldUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyLong;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests the PeonClass.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WorldUtil.class)
public class PeonTest extends BaseGDXTest {
    @Before
    public void setup() {
        FloatingDamageComponent fdc = mock(FloatingDamageComponent.class);
        mockStatic(WorldUtil.class);
        when(WorldUtil.getFloatingDamageComponent()).thenReturn(fdc);
    }

    /**
     * Tests for a valid constructor of the Peon.
     */
    @Test
    public void testConstructor() {
        Peon p = new Peon(1, 1, 1, 10);
        assertEquals(1f, p.getCol(), 0.001f);
        assertEquals(1f, p.getRow(), 0.001f);
        assertEquals(1f, p.getSpeed(), 0.001f);
        assertEquals(10, p.getCurrentHealth());
        assertNotNull(p.getTexture());
    }

    /**
     * Tests that effects are applied.
     */
    @Test
    public void testStatusEffectApplied() {
        // Prepare peon, and mock a status effect
        Peon p = new Peon(1, 1, 1, 10);
        StatusEffect effect = mock(StatusEffect.class);
        when(effect.getActive()).thenReturn(true);

        p.addEffect(effect);
        p.onTick(0);
        verify(effect).applyEffect();
    }

    /**
     * Tests that effects are removed.
     */
    @Test
    public void testStatusEffectRemoved() {
        // Prepare peon, and mock a status effect
        Peon p = new Peon(1, 1, 1, 10);
        StatusEffect effect = mock(StatusEffect.class);
        when(effect.getActive()).thenReturn(false);

        p.addEffect(effect);
        p.onTick(0);

        // After tick, we expect no effects remaining.
        assertEquals(0, p.getEffects().size());
    }

    /**
     * Tests that movement tasks are called.
     */
    @Test
    public void testMovementTaskTick() {
        // Prepare peon
        Peon p = new Peon(1, 1, 1, 10);

        // Mock movement task
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isAlive()).thenReturn(true);
        when(movementTask.isComplete()).thenReturn(true);

        // Set movement task, and onTick()
        p.setMovementTask(movementTask);
        p.onTick(0);

        // Verify
        verify(movementTask).onTick(anyLong());
    }

    /**
     * Tests that the movement task is removed when complete.
     */
    @Test
    public void testMovementTaskComplete() {
        // Prepare peon
        Peon p = new Peon(1, 1, 1, 10);

        // Mock movement task
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isAlive()).thenReturn(true);
        when(movementTask.isComplete()).thenReturn(true);

        // Set movement task, and onTick()
        p.setMovementTask(movementTask);
        p.onTick(0);

        // Check movement task is null
        assertNull(p.getMovementTask());
    }

    /**
     * Tests that movement tasks are called.
     */
    @Test
    public void testCombatTaskTick() {
        // Prepare peon
        Peon p = new Peon(1, 1, 1, 10);

        // Mock movement task
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isAlive()).thenReturn(true);
        when(combatTask.isComplete()).thenReturn(true);

        // Set movement task, and onTick()
        p.setCombatTask(combatTask);
        p.onTick(0);

        // Verify
        verify(combatTask).onTick(anyLong());
    }

    /**
     * Tests that the movement task is removed when complete.
     */
    @Test
    public void testCombatTaskComplete() {
        // Prepare peon
        Peon p = new Peon(1, 1, 1, 10);

        // Mock movement task
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isAlive()).thenReturn(true);
        when(combatTask.isComplete()).thenReturn(true);

        // Set movement task, and onTick()
        p.setCombatTask(combatTask);
        p.onTick(0);

        // Check movement task is null
        assertNull(p.getCombatTask());
    }

    /**
     * Tests applyDamage with no armour and no vulnerability.
     */
    @Test
    public void testApplyDamageStandard() {
        Peon p = new Peon(1, 1, 1, 100);
        int damage = p.applyDamage(50, DamageType.COMMON);
        assertEquals(50, p.getCurrentHealth());
        assertEquals(50, damage);
    }

    /**
     * Tests applyDamage with damage vulnerability.
     */
    @Test
    public void testApplyDamageVulnerable() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setVulnerability(DamageType.FIRE);
        int damage = p.applyDamage(50, DamageType.FIRE);
        assertEquals(25, p.getCurrentHealth());
        assertEquals(75, damage);
    }

    /**
     * Tests applyDamage with damage vulnerability - but vulnerable to different
     * damage type.
     */
    @Test
    public void testApplyDamageNotVulnerable() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setVulnerability(DamageType.FIRE);
        int damage = p.applyDamage(50, DamageType.ICE);
        assertEquals(50, p.getCurrentHealth());
        assertEquals(50, damage);
    }

    /**
     * Tests applyDamage with armour.
     */
    @Test
    public void testApplyDamageArmoured() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setArmour(2000);
        int damage = p.applyDamage(50, DamageType.COMMON);
        assertEquals(75, p.getCurrentHealth());
        assertEquals(25, damage);
    }

    /**
     * Tests getter and setters for vulnerability
     */
    @Test
    public void testGetSetVulnerability() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setVulnerability(DamageType.FIRE);
        assertEquals(DamageType.FIRE, p.getVulnerability());
    }

    /**
     * Tests adding armour to the peon.
     */
    @Test
    public void testAddArmour() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setArmour(1000);
        p.addArmour(1000);
        assertEquals(2000f, p.getArmour(), 0.001f);
    }

    /**
     * Tests reduce health applies damage ignoring armour and damage types.
     */
    @Test
    public void testReduceHealth() {
        Peon p = new Peon(1, 1, 1, 100);
        p.setArmour(2000f);
        p.reduceHealth(50);
        assertEquals(50, p.getCurrentHealth());
    }

    /**
     * Tests isDead.
     */
    @Test
    public void testIsDead() {
        Peon p = new Peon(1, 1, 1, 100);
        p.reduceHealth(200);
        assertEquals(true, p.isDead());
    }

    /**
     * Tests that getHealthTracker returns valid HealthTracker instance.
     */
    @Test
    public void testGetHealthTracker() {
        Peon p = new Peon(1, 1, 1, 99);
        assertNotNull(p.getHealthTracker());
        assertEquals(99, p.getHealthTracker().getCurrentHealthValue());
    }

    /**
     * Tests that locking the health prevents incoming damage.
     */
    @Test
    public void testLockHealth() {
        Peon p = new Peon(1, 1, 1, 100);
        p.lockHealth();
        p.applyDamage(50, DamageType.COMMON);
        assertEquals(100, p.getCurrentHealth());
    }

    /**
     * Tests that unlocking health allows incoming damage.
     */
    @Test
    public void testUnlockHealth() {
        Peon p = new Peon(1, 1, 1, 100);
        p.lockHealth();
        p.unlockHealth();
        p.applyDamage(50, DamageType.COMMON);
        assertEquals(50, p.getCurrentHealth());
    }
}
