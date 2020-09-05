package deco2800.thomas.tasks;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for ApplyDamageOnCollisionTaskTest class
 */
public class ApplyDamageOnCollisionTaskTest extends BaseGDXTest {
    // Used to verify task execution
    private AgentEntity agentEntity;
    private CombatEntity combatEntity;
    private GameManager gameManager;
    private AbstractWorld abstractWorld;

    /**
     * Prepare for testing by mocking relevant classes.
     */
    @Before
    public void setup() {
        // Mock game manager
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);

        // Mock abstract world
        abstractWorld = mock(AbstractWorld.class);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(combatEntity, agentEntity)));

        // Mock combat entity and agent entity (so that they're on different factions)
        combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.Evil);
        agentEntity = mock(AgentEntity.class);
        when(agentEntity.getFaction()).thenReturn(EntityFaction.Ally);
    }

    /**
     * Verifies that the applyDamage method is called when the entities
     * collide. A passing test is when the reduceHealth and getDamage methods are called
     * from the player peon and fireball respectively.
     */
    public void applyingDamageOnCollisionTest() {
        // Start task
        ApplyDamageOnCollisionTask task = new ApplyDamageOnCollisionTask(combatEntity, 1);
        task.onTick(1);

        // Verify getDamage and reduceHealth are called
        verify(combatEntity).getDamage();
        verify(agentEntity).reduceHealth(anyInt());

        // Verify state change
        assertTrue(task.isComplete());
    }
}