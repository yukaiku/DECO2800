package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.managers.TextureManagerTest;
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

        // Mock a texture manager
        TextureManager textureManager = mock(TextureManager.class);
        Texture mockedTexture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(mockedTexture);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(mockedTexture.getWidth()).thenReturn(1);
        when(mockedTexture.getHeight()).thenReturn(1);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // Create projectile to test on, and set tasks
        Projectile projectile = new Projectile(0, 10, 0, 10, 5, EntityFaction.ALLY);
        projectile.setMovementTask(movementTask);
        projectile.setCombatTask(combatTask);

        // Call on tick, then verify that the entity was remove from the world
        projectile.onTick(0);
        projectile.getFrame(20); // Make sure animation runs to completion
        projectile.onTick(0);
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(projectile);
    }
}
