package deco2800.thomas.worlds;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;

/**
 * Set of tests for the AbstractWorld class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class AbstractWorldTest extends BaseGDXTest {
    /**
     * Test class for testing on AbstractWorld.
     */
    private class TestAbstractWorld extends AbstractWorld {

        protected void generateWorld() {
            // No action required.
        }

        /**
         * Generates the tiles for the world
         */

        protected void generateTiles() {

        }

        @Override
        public List<AbstractDialogBox> returnAllDialogues() {
            return null;
        }
    }

    /**
     * Test entity for testing in AbstractWorld
     */
    private class TestAbstractEntity extends AbstractEntity {
        @Override
        public void onTick(long i) {
            // No action required.
        }
    }

    @Mock
    private GameManager mockGM;

    private TextureManager mockTM;

    @Before
    public void setup() {
        // Mock game manager
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockGM);

        // Mock texture manager
        mockTM = new TextureManager();
        when(GameManager.getManagerFromInstance(TextureManager.class))
                .thenReturn(mockTM);
    }

    /**
     * Tests that an empty list is returned when no entities are
     * found within bounds.
     */
    @Test
    public void getEntityInBoundsTestEmpty() {
        // Add at least 1 dummy entity to a test world
        TestAbstractWorld world = new TestAbstractWorld();
        TestAbstractEntity entity = new TestAbstractEntity();
        entity.setCol(20);
        entity.setRow(20);
        world.addEntity(entity);

        // Get list of all entities in bounds
        BoundingBox bounds = new BoundingBox(new SquareVector(0, 0), 10, 10);
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(bounds);

        // Should be an empty list
        assertEquals(0, collidingEntities.size());
    }

    /**
     * Tests that a list of entities is return when entities are
     * found within bounds.
     */
    @Test
    public void getEntityInBoundsTestMany() {
        // Add at least 2 dummy entities to a test world
        TestAbstractWorld world = new TestAbstractWorld();
        TestAbstractEntity entityA = new TestAbstractEntity();
        entityA.setCol(20);
        entityA.setRow(20);
        world.addEntity(entityA);
        TestAbstractEntity entityB = new TestAbstractEntity();
        entityB.setCol(20);
        entityB.setRow(20);
        world.addEntity(entityB);

        // Get list of all entities in bounds
        BoundingBox bounds = new BoundingBox(new SquareVector(20, 20), 50, 50);
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(bounds);
        assertEquals(2, collidingEntities.size());
        assertTrue(collidingEntities.contains(entityA));
        assertTrue(collidingEntities.contains(entityB));
    }
}
