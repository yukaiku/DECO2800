package deco2800.thomas.tasks.status;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.Peon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Tests the BurnStatus generalisation of StatusEffect and its methods.
 *
 * These tests depend on the functionality of the AgentEntity class.
 */
public class SpeedStatusTest extends BaseGDXTest {

    // the main SpeedStatus instance being tested
    private SpeedStatus speed;

    // the entity being passed to the status
    private AgentEntity entity;

    // the speed multiplier of the status
    private float multiplier;

    @Before
    public void setUp() throws Exception {
        entity = new AgentEntity() {
            @Override
            public void onTick(long i) {
            }
        };
        multiplier = 0.5f;
        speed = new SpeedStatus((Peon)entity, multiplier, 1);
    }

    /**
     * Tests that the speed effect is correctly applied after the first call
     * of applyEffect().
     */
    @Test
    public void applyEffectInitial() {
        float startSpeed = entity.getSpeed();
        speed.applyEffect();

        Assert.assertEquals(startSpeed * multiplier, entity.getSpeed(), 0.000001);
    }

    /**
     * Tests that a second call of applyEffect() before the duration of
     * the effect expires does not affect an entity's speed.
     */
    @Test
    public void applyEffectAgainBeforeTime() {
        float startSpeed = entity.getSpeed();
        speed.applyEffect();
        speed.applyEffect();

        Assert.assertEquals(startSpeed * multiplier, entity.getSpeed(), 0.000001);
    }

    /**
     * Tests that removeEffect() correctly removes an effect and sets an
     * entity back to its original speed.
     */
    @Test
    public void removeEffect() {
        float startSpeed = entity.getSpeed();
        speed.applyEffect();
        speed.removeEffect();

        Assert.assertEquals(startSpeed, entity.getSpeed(), 0.000001);
    }

    /**
     * Tests that multiple effects correctly change an entity's speed and that
     * removal of one of these effects removes ONLY that effect's speed change.
     */
    @Test
    public void multipleEffects() {
        float startSpeed = entity.getSpeed();
        float otherMultiplier = 0.1f;
        SpeedStatus speed2 = new SpeedStatus((Peon)entity, otherMultiplier, 1);

        speed.applyEffect();
        speed2.applyEffect();
        Assert.assertEquals(startSpeed * multiplier * otherMultiplier, entity.getSpeed(), 0.000001);

        speed.removeEffect();
        Assert.assertEquals(startSpeed * otherMultiplier, entity.getSpeed(), 0.000001);
    }

    /**
     * Tests that a second call of applyEffect() correctly removes the effect and sets
     * its state to inactive after the effect's duration has expired.
     */
    @Test
    public void applyEffectAgainAfterTime() throws InterruptedException {
        float startSpeed = entity.getSpeed();
        speed.applyEffect();

        TimeUnit.SECONDS.sleep(2);

        speed.applyEffect();
        Assert.assertFalse(speed.getActive());
        Assert.assertEquals(startSpeed, entity.getSpeed(), 0.000001);
    }
}