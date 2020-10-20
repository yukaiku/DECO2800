package deco2800.thomas.entities.enemies.monsters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class ImmuneOrcTest extends BaseGDXTest {

    private ImmuneOrc spyOrc;

    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
        PowerMockito.mockStatic(GameManager.class);
        GameManager gameManager = mock(GameManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        Texture texture = mock(Texture.class);
        Array<TextureRegion> textureRegion = new Array<>();
        textureRegion.add(mock(TextureRegion.class));
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);
        when(textureManager.getTexture(anyString())).thenReturn(texture);

        when(GameManager.get()).thenReturn(gameManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);
        when(textureManager.getAnimationFrames(anyString())).thenReturn(textureRegion);

        spyOrc = spy(new ImmuneOrc());
    }

    @Test
    public void applyImmuneDamage() {

    }

    @Test
    public void death() {
    }

    @Test
    public void deepCopy() {
    }
}