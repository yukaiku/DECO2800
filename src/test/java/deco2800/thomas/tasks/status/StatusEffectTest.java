package deco2800.thomas.tasks.status;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.Peon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Tests the methods of the abstract StatusEffect class.
 */
public class StatusEffectTest extends BaseGDXTest {

    // the status effect instance being tested
    private StatusEffect effect;

    // the entity being passed to the status effect
    private Peon entity;

    /**
     * A blank child of StatusEffect so that the abstract class
     * can be tested.
     */
    private static class StatusEffectTester extends StatusEffect {
        public StatusEffectTester(Peon entity) {
            super(entity);
        }

        @Override
        public void applyEffect() {
        }
    }

    @Before
    public void setUp() throws Exception {
        entity = mock(Peon.class);
        effect = new StatusEffectTester(entity);
    }

    /**
     * Tests that a null entity passed to a StatusEffect will result
     * in an inactive effect.
     */
    @Test
    public void nullEntity() {
        effect = new StatusEffectTester(null);
        Assert.assertFalse(effect.getActive());
    }

    /**
     * Tests that getActive() correctly returns the active state of
     * the StatusEffect.
     */
    @Test
    public void getActive() {
        Assert.assertTrue(effect.getActive());
    }

    /**
     * Tests that setActiveStatus() correctly updates the active state
     * of the StatusEffect.
     */
    @Test
    public void setActiveState() {
        effect.setActiveState(false);
        Assert.assertFalse(effect.getActive());
    }

    /**
     * Tests that getAffectedEntity() correctly returns the entity tied
     * to this StatusEffect.
     */
    @Test
    public void getAffectedEntity() {
        Assert.assertEquals(entity, effect.getAffectedEntity());
    }
}