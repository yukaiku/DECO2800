package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.attacks.*;
import deco2800.thomas.entities.enemies.bosses.DesertDragon;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.entities.enemies.bosses.TundraDragon;
import deco2800.thomas.entities.enemies.bosses.VolcanoDragon;
import deco2800.thomas.managers.*;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.renderers.components.BossHealthComponent;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.tasks.combat.*;
import deco2800.thomas.util.WorldUtil;
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
@PrepareForTest({GameManager.class, WorldUtil.class})
public class DragonTest extends BaseGDXTest {
    private AbstractWorld world;
    private PlayerPeon playerPeon;
    private VolcanoDragon volcanoDragon;
    private SwampDragon swampDragon;
    private DesertDragon desertDragon;
    private TundraDragon tundraDragon;

    private GameManager gameManager;
    private EnemyManager enemyManager;

    @Before
    public void setUp() throws Exception {
        // Mock floating damage
        FloatingDamageComponent fdc = mock(FloatingDamageComponent.class);
        PowerMockito.mockStatic(WorldUtil.class);
        when(WorldUtil.getFloatingDamageComponent()).thenReturn(fdc);
        when(WorldUtil.colRowToWorldCords(anyFloat(), anyFloat())).thenCallRealMethod();

        PowerMockito.mockStatic(GameManager.class);
        enemyManager = mock(EnemyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        SoundManager soundManager = mock(SoundManager.class);
        StatusEffectManager seManager = mock(StatusEffectManager.class);
        ScreenManager screenManager = mock(ScreenManager.class);

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
        when(GameManager.getManagerFromInstance(ScreenManager.class)).thenReturn(screenManager);

        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> dragonTexture = new Array<>();
        dragonTexture.add(new TextureRegion(new Texture("resources/enemies/dragon_volcano.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(dragonTexture);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        GameScreen screen = mock(GameScreen.class);
        when(screenManager.getCurrentScreen()).thenReturn(screen);
        OverlayRenderer renderer = mock(OverlayRenderer.class);
        when(screen.getOverlayRenderer()).thenReturn(renderer);
        BossHealthComponent component = mock(BossHealthComponent.class);
        when(renderer.getComponentByInstance(any())).thenReturn(component);

        world = mock(AbstractWorld.class);
        Tile tile = new Tile("quicksand", 0, 0);
        when(world.getTile(anyFloat(), anyFloat())) .thenReturn(tile);
        playerPeon = mock(PlayerPeon.class);
        when(GameManager.get().getWorld()).thenReturn(world);
        when(GameManager.get().getWorld().getPlayerEntity()).thenReturn(playerPeon);

        volcanoDragon = new VolcanoDragon(1000, 0.1f, 1);
        swampDragon = new SwampDragon (1000, 0.1f, 2);
        desertDragon = new DesertDragon(1000, 0.1f, 3);
        tundraDragon = new TundraDragon(1000, 0.1f, 4);
    }

    @Test
    public void testConstructors() {
        assertEquals(volcanoDragon.getObjectName(), "Chusulth");
        assertEquals(desertDragon.getObjectName(), "Doavnaen");
        assertEquals(tundraDragon.getObjectName(), "Diokiedes");
        assertEquals(swampDragon.getObjectName(), "Siendiadut");

        assertEquals(volcanoDragon.getSpeed(), 0.1f, 0.01);
        assertEquals(volcanoDragon.getCurrentHealth(), 1000);
        assertEquals(volcanoDragon.getVariationString(), "volcano");
    }

    @Test
    public void testSummonGoblin() {
        swampDragon.hitByTarget();
        swampDragon.summonGoblin();
        swampDragon.getCombatTask().onTick(1);
        verify(enemyManager, times(1)).spawnSpecialEnemy(eq("swampGoblin"), anyFloat(), anyFloat());
    }

    @Test
    public void testOnHit() {
        assertEquals(desertDragon.applyDamage(1, DamageType.COMMON), 0);
        assertEquals(desertDragon.applyDamage(1, DamageType.COMMON), 1);
        assertNotNull(desertDragon.getTarget());
    }

    @Test
    public void testVolcanoDragonElementalAttack() {
        volcanoDragon.hitByTarget();
        volcanoDragon.elementalAttack();
        volcanoDragon.hitByTarget();
        volcanoDragon.elementalAttack();
        assertTrue(volcanoDragon.getCombatTask() instanceof FireBombAttackTask);
        volcanoDragon.getCombatTask().onTick(1);
        verify(world, times(9)).addEntity(any(Explosion.class));
    }

    @Test
    public void testSwampDragonElementalAttack() {
        swampDragon.hitByTarget();
        swampDragon.elementalAttack();
        assertTrue(swampDragon.getCombatTask() instanceof ScorpionStingAttackTask);
    }

    @Test
    public void testDesertDragonElementalAttack() {
        desertDragon.hitByTarget();
        desertDragon.elementalAttack();
        assertTrue(desertDragon.getCombatTask() instanceof SandTornadoAttackTask);
    }

    @Test
    public void testTundraDragonElementalAttack() {
        tundraDragon.hitByTarget();
        tundraDragon.elementalAttack();
        assertTrue(tundraDragon.getCombatTask() instanceof IceBreathTask);
        tundraDragon.getCombatTask().onTick(1);
        verify(world, times(1)).addEntity(any(Freeze.class));
    }

    @Test
    public void testVolcanoBreathAttack() {
        volcanoDragon.hitByTarget();
        volcanoDragon.breathAttack();
        volcanoDragon.getCombatTask().onTick(1);
        verify(world, times(3)).addEntity(any(VolcanoFireball.class));
    }

    @Test
    public void testSwampBreathAttack() {
        swampDragon.hitByTarget();
        swampDragon.breathAttack();
        swampDragon.getCombatTask().onTick(1);
        verify(world, times(1)).addEntity(any(PlayerFireball.class));
    }

    @Test
    public void testDesertBreathAttack() {
        desertDragon.hitByTarget();
        desertDragon.breathAttack();
        desertDragon.getCombatTask().onTick(1);
        verify(world, times(1)).addEntity(any(DesertFireball.class));
    }

    @Test
    public void testTundraBreathAttack() {
        tundraDragon.hitByTarget();
        tundraDragon.breathAttack();
        tundraDragon.getCombatTask().onTick(1);
        verify(world, times(1)).addEntity(any(Iceball.class));
    }

    @Test
    public void testDeath() {
        volcanoDragon.hitByTarget();
        volcanoDragon.death();
        PowerMockito.verifyStatic(WorldUtil.class);
        WorldUtil.removeEntity(any(AbstractEntity.class));
        verify(world, times(1)).setOrbEntity(any(Orb.class));
    }

    @Test
    public void testAnimationFrame() {
        volcanoDragon.hitByTarget();
        Array<TextureRegion> dragonArray = new Array<>();
        dragonArray.add(new TextureRegion(new Texture("resources/enemies/dragon_volcano.png")));
        assertEquals(dragonArray.get(0).getTexture().toString(), volcanoDragon.getFrame(0).getTexture().toString());
    }

}
