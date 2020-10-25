package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.monsters.Orc;
import deco2800.thomas.managers.*;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class OrcTest extends BaseGDXTest {
    private AbstractWorld world;
    private PlayerPeon playerPeon;
    private Orc volcanoOrc;
    private Orc swampOrc;
    private Orc desertOrc;
    private Orc tundraOrc;

    private GameManager gameManager;
    private EnemyManager enemyManager;
    private TextureManager textureManager;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(GameManager.class);
        enemyManager = mock(EnemyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        SoundManager soundManager = mock(SoundManager.class);
        StatusEffectManager seManager = mock(StatusEffectManager.class);

        gameManager = mock(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getManager(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(gameManager.getManager(InputManager.class)).thenReturn(inputManager);
        when(gameManager.getManager(EnemyManager.class)).thenReturn(enemyManager);
        when(gameManager.getManager(TextureManager.class)).thenReturn(textureManager);
        when(gameManager.getManager(SoundManager.class)).thenReturn(soundManager);
        when(gameManager.getManager(StatusEffectManager.class)).thenReturn(seManager);
        when(GameManager.getManagerFromInstance(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);
        when(GameManager.getManagerFromInstance(SoundManager.class)).thenReturn(soundManager);
        when(GameManager.getManagerFromInstance(StatusEffectManager.class)).thenReturn(seManager);

        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> orcTexture = new Array<>();
        orcTexture.add(new TextureRegion(new Texture("resources/enemies/orc_tundra.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(orcTexture);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        world = mock(AbstractWorld.class);
        playerPeon = mock(PlayerPeon.class);
        when(GameManager.get().getWorld()).thenReturn(world);
        when(GameManager.get().getWorld().getPlayerEntity()).thenReturn(playerPeon);
        when(GameManager.get().getWorld().getType()).thenReturn("Test");

        volcanoOrc = new Orc(EnemyIndex.Variation.VOLCANO, 100, 0.1f, 1, 1, 1, 1);
        desertOrc = new Orc(EnemyIndex.Variation.DESERT, 100, 0.1f, 1, 1, 1, 1);
        swampOrc = new Orc(EnemyIndex.Variation.SWAMP, 100, 0.1f, 1, 1, 1, 1);
        tundraOrc = new Orc(EnemyIndex.Variation.TUNDRA, 100, 0.1f, 1, 1, 1, 1);
    }

    @Test
    public void testConstructors() {
        assertEquals(volcanoOrc.getObjectName(), "Volcano Orc");
        assertEquals(desertOrc.getObjectName(), "Desert Orc");
        assertEquals(swampOrc.getObjectName(), "Swamp Orc");

        assertEquals(tundraOrc.getObjectName(), "Tundra Orc");
        assertEquals(volcanoOrc.getTexture(), "orcVolcano");
        assertEquals(desertOrc.getTexture(), "orcDesert");
        assertEquals(swampOrc.getTexture(), "orcSwamp");
        assertEquals(tundraOrc.getTexture(), "orcTundra");

        assertEquals(volcanoOrc.getSpeed(), 0.1f, 0.01);
        assertEquals(desertOrc.getCurrentHealth(), 100);
    }

    @Test
    public void testDetection() {
        tundraOrc.detectTarget();
        assertNotNull(tundraOrc.getTarget());
        tundraOrc.pursueTarget();
        playerPeon = null;
        tundraOrc.pursueTarget();
        tundraOrc.attackPlayer();
        tundraOrc.detectTarget();
    }


    @Test
    public void testDeath() {
        tundraOrc.death();
        verify(enemyManager, times(1)).removeWildEnemy(any());
    }

    @Test
    public void testAttack() {
        tundraOrc.attackPlayer();
        tundraOrc.detectTarget();
        tundraOrc.attackPlayer();
        assertTrue(tundraOrc.getCombatTask() instanceof MeleeAttackTask);
    }

    @Test
    public void testAnimationFrame() {
        Array<TextureRegion> orcArray = new Array<>();
        orcArray.add(new TextureRegion(new Texture("resources/enemies/orc_tundra.png")));
        assertEquals(orcArray.get(0).getTexture().toString(), tundraOrc.getFrame(0).getTexture().toString());
    }

    @Test
    public void testCopy() {
        assertEquals(swampOrc.deepCopy().getMaxHealth(), swampOrc.getMaxHealth());
        assertEquals(swampOrc.deepCopy().getSpeed(), swampOrc.getSpeed(), 0.01f);
        assertEquals(swampOrc.deepCopy().getTexture(), swampOrc.getTexture());
    }
}
