package deco2800.thomas.tasks;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.attacks.Projectile;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for DirectProjectileMovementTask class
 */
public class DirectProjectileMovementTaskTest extends BaseGDXTest {
    /**
     * Verifies that the moveTowards method is being called per tick.
     */
    @Test
    public void directMovementCalledTest() {
        SquareVector destination = mock(SquareVector.class);
        Projectile projectile = mock(Projectile.class);

        DirectProjectileMovementTask task = new DirectProjectileMovementTask(projectile, destination, 10);
        task.onTick(0);

        verify(projectile).moveTowards(any(SquareVector.class));
    }

    /**
     * Tests that the task ends after n ticks
     */
    @Test
    public void lifetimeTest() {
        SquareVector destination = mock(SquareVector.class);
        Projectile projectile = mock(Projectile.class);

        DirectProjectileMovementTask task = new DirectProjectileMovementTask(projectile, destination, 5);
        for (int i = 0; i < 5; i++) {
            task.onTick(i);
        }

        assertTrue(task.isComplete());
    }
}