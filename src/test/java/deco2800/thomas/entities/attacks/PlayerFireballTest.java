package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.AbstractTask;

import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Tests the PlayerFireball class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class PlayerFireballTest extends BaseGDXTest {
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
     * Tests the spawn method of PlayerFireball.
     */
    @Test
    public void testSpawn() {
        // Mock world
        AbstractWorld world = mock(AbstractWorld.class);
        when(gameManager.getWorld()).thenReturn(world);

        // Try spawn Fireball
        PlayerFireball.spawn(0, 0, 10, 10, 10, 1, 20, EntityFaction.ALLY);
        verify(world).addEntity(argThat(entity -> {
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile)entity;
                return projectile.getMovementTask() instanceof DirectProjectileMovementTask
                        && projectile.getCombatTask() instanceof ApplyDamageOnCollisionTask;
            }
            return false;
        }));
    }
}
