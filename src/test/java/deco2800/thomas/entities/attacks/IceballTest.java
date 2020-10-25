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

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Tests the Projectile class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class IceballTest extends BaseGDXTest {
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
     * Tests that the slow is correctly applied to any entities it collides with.
     */
    @Test
    public void testApplySlow() {
        // Mock the tasks
        AbstractTask movementTask = mock(AbstractTask.class);
        when(movementTask.isComplete()).thenReturn(false);
        AbstractTask combatTask = mock(AbstractTask.class);
        when(combatTask.isComplete()).thenReturn(true);

        // Create Iceball to test on
        Iceball iceball = new Iceball(1, 1, 10, 1, EntityFaction.ALLY);
        iceball.setMovementTask(movementTask);
        iceball.setCombatTask(combatTask);

        // Mock dummy peon to collide with, and dummy list to return
        Peon peon = mock(Peon.class);
        ArrayList<AbstractEntity> list = new ArrayList<>();
        list.add(iceball);
        list.add(peon);

        // Mock collision to return dummy entity
        AbstractWorld world = mock(AbstractWorld.class);
        when(world.getEntitiesInBounds(any(BoundingBox.class))).thenReturn(list);
        when(gameManager.getWorld()).thenReturn(world);

        // Tick iceball once, and verify slow is applied to peon
        iceball.onTick(0);
        verify(peon).addEffect(any(SpeedStatus.class));
        verify(peon).applyDamage(10, DamageType.ICE);

        // Tick iceball to completion and verify it is removed from world
        iceball.getFrame(100f);
        iceball.onTick(0);
        verifyStatic(WorldUtil.class, atLeastOnce());
        WorldUtil.removeEntity(iceball);
    }
}
