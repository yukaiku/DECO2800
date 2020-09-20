package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.attacks.Projectile;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
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
 * Tests the Explosion class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class ExplosionTest extends BaseGDXTest {
    /**
     * Tests the constructor creates a valid instance from arguments.
     */
    @Test
    public void testValidConstructor() {
        Explosion explosion = new Explosion(1, 1, 10, EntityFaction.Ally);

        assertEquals(1, explosion.getCol(), 0.001);
        assertEquals(1, explosion.getRow(), 0.001);
        assertEquals(RenderConstants.PROJECTILE_RENDER, explosion.getRenderOrder());
        assertEquals(10, explosion.getDamage());
        assertEquals(EntityFaction.Ally, explosion.getFaction());
    }

    /**
     * Tests that the combat task tick
     */
    @Test
    public void testTasksTick() {
        // Mock the tas
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(false);

        // Create explosion to test on, and set tasks
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.Ally);
        explosion.setCombatTask(combatTask);

        // Call on tick, and verify the task was called appropriately
        explosion.onTick(0);
        verify(combatTask).onTick(anyLong());
    }

    /**
     * Tests that the projectile removes itself from the world when its tasks complete.
     */
    @Test
    public void testProjectileDeath() {
        // Mock the task
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(true);

        // Mock a texture manager, and a texture
        Texture texture = mock(Texture.class);
        when(texture.getWidth()).thenReturn(10);
        when(texture.getHeight()).thenReturn(10);
        TextureManager textureManager = mock(TextureManager.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);

        // Mock the game manager, and a CombatManager
        GameManager gameManager = mock(GameManager.class);
        mockStatic(WorldUtil.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // Create explosion to test on, and set tasks
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.Ally);
        explosion.setCombatTask(combatTask);

        // Call on tick, then verify that the entity was remove from the world
        explosion.onTick(0);
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(explosion);
    }
}
