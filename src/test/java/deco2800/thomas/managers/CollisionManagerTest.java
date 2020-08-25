package deco2800.thomas.managers;

import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * A suite of tests for the CollisionManager class.
 */
public class CollisionManagerTest {
    /**
     * Test combat entity class for testing CollisionManager.
     */
    private class TestCombatEntity implements CombatEntity {
        private BoundingBox bounds;

        public TestCombatEntity(SquareVector origin, float width, float height) {
            bounds = new BoundingBox(origin, width, height);
        }

        @Override
        public void getHealth() {

        }

        @Override
        public BoundingBox getBoundingBox() {
            return bounds;
        }
    }

    // Reference to collision manager to test with.
    private CollisionManager manager;
    private TestCombatEntity boxA, boxB, boxC, boxD;

    @Before
    public void setup() {
        manager = CollisionManager.get();

        // Pre populate
        // A, B, C do not overlap
        // D overlaps with A, B and C
        boxA = new TestCombatEntity(new SquareVector(10, 10), 20, 20);
        boxB = new TestCombatEntity(new SquareVector(50, 5), 50, 20);
        boxC = new TestCombatEntity(new SquareVector(35, 40), 25, 10);
        boxD = new TestCombatEntity(new SquareVector(25, 20), 35, 25);

        manager.add(boxA);
        manager.add(boxB);
        manager.add(boxC);
        manager.add(boxD);
    }

    @After
    public void teardown() {
        manager.clear();
    }

    /**
     * Tests the method boundingBoxesOverlap on a series of known overlapping bounding boxes.
     */
    @Test
    public void overlappingBoundingBoxes() {
        // We expect to overlap with all boxes, as D overlaps all including itself.
        List<CombatEntity> entities = manager.getAllEntitiesInBounds(boxD.getBoundingBox());
        assertEquals(4, entities.size());
        assertTrue(entities.contains(boxA));
        assertTrue(entities.contains(boxB));
        assertTrue(entities.contains(boxC));
        assertTrue(entities.contains(boxD));
    }

    /**
     * Tests the method boundingBoxesOverlap on a series of known non-overlapping bounding boxes.
     */
    @Test
    public void nonOverlappingBoundingBoxes() {
        // We expect to overlap with boxes A and D only, and not B and C.
        List<CombatEntity> entities = manager.getAllEntitiesInBounds(boxA.getBoundingBox());
        assertEquals(2, entities.size());
        assertTrue(entities.contains(boxA));
        assertTrue(entities.contains(boxD));
    }
}
