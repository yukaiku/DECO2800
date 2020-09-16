package deco2800.thomas.tasks.status;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.managers.GameManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Tests the BurnStatus generalisation of StatusEffect and its methods.
 *
 * These tests depend on the functionality of the HealthTracker class.
 */
@RunWith(PowerMockRunner.class)
public class BurnStatusTest extends BaseGDXTest {

    // the main BurnStatus instance being tested
    private BurnStatus burn;

    // the entity being passed to the status
    private AgentEntity entity;

    // the health tracker used to verify damage dealt
    private HealthTracker healthTracker;

    @Before
    public void setUp() throws Exception {
        entity = mock(AgentEntity.class);
        healthTracker = new HealthTracker(50);
        when(entity.getHealthTracker()).thenReturn(healthTracker);
        burn = new BurnStatus(entity, 5, 2);
    }

    /**
     * Tests that the first tick of a new instance is always ready.
     */
    @Test
    public void ticksReadyFirstTick() {
        Assert.assertTrue(burn.ticksReady());
    }

    /**
     * Tests that the second tick of an instance is not ready, if it
     * is called before 1 second has passed.
     */
    @Test
    public void ticksReadyNextTickBefore1s() {
        burn.ticksReady();
        Assert.assertFalse(burn.ticksReady());
    }

    /**
     * Tests that the first damage tick of a new instance is correctly
     * applied with applyEffect().
     */
    @Test
    public void applyEffectFirstTick() {
        burn.applyEffect();
        Assert.assertEquals(healthTracker.getCurrentHealthValue(), 45);
    }

    /**
     * Tests that the second damage tick of a new instance is not applied
     * if applyEffect() is called before 1 second has passed.
     */
    @Test
    public void applyEffectTicksNotReady() {
        burn.applyEffect();
        burn.applyEffect();

        Assert.assertEquals(healthTracker.getCurrentHealthValue(), 45);
    }

    /**
     * Tests that a new instance with one tick specified
     * is correctly set to inactive after the first tick.
     */
    @Test
    public void inactiveAfterTick() {
        BurnStatus burn2 = new BurnStatus(entity, 5, 1);
        Assert.assertTrue(burn2.getActive());
        burn2.applyEffect();
        Assert.assertFalse(burn2.getActive());
    }

    /**
     * Tests functionality of several events after 1 second has passed from
     * a new instance's first tick:
     *
     * - The call of ticksReady() correctly returns true after 1s,
     * - The second damage tick is correctly applied with applyEffect() after 1s,
     * - A status with two ticks specified is set to inactive after 1s.
     */
    @Test
    public void after1s() throws InterruptedException {
        BurnStatus burn2 = new BurnStatus(entity, 4, 2);
        burn.ticksReady();
        burn2.applyEffect();

        // wait 1 second so that ticksReady() can return true
        TimeUnit.SECONDS.sleep(2);

        Assert.assertTrue(burn.ticksReady());

        burn2.applyEffect();
        // should have taken two instances of 4 dmg
        Assert.assertEquals(healthTracker.getCurrentHealthValue(), 42);
        Assert.assertFalse(burn2.getActive());
    }
}
