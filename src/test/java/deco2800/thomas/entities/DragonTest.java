package deco2800.thomas.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.attacks.Explosion;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.dragons.DesertDragon;
import deco2800.thomas.entities.enemies.dragons.SwampDragon;
import deco2800.thomas.entities.enemies.dragons.VolcanoDragon;
import deco2800.thomas.managers.*;
import deco2800.thomas.tasks.combat.FireBombAttackTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;
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
public class DragonTest extends BaseGDXTest {
    private AbstractWorld world;
    private PlayerPeon playerPeon;
    private Dragon dragon;
    private VolcanoDragon volcanoDragon;
    private SwampDragon swampDragon;
    private DesertDragon desertDragon;

    private GameManager gameManager;
    private EnemyManager enemyManager;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(GameManager.class);
        enemyManager = mock(EnemyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);

        gameManager = mock(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getManager(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(gameManager.getManager(InputManager.class)).thenReturn(inputManager);
        when(gameManager.getManager(EnemyManager.class)).thenReturn(enemyManager);
        when(gameManager.getManager(TextureManager.class)).thenReturn(textureManager);
        when(GameManager.getManagerFromInstance(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        world = mock(AbstractWorld.class);
        Tile tile = new Tile("quicksand", 0, 0);
        when(world.getTile(anyFloat(), anyFloat())) .thenReturn(tile);
        playerPeon = mock(PlayerPeon.class);
        when(GameManager.get().getWorld()).thenReturn(world);
        when(GameManager.get().getWorld().getPlayerEntity()).thenReturn(playerPeon);

        volcanoDragon = new VolcanoDragon("Volcano_dragon", 1, 0.1f, 1000, "dragon_volcano", 1);
        swampDragon = new SwampDragon("swamp_dragon", 1, 0.1f, 1000, "dragon_swamp", 2);
        desertDragon = new DesertDragon("desert_dragon", 1, 0.1f, 1000, "dragon_desert", 3);
    }

    @Test
    public void testConstructor() {
        assertEquals(dragon.getHeight(), 1);
        assertEquals(dragon.getObjectName(), "elder_dragon");
        assertEquals(dragon.getSpeed(), 0.15f, 0.01);
        assertEquals(dragon.getCurrentHealth(), 1000);
    }

    @Test
    public void testSummonGoblin() {
        dragon.summonGoblin();
        verify(enemyManager, times(1)).spawnSpecialEnemy(any(Goblin.class), anyFloat(), anyFloat());
    }

    @Test
    public void testOnHit() {
        assertEquals(dragon.applyDamage(1, DamageType.COMMON), 1);
        assertNotNull(dragon.getTarget());
    }

    @Test
    public void testMeleeAttack() {
        dragon.hitByTarget();
        dragon.elementalAttack();
        assertTrue(dragon.getCombatTask() instanceof MeleeAttackTask);
    }

    @Test
    public void testVolcanoDragonMeleeAttack() {
        volcanoDragon.hitByTarget();
        volcanoDragon.elementalAttack();
        assertTrue(volcanoDragon.getCombatTask() instanceof FireBombAttackTask);
        volcanoDragon.getCombatTask().onTick(1);
        verify(world, times(25)).addEntity(any(Explosion.class));
    }

    @Test
    public void testSummonRangedAttack() {
        dragon.hitByTarget();
        dragon.breathAttack();
        verify(world, times(1)).addEntity(any(Fireball.class));
    }

    @Test
    public void testVolcanoSummonRangedAttack() {
        volcanoDragon.hitByTarget();
        volcanoDragon.breathAttack();
        verify(world, times(3)).addEntity(any(VolcanoFireball.class));
    }

    @Test
    public void testSwampSummonRangedAttack() {
        swampDragon.hitByTarget();
        swampDragon.breathAttack();
        assertTrue(swampDragon.getCombatTask() instanceof ScorpionStingAttackTask);
    }

    @Test
    public void testDesertSummonRangedAttack() {
        desertDragon.hitByTarget();
        desertDragon.breathAttack();
        verify(world, times(1)).addEntity(any(DesertFireball.class));
    }

    @Test
    public void testDeath() {
        dragon.death();
        verify(enemyManager, times(1)).removeBoss();
        verify(world, times(1)).setOrbEntity(any(Orb.class));
    }
}
