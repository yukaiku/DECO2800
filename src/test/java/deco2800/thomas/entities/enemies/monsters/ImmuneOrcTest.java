package deco2800.thomas.entities.enemies.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.WildcardMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
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
@PrepareForTest({GameManager.class, WorldUtil.class})
public class ImmuneOrcTest extends BaseGDXTest {

    private ImmuneOrc spyOrc;

    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
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

        PowerMockito.mockStatic(WorldUtil.class);
        FloatingDamageComponent floatingDamageComponent = mock(FloatingDamageComponent.class);
        doNothing().when(floatingDamageComponent).add(anyInt(), eq(Color.RED), any(float[].class), anyInt());
        when(WorldUtil.getFloatingDamageComponent()).thenReturn(floatingDamageComponent);
        when(WorldUtil.colRowToWorldCords(anyFloat(), anyFloat())).thenReturn(new float[]{});

        spyOrc = spy(new ImmuneOrc());
    }

    @Test
    public void applyImmuneDamage() {
        spyOrc.applyDamage(10, DamageType.FIRE);

        Assert.assertEquals(100, spyOrc.getCurrentHealth());
    }

    @Test
    public void applyNonImmuneDamage() {
        spyOrc.applyDamage(10, DamageType.NOT_IMMUNE);

        Assert.assertEquals(90, spyOrc.getCurrentHealth());
    }

    @Test
    public void deathRemovesOrc() {
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

        spyOrc.death();
        verify(enemyManager, times(1)).removeWildEnemy(spyOrc);
    }

    @Test
    public void deathGrantsSkill() {
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

        spyOrc.death();
        verify(playerManager, times(1)).grantWizardSkill(WizardSkills.SANDTORNADO);
    }

    @Test
    public void deepCopy() {
        Orc otherOrc = spyOrc.deepCopy();

        spyOrc.applyDamage(10, DamageType.NOT_IMMUNE);
        Assert.assertEquals(spyOrc.getCurrentHealth(), 90);
        Assert.assertEquals(otherOrc.getCurrentHealth(), 100);
    }
}