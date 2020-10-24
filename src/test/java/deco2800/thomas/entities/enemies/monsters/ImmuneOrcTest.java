package deco2800.thomas.entities.enemies.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.LoadedPeon;
import deco2800.thomas.managers.*;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Tests the ImmuneOrc class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class, LoadedPeon.class})
public class ImmuneOrcTest extends BaseGDXTest {

    // the ImmuneOrc instance which will be tested
    private ImmuneOrc spyOrc;

    // a gameManager instance which will be mocked for some tests
    private GameManager gameManager;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the ImmuneOrc MUST be mocked here.
     */
    @Before
    public void setUp() throws Exception {
        // mocks the game manager and texture manager, so that an ImmuneOrc can be instantiated
        PowerMockito.mockStatic(GameManager.class);
        gameManager = mock(GameManager.class);
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

        // mocks the sound manager
        SoundManager soundManager = mock(SoundManager.class);
        when(gameManager.getManager(SoundManager.class)).thenReturn(soundManager);
        when(GameManager.getManagerFromInstance(SoundManager.class)).thenReturn(soundManager);

        // mocks the loaded peon's credit method
        PowerMockito.mockStatic(LoadedPeon.class);
        PowerMockito.doNothing().when(LoadedPeon.class);
        LoadedPeon.credit(anyInt());

        // mocks the status effect manager
        StatusEffectManager statusEffectManager = mock(StatusEffectManager.class);
        doNothing().when(statusEffectManager).removeEffectsOnEntity(any(AgentEntity.class));;
        when(gameManager.getManager(StatusEffectManager.class)).thenReturn(statusEffectManager);
        when(GameManager.getManagerFromInstance(StatusEffectManager.class)).thenReturn(statusEffectManager);



        // mocks managers and components related to Floating Damage numbers
        PowerMockito.mockStatic(WorldUtil.class);
        FloatingDamageComponent floatingDamageComponent = mock(FloatingDamageComponent.class);
        doNothing().when(floatingDamageComponent).add(anyInt(), eq(Color.RED), any(float[].class), anyInt());
        when(WorldUtil.getFloatingDamageComponent()).thenReturn(floatingDamageComponent);
        when(WorldUtil.colRowToWorldCords(anyFloat(), anyFloat())).thenReturn(new float[]{});

        spyOrc = spy(new ImmuneOrc());
    }

    /**
     * Tests that the ImmuneOrc is immune to damage types that are not of
     * the special type NOT_IMMUNE. This interaction is handled by applyDamage().
     */
    @Test
    public void applyImmuneDamage() {
        spyOrc.applyDamage(10, DamageType.FIRE);
        spyOrc.applyDamage(10, DamageType.SWAMPY_WATER);
        spyOrc.applyDamage(10, DamageType.ICE);
        spyOrc.applyDamage(10, DamageType.SAND_I_GUESS);
        spyOrc.applyDamage(10, DamageType.COMMON);

        Assert.assertEquals(100, spyOrc.getCurrentHealth());
    }

    /**
     * Tests that the ImmuneOrc is successfully damaged by the special damage
     * type NOT_IMMUNE. This interaction is handled by applyDamage().
     */
    @Test
    public void applyNonImmuneDamage() {
        spyOrc.applyDamage(10, DamageType.NOT_IMMUNE);

        Assert.assertEquals(90, spyOrc.getCurrentHealth());
    }

    /**
     * Tests that the ImmuneOrc's overridden death() method successfully
     * calls for the enemy manager to remove the spyOrc instance.
     */
    @Test
    public void deathRemovesOrc() {
        // mocks all managers used in the death() method
        EnemyManager enemyManager = mock(EnemyManager.class);
        PlayerManager playerManager = mock(PlayerManager.class);
        doNothing().when(playerManager).grantWizardSkill(any(WizardSkills.class));
        AbstractWorld world = mock(AbstractWorld.class);
        Tile tile = mock(Tile.class);
        when(world.getTile(anyFloat(), anyFloat())).thenReturn(tile);
        doNothing().when(world).addEntity(any(AbstractEntity.class));
        when(gameManager.getWorld()).thenReturn(world);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(PlayerManager.class)).thenReturn(playerManager);
        when(GameManager.get().getWorld().getType()).thenReturn("Test");

        spyOrc.death();
        verify(enemyManager, times(1)).removeWildEnemy(spyOrc);
    }

    /**
     * Tests that the ImmuneOrc's overridden death() method successfully calls for
     * the player manager to grant the player a new skill.
     */
    @Test
    public void deathGrantsSkill() {
        // mocks all managers used in the death() method
        EnemyManager enemyManager = mock(EnemyManager.class);
        PlayerManager playerManager = mock(PlayerManager.class);
        doNothing().when(enemyManager).removeWildEnemy(spyOrc);
        AbstractWorld world = mock(AbstractWorld.class);
        Tile tile = mock(Tile.class);
        when(world.getTile(anyFloat(), anyFloat())).thenReturn(tile);
        doNothing().when(world).addEntity(any(AbstractEntity.class));
        when(gameManager.getWorld()).thenReturn(world);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(PlayerManager.class)).thenReturn(playerManager);
        when(GameManager.get().getWorld().getType()).thenReturn("Test");

        spyOrc.death();
        verify(playerManager, times(1)).grantWizardSkill(WizardSkills.SANDTORNADO);
    }

    /**
     * Tests that the ImmuneOrc's overridden deepCopy() method successfully creates
     * a new deep copy of an Immune Orc when called.
     */
    @Test
    public void deepCopy() {
        Orc otherOrc = spyOrc.deepCopy();

        spyOrc.applyDamage(10, DamageType.NOT_IMMUNE);
        Assert.assertEquals(spyOrc.getCurrentHealth(), 90);
        Assert.assertEquals(otherOrc.getCurrentHealth(), 100);
    }
}