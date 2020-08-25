package deco2800.thomas.managers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * A suite of tests for the CollisionManager class.
 */
public class CollisionManagerTest {
    private CollisionManager manager;

    @Before
    public void setup() {
        manager = CollisionManager.get();
    }

    @After
    public void teardown() {

    }

    /**
     * Tests the method boundingBoxesOverlap on a series of known overlapping bounding boxes.
     */
    @Test
    public void overlappingBoundingBoxes() {
        
    }

    /**
     * Tests the method boundingBoxesOverlap on a series of known non-overlapping bounding boxes.
     */
    @Test
    public void nonOverlappingBoundingBoxes() {

    }
}
