package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.ItemDropTable;
import deco2800.thomas.managers.*;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
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
public class GoblinTest extends BaseGDXTest {
    private AbstractWorld world;
    private PlayerPeon playerPeon;
    private Goblin volcanoGoblin;
    private Goblin swampGoblin;
    private Goblin desertGoblin;
    private Goblin tundraGoblin;

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
        Array<TextureRegion> goblinTexture = new Array<>();
        goblinTexture.add(new TextureRegion(new Texture("resources/enemies/goblin_tundra.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(goblinTexture);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        world = mock(AbstractWorld.class);
        playerPeon = mock(PlayerPeon.class);
        when(GameManager.get().getWorld()).thenReturn(world);
        when(GameManager.get().getWorld().getPlayerEntity()).thenReturn(playerPeon);
        when(GameManager.get().getWorld().getType()).thenReturn("Test");



        volcanoGoblin = new Goblin(EnemyIndex.Variation.VOLCANO, 100, 0.1f, 1, 1, 1);
        desertGoblin = new Goblin(EnemyIndex.Variation.DESERT, 100, 0.1f, 1, 1, 1);
        swampGoblin = new Goblin(EnemyIndex.Variation.SWAMP, 100, 0.1f, 1, 1, 1);
        tundraGoblin = new Goblin(EnemyIndex.Variation.TUNDRA, 100, 0.1f, 1, 1, 1);
    }

    @Test
    public void testConstructors() {
        assertEquals(volcanoGoblin.getObjectName(), "Volcano Goblin");
        assertEquals(desertGoblin.getObjectName(), "Desert Goblin");
        assertEquals(swampGoblin.getObjectName(), "Swamp Goblin");
        assertEquals(tundraGoblin.getObjectName(), "Tundra Goblin");

        assertEquals(volcanoGoblin.getTexture(), "goblinVolcano");
        assertEquals(desertGoblin.getTexture(), "goblinDesert");
        assertEquals(swampGoblin.getTexture(), "goblinSwamp");
        assertEquals(tundraGoblin.getTexture(), "goblinTundra");

        assertEquals(volcanoGoblin.getSpeed(), 0.1f, 0.01);
        assertEquals(desertGoblin.getCurrentHealth(), 100);
    }

    @Test
    public void testDetection() {
        tundraGoblin.detectTarget();
        assertNotNull(tundraGoblin.getTarget());
    }

    /*
    @Test
    public void testPursuit() {
        tundraOrc.detectTarget();
        assertNotNull(tundraOrc.getTarget());
    }
     */

    @Test
    public void testDeath() {
        tundraGoblin.death();
        verify(enemyManager, times(1)).removeSpecialEnemy(any());
    }

    @Test
    public void testAttack() {
        tundraGoblin.detectTarget();
        tundraGoblin.attackPlayer();
        assertTrue(tundraGoblin.getCombatTask() instanceof MeleeAttackTask);
    }

    @Test
    public void testAnimationFrame() {
        Array<TextureRegion> goblinArray = new Array<>();
        goblinArray.add(new TextureRegion(new Texture("resources/enemies/goblin_tundra.png")));
        assertTrue(goblinArray.get(0).getTexture().toString().equals(tundraGoblin.getFrame(0).getTexture().toString()));
    }

    @Test
    public void testCopy() {
        assertEquals(swampGoblin.deepCopy().getMaxHealth(), swampGoblin.getMaxHealth());
        assertEquals(swampGoblin.deepCopy().getSpeed(), swampGoblin.getSpeed(), 0.01f);
        assertEquals(swampGoblin.deepCopy().getTexture(), swampGoblin.getTexture());
    }
}
