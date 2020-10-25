package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.AbstractTask;

import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
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
 * Tests the CombatEntity class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, WorldUtil.class})
public class CombatEntityTest extends BaseGDXTest {
    /* Test entity that extends combat entity for testing. */
    public class TestEntity extends CombatEntity {
        public TestEntity() {
            super(0, 0, 0, 10, EntityFaction.ALLY, DamageType.COMMON);
        }

        @Override
        public void onTick(long i) {

        }
    }

    @Before
    public void setup() {
        // Mock the game manager, and a CombatManager
        GameManager gameManager = mock(GameManager.class);
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
     * Tests the constructor creates a valid instance from arguments.
     */
    @Test
    public void testValidConstructor() {
        CombatEntity entity = new TestEntity();

        assertEquals(0, entity.getCol(), 0.001);
        assertEquals(0, entity.getRow(), 0.001);
        assertEquals(0, entity.getRenderOrder());
        assertEquals(10, entity.getDamage());
        assertEquals(EntityFaction.ALLY, entity.getFaction());
        assertEquals(DamageType.COMMON, entity.getDamageType());
    }

    /**
     * Tests getter and setter for Damage.
     */
    @Test
    public void testGetSetDamage() {
        TestEntity entity = new TestEntity();
        entity.setDamage(0);
        assertEquals(0, entity.getDamage());
    }

    /**
     * Tests getter and setter for DamageType.
     */
    @Test
    public void testGetSetDamageType() {
        TestEntity entity = new TestEntity();
        entity.setDamageType(DamageType.ICE);
        assertEquals(DamageType.ICE, entity.getDamageType());
    }

    /**
     * Test getter and setter of MovementTask.
     */
    @Test
    public void testGetSetMovementTask() {
        TestEntity entity = new TestEntity();
        AbstractTask task = mock(AbstractTask.class);
        entity.setMovementTask(task);
        assertEquals(task, entity.getMovementTask());
    }

    /**
     * Test getter and setter of CombatTask.
     */
    @Test
    public void testGetSetCombatTask() {
        TestEntity entity = new TestEntity();
        AbstractTask task = mock(AbstractTask.class);
        entity.setCombatTask(task);
        assertEquals(task, entity.getCombatTask());
    }
}
