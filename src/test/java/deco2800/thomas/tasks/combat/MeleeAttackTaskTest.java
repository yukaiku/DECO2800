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
 * Test for MeleeAttackTaskTest class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class MeleeAttackTaskTest extends BaseGDXTest {
    // Used to verify task execution
    private GameManager gameManager;

    /**
     * Prepare for testing by mocking relevant classes.
     */
    @Before
    public void setup() {
        // Mock game manager
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
    }

    /**
     * Verifies that the applyDamage method is called when the entities
     * collide. A passing test is when the reduceHealth is called from the player peon.
     */
    @Test
    public void applyingDamageOnCollisionTest() {
        // Mock combat entity and agent entity (so that they're on different factions)
        CombatEntity combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.EVIL);
        Peon peon = mock(Peon.class);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock abstract world
        AbstractWorld abstractWorld = mock(AbstractWorld.class);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(combatEntity, peon)));

        // Start task
        MeleeAttackTask task = new MeleeAttackTask(combatEntity, new SquareVector(0, 0), 10, 10, 50);
        task.onTick(1);

        // Verify reduceHealth is called
        verify(peon).applyDamage(anyInt(), any(DamageType.class));

        // Verify state change
        assertTrue(task.isComplete());
    }

    /**
     * Verifies that no damage is applied when there is no collision, and that the
     * task completes after 1 tick.
     */
    @Test
    public void noDamageTest() {
        // Mock combat entity and agent entity (so that they're on different factions)
        CombatEntity combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.EVIL);
        Peon peon = mock(Peon.class);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock abstract world
        AbstractWorld abstractWorld = mock(AbstractWorld.class);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>());

        // Start task
        MeleeAttackTask task = new MeleeAttackTask(combatEntity, new SquareVector(0, 0), 10, 10, 50);
        task.onTick(1);

        // Verify reduceHealth is not called
        verify(peon, never()).applyDamage(anyInt(), any(DamageType.class));

        // Verify state change
        assertTrue(task.isComplete());
    }
}