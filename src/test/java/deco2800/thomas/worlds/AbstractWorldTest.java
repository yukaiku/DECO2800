package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Set of tests for the AbstractWorld class.
 */
public class AbstractWorldTest {
    /**
     * Test class for testing on AbstractWorld.
     */
    private class TestAbstractWorld extends AbstractWorld {
        @Override
        protected void generateWorld() {
            // No action required.
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
        entity.setBounds(new BoundingBox(new SquareVector(20, 20), 10, 10));
        world.addEntity(entity);

        // Get list of all entities in bounds
        BoundingBox bounds = new BoundingBox(new SquareVector(0, 0), 10, 10);
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(bounds);

        // Should be an empty list
        assertEquals(0, collidingEntities.size());
    }
}
