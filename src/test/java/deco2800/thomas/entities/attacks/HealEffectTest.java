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
public class HealEffectTest extends BaseGDXTest {
    private Peon peon;
    private AbstractWorld abstractWorld;
    private int restoreHealth;

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
    }

    /**
     * Test create constructor with the new
     * health (= currentHealth + restoreHealth) < entity's max health
     * Testcase: 10 + 10 < 100
     */
    @Test
    public void testConstructorWhenNewHealthLessThanMaxHealth() {
        restoreHealth = 10;
        when(peon.getCurrentHealth()).thenReturn(10);
        when(peon.getMaxHealth()).thenReturn(100);
        HealEffect healEffect = new HealEffect(peon, restoreHealth);

        assertEquals(1f, healEffect.getCol(), 0.1f);
        assertEquals(2f, healEffect.getRow(), 0.1f);
        assertEquals(5f, healEffect.getRowRenderLength(), 0.1f);
        assertEquals(10f, healEffect.getColRenderLength(), 0.1f);
        assertEquals(RenderConstants.PEON_EFFECT_RENDER, healEffect.getRenderOrder());
        assertEquals(EntityFaction.ALLY, healEffect.getFaction());
        verify(peon,times(1)).regenerateHealth(restoreHealth);
        verify(peon,times(0)).setCurrentHealthValue(anyInt());
    }

    /**
     * Test create constructor with the new
     * health (= currentHealth + restoreHealth) > entity's max health
     * Testcase: 100 + 10 > 100
     */
    @Test
    public void testConstructorWhenNewHealthGreaterThanMaxHealth() {
        restoreHealth = 10;
        when(peon.getCurrentHealth()).thenReturn(100);
        when(peon.getMaxHealth()).thenReturn(100);
        HealEffect healEffect = new HealEffect(peon, restoreHealth);

        assertEquals(1f, healEffect.getCol(), 0.1f);
        assertEquals(2f, healEffect.getRow(), 0.1f);
        assertEquals(5f, healEffect.getRowRenderLength(), 0.1f);
        assertEquals(10f, healEffect.getColRenderLength(), 0.1f);
        assertEquals(RenderConstants.PEON_EFFECT_RENDER, healEffect.getRenderOrder());
        assertEquals(EntityFaction.ALLY, healEffect.getFaction());
        verify(peon,times(0)).regenerateHealth(anyInt());
        verify(peon,times(1)).setCurrentHealthValue(100);
    }
}
