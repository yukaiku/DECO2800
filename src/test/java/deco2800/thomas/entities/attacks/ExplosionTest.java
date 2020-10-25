package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.AbstractTask;

import deco2800.thomas.util.WorldUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    @Before
    public void setup() {
        // Mock a texture manager, a texture and an animation
        Texture texture = mock(Texture.class);
        when(texture.getWidth()).thenReturn(10);
        when(texture.getHeight()).thenReturn(10);
        Array<TextureRegion> animation = new Array<>();
        animation.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));

        TextureManager textureManager = mock(TextureManager.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        when(textureManager.getAnimationFrames(anyString())).thenReturn(animation);

        // Mock the game manager, and a CombatManager
        GameManager gameManager = mock(GameManager.class);
        mockStatic(WorldUtil.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);
    }

    /**
     * Tests the constructor creates a valid instance from arguments.
     */
    @Test
    public void testValidConstructor() {
        Explosion explosion = new Explosion(1, 1, 10, EntityFaction.ALLY);

        assertEquals(1, explosion.getCol(), 0.001);
        assertEquals(1, explosion.getRow(), 0.001);
        assertEquals(RenderConstants.PROJECTILE_RENDER, explosion.getRenderOrder());
        assertEquals(10, explosion.getDamage());
        assertEquals(EntityFaction.ALLY, explosion.getFaction());
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
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.ALLY);
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

        // Create explosion to test on, and set tasks
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.ALLY);
        explosion.setCombatTask(combatTask);

        // Call on tick, then verify that the entity was remove from the world
        explosion.onTick(0);
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(explosion);
    }

    /**
     * Tests that the entity death occurs when no combat task is set.
     */
    @Test
    public void testProjectileDeath2() {
        // Create explosion to test on
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.ALLY);

        // Call on tick, then verify that the entity was remove from the world
        explosion.onTick(0);
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(explosion);
    }

    /**
     * Tests that getFrame returns valid texture regions.
     */
    @Test
    public void testGetFrame() {
        // Create explosion to test on
        Explosion explosion = new Explosion(0, 0, 10, EntityFaction.ALLY);

        // Run animation
        assertNotNull(explosion.getFrame(0.1f));
        assertNotNull(explosion.getFrame(1f));
        assertNotNull(explosion.getFrame(10f));
        assertNotNull(explosion.getFrame(100f));
    }
}
