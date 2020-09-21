package deco2800.thomas.entities.attacks;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;

import deco2800.thomas.util.WorldUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Tests the Projectile class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class ProjectileTest extends BaseGDXTest {
    /**
     * Tests the constructor creates a valid instance from arguments.
     */
    @Test
    public void testValidConstructor() {
        Projectile projectile = new Projectile(0, 10, 0, 10, 5, EntityFaction.ALLY);

        assertEquals(0, projectile.getCol(), 0.001);
        assertEquals(10, projectile.getRow(), 0.001);
        assertEquals(0, projectile.getRenderOrder());
        assertEquals(10, projectile.getDamage());
        assertEquals(5, projectile.getSpeed(), 0.001);
        assertEquals(EntityFaction.ALLY, projectile.getFaction());
    }

    /**
     * Tests that the movement and combat tasks tick
     */
    @Test
    public void testTasksTick() {
        // Mock the tasks
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isComplete()).thenReturn(false);
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(false);

        // Create projectile to test on, and set tasks
        Projectile projectile = new Projectile(0, 10, 0, 10, 5, EntityFaction.ALLY);
        projectile.setMovementTask(movementTask);
        projectile.setCombatTask(combatTask);

        // Call on tick, and verify the tasks where called appropriately
        projectile.onTick(0);
        verify(movementTask).onTick(anyLong());
        verify(combatTask).onTick(anyLong());
    }

    /**
     * Tests that the projectile removes itself from the world when its tasks complete.
     */
    @Test
    public void testProjectileDeath() {
        // Mock the tasks
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isComplete()).thenReturn(false);
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(true);

        // Mock the game manager, and a CombatManager
        GameManager gameManager = mock(GameManager.class);
        mockStatic(WorldUtil.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);

        // Create projectile to test on, and set tasks
        Projectile projectile = new Projectile(0, 10, 0, 10, 5, EntityFaction.ALLY);
        projectile.setMovementTask(movementTask);
        projectile.setCombatTask(combatTask);

        // Call on tick, then verify that the entity was remove from the world
        projectile.onTick(0);
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(projectile);
    }
}
