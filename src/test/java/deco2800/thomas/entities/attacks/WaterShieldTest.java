package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class WaterShieldTest extends BaseGDXTest {
    private Peon peon;
    private AbstractWorld abstractWorld;
    private long lifeTime;

    @Before
    public void setUp() {
        peon = mock(Peon.class);

        mockStatic(GameManager.class);

        GameManager gameManager = mock(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        abstractWorld = mock(AbstractWorld.class);
        doNothing().when(abstractWorld).removeEntity(any(AbstractEntity.class));
        when(gameManager.getWorld()).thenReturn(abstractWorld);

        // Mock texture manager
        TextureManager textureManager = mock(TextureManager.class);
        Texture testTexture = mock(Texture.class);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(testTexture.getWidth()).thenReturn(10);
        when(testTexture.getHeight()).thenReturn(10);
        when(textureManager.getTexture(anyString())).thenReturn(testTexture);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        when(peon.getCol()).thenReturn(1f);
        when(peon.getRow()).thenReturn(2f);
        when(peon.getRowRenderLength()).thenReturn(5f);
        when(peon.getColRenderLength()).thenReturn(10f);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        lifeTime = 10;
    }

    /**
     * Test create a constructor then check for the attributes
     */
    @Test
    public void testValidConstructor() {
        WaterShield waterShield = new WaterShield(peon, lifeTime);

        assertEquals(1f, waterShield.getCol(), 0.1f);
        assertEquals(2f, waterShield.getRow(), 0.1f);
        assertEquals(5f, waterShield.getRowRenderLength(), 0.1f);
        assertEquals(10f, waterShield.getColRenderLength(), 0.1f);
        assertEquals(RenderConstants.PEON_EFFECT_RENDER, waterShield.getRenderOrder());
        assertEquals(EntityFaction.ALLY, waterShield.getFaction());
        verify(peon, times(1)).lockHealth();
    }

    /**
     * Test onTick function when the lifetime is not expired
     * yet, which means the effect of the water shield still exist
     */
    @Test
    public void testWaterShieldNotExpired() {
        WaterShield waterShield = new WaterShield(peon, lifeTime);
        when(peon.getCol()).thenReturn(2f);
        when(peon.getRow()).thenReturn(3f);

        assertEquals(1f, waterShield.getCol(), 0.1f);
        assertEquals(2f, waterShield.getRow(), 0.1f);

        waterShield.onTick(0);

        assertEquals(2f, waterShield.getCol(), 0.1f);
        assertEquals(3f, waterShield.getRow(), 0.1f);
    }

    /**
     * Test the ocTick function when the life time is expired
     * and the effect of the Watershield disappear
     */
    @Test
    public void testWaterShieldExpired() {
        WaterShield waterShield = new WaterShield(peon, lifeTime);
        when(peon.getCol()).thenReturn(2f);
        when(peon.getRow()).thenReturn(3f);

        assertEquals(1f, waterShield.getCol(), 0.1f);
        assertEquals(2f, waterShield.getRow(), 0.1f);

        // Call onTick until time expired
        for (int i = 0; i <= lifeTime + 1; i++) {
            waterShield.onTick(0);
        }

        assertEquals(2f, waterShield.getCol(), 0.1f);
        assertEquals(3f, waterShield.getRow(), 0.1f);
        verify(peon, atLeastOnce()).unlockHealth();
        verify(abstractWorld, atLeastOnce()).removeEntity(any(AbstractEntity.class));
    }
}
