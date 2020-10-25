package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.AbstractTask;

import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Tests the Freeze class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class FreezeTest extends BaseGDXTest {
    private GameManager gameManager;

    @Before
    public void setup() {
        // Mock the game manager, and a CombatManager
        gameManager = mock(GameManager.class);
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
    }

    /**
     * Tests that only the combat task is updated.
     */
    @Test
    public void testTasksTick() {
        // Mock the tasks
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isComplete()).thenReturn(false);
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(false).thenReturn(true);

        // Create test class and tick it
        Freeze freeze = new Freeze(0, 0, 0, EntityFaction.ALLY, 0f);
        freeze.setCombatTask(combatTask);
        freeze.setMovementTask(movementTask);
        freeze.onTick(0);
        freeze.onTick(0);
        freeze.onTick(0);

        // Verify only the combat task ticks, and only while combat task is not complete
        verify(combatTask, times(2)).onTick(anyLong());
        verify(movementTask, never()).onTick(anyLong());
    }

    /**
     * Verifies entity is remove after its animation is done.
     */
    @Test
    public void testEntityDeath() {
        // Mock the tasks
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(true);

        // Create test class, run animation and tick it
        Freeze freeze = new Freeze(0, 0, 0, EntityFaction.ALLY, 0f);
        freeze.setCombatTask(combatTask);
        freeze.getFrame(100f);
        freeze.onTick(0);

        // Verify it was removed
        verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(freeze);
    }

    /**
     * Tests getFrame returns valid texture region.
     */
    @Test
    public void testGetFrame() {
        // Create test class, and run animation
        Freeze freeze = new Freeze(0, 0, 0, EntityFaction.ALLY, 0f);
        assertNotNull(freeze.getFrame(0.1f));
        assertNotNull(freeze.getFrame(1f));
        assertNotNull(freeze.getFrame(10f));
        assertNotNull(freeze.getFrame(100f));
    }
}
