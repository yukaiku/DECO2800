package deco2800.thomas.tasks.status;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.util.SquareVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Tests the QuicksandBurnStatus generalisation of the BurnStatus class.
 */
public class QuicksandBurnStatusTest extends BaseGDXTest {

    // the QuicksandBurnStatus instance being tested
    private QuicksandBurnStatus qBurn;

    // the position SquareVector being mocked
    private SquareVector pos;

    @Before
    public void setUp() throws Exception {
        pos = mock(SquareVector.class);
        HealthTracker healthTracker = mock(HealthTracker.class);
        Peon entity = mock(Peon.class);
        when(entity.getPosition()).thenReturn(pos);
        when(entity.getHealthTracker()).thenReturn(healthTracker);
        when(healthTracker.getCurrentHealthValue()).thenReturn(50);

        qBurn = new QuicksandBurnStatus(entity, 5, 2, pos);
    }

    /**
     * Tests that when the entity is on the same position as specified
     * by the status, the effect remains active.
     */
    @Test
    public void applyEffectSamePosition() {
        when(pos.tileEquals(Mockito.any(SquareVector.class))).thenReturn(true);
        qBurn.applyEffect();
        Assert.assertTrue(qBurn.getActive());
    }

    /**
     * Tests that when the entity is not on the same position as specified
     * by the status, the effect is set to inactive.
     */
    @Test
    public void applyEffectDifferentPosition() {
        when(pos.tileEquals(Mockito.any(SquareVector.class))).thenReturn(false);
        qBurn.applyEffect();
        Assert.assertFalse(qBurn.getActive());
    }
}