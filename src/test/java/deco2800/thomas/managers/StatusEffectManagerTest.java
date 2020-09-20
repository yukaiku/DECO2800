package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.status.StatusEffect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Tests the status effect manager, which is responsible for storing
 * and applying status effects at each tick of the game.
 */
public class StatusEffectManagerTest extends BaseGDXTest {

    // the StatusEffectManager instance being tested
    private StatusEffectManager manager;

    // a mocked StatusEffect instance for testing
    private StatusEffect effect;

    // a mocking Peon instance for testing
    private Peon entity;

    @Before
    public void setUp() throws Exception {
        effect = mock(StatusEffect.class);
        manager = spy(new StatusEffectManager());
        entity = mock(Peon.class);
    }

    /**
     * Tests that getCurrentStatusEffects() correctly returns an empty list
     * when there are no active status effects.
     */
    @Test
    public void getCurrentStatusEffectsEmpty() {
        Assert.assertArrayEquals(new StatusEffect[]{}, manager.getCurrentStatusEffects().toArray());
    }

    /**
     * Tests that a new status effect can be added with addStatus(), and that
     * this can be returned in a list with getCurrentStatusEffects().
     */
    @Test
    public void addStatus() {
        manager.addStatus(effect);
        Assert.assertArrayEquals(new StatusEffect[]{effect}, manager.getCurrentStatusEffects().toArray());
    }

    /**
     * Tests that onTick() correctly leaves an effect active if it is active and
     * has a non-null entity.
     */
    @Test
    public void onTickActiveEffectNonNullEntity() {
        when(effect.getAffectedEntity()).thenReturn(entity);
        when(effect.getActive()).thenReturn(true);

        manager.addStatus(effect);
        manager.onTick(anyLong());
        Assert.assertArrayEquals(new StatusEffect[]{effect}, manager.getCurrentStatusEffects().toArray());
    }

    /**
     * Tests that onTick() correctly removes an effect if it is active but
     * has a null entity.
     */
    @Test
    public void onTickActiveEffectNullEntity() {
        when(effect.getAffectedEntity()).thenReturn(null);
        when(effect.getActive()).thenReturn(true);

        manager.addStatus(effect);
        manager.onTick(anyLong());
        Assert.assertArrayEquals(new StatusEffect[]{}, manager.getCurrentStatusEffects().toArray());
    }

    /**
     * Tests that onTick() correctly removes an effect if it is inactive and
     * has a null entity.
     */
    @Test
    public void onTickInactiveEffectNullEntity() {
        when(effect.getAffectedEntity()).thenReturn(null);
        when(effect.getActive()).thenReturn(false);

        manager.addStatus(effect);
        manager.onTick(anyLong());
        Assert.assertArrayEquals(new StatusEffect[]{}, manager.getCurrentStatusEffects().toArray());
    }

    /**
     * Tests that onTick() correctly removes an effect if it is inactive but
     * has a non-null entity.
     */
    @Test
    public void onTickInactiveEffectNonNullEntity() {
        when(effect.getAffectedEntity()).thenReturn(entity);
        when(effect.getActive()).thenReturn(false);

        manager.addStatus(effect);
        manager.onTick(anyLong());
        Assert.assertArrayEquals(new StatusEffect[]{}, manager.getCurrentStatusEffects().toArray());
    }
}