package deco2800.thomas.tasks.combat;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test for ApplyDamageOverTimeTask class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class ApplyDamageOverTimeTaskTest extends BaseGDXTest {
    // Used to verify task execution
    private GameManager gameManager;
    private AbstractWorld abstractWorld;
    private CombatEntity combatEntity;
    private Peon peon;

    /**
     * Prepare for testing by mocking relevant classes.
     */
    @Before
    public void setup() {
        // Mock game manager
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);

        // Mock combat entity and agent entity (so that they're on different factions)
        combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.EVIL);
        when(combatEntity.getDamageType()).thenReturn(DamageType.COMMON);
        when(combatEntity.getBounds()).thenReturn(new BoundingBox(new SquareVector(0, 0), 10, 10));
        peon = mock(Peon.class);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock abstract world
        abstractWorld = mock(AbstractWorld.class);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(combatEntity, peon)));
    }

    /**
     * Verifies that the applyDamage method is called when the entities
     * collide. A passing test is when the reduceHealth and getDamage methods are called
     * from the player peon and fireball respectively.
     */
    @Test
    public void applyingDamageOnCollisionTest() {
        // Start task
        ApplyDamageOverTimeTask task = new ApplyDamageOverTimeTask(combatEntity, 1, 0);
        task.onTick(1);

        // Verify getDamage and reduceHealth are called
        verify(combatEntity).getDamage();
        verify(peon).applyDamage(anyInt(), any(DamageType.class));
    }

    /**
     * Ensures that multiple ticks of damage are applied over the lifetime of the
     * task.
     */
    @Test
    public void applyingMultipleTicksOverLifetimeTest() {
        // Start task
        ApplyDamageOverTimeTask task = new ApplyDamageOverTimeTask(combatEntity, 5, 0);
        for (int i = 0; i < 5; i++) {
            task.onTick(i);
        }

        // Verify getDamage and reduceHealth are called each time
        verify(combatEntity, times(5)).getDamage();
        verify(peon, times(5)).applyDamage(anyInt(), any(DamageType.class));
    }

    /**
     * Ensures that ticks follow period.
     */
    @Test
    public void tickPeriodTest() {
        // Start task
        ApplyDamageOverTimeTask task = new ApplyDamageOverTimeTask(combatEntity, 5, 3);
        for (int i = 0; i < 5; i++) {
            task.onTick(i);
        }

        // Verify getDamage and reduceHealth are called only twice
        verify(combatEntity, times(2)).getDamage();
        verify(peon, times(2)).applyDamage(anyInt(), any(DamageType.class));
    }

    /**
     * Tests that the task completes after lifetime is reached.
     */
    @Test
    public void taskLifetimeTest() {
        // Start task
        ApplyDamageOverTimeTask task = new ApplyDamageOverTimeTask(combatEntity, 5, 3);
        for (int i = 0; i < 5; i++) {
            task.onTick(i);
        }

        assertTrue(task.isComplete());
    }
}