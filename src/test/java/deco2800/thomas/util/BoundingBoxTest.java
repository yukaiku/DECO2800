package deco2800.thomas.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the BoundingBox class.
 */
public class BoundingBoxTest {
    /**
     * Tests the constructor works as intended on valid use case.
     */
    @Test
    public void validConstructorTest() {
        BoundingBox box = new BoundingBox(new SquareVector(0, 0), 10, 10);
        assertEquals(0, box.getLeft(), 0);
        assertEquals(0, box.getBottom(), 0);
        assertEquals(10, box.getRight(), 0);
        assertEquals(10, box.getTop(), 0);
    }

    /**
     * Tests the constructor with an invalid width.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidWidthTest() {
        BoundingBox box = new BoundingBox(new SquareVector(0, 0), -10, 10);
    }

    /**
     * Tests the constructor with an invalid height.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidHeightTest() {
        BoundingBox box = new BoundingBox(new SquareVector(0, 0), 10, -10);
    }

    /**
     * Tests bounds works with non-overlapping boxes.
     */
    @Test
    public void nonOverlappingTest() {
        BoundingBox boxA = new BoundingBox(new SquareVector(0, 0), 10, 10);
        BoundingBox boxB = new BoundingBox(new SquareVector(20, 20), 10, 10);
        assertFalse(boxA.overlaps(boxB));
        assertFalse(boxB.overlaps(boxA));
    }

    /**
     * Tests bounds works with overlapping boxes.
     */
    @Test
    public void overlappingTest() {
        BoundingBox boxA = new BoundingBox(new SquareVector(0, 0), 30, 30);
        BoundingBox boxB = new BoundingBox(new SquareVector(0, 0), 10, 10);
        assertTrue(boxA.overlaps(boxB));
        assertTrue(boxB.overlaps(boxA));
    }

    /**
     * Tests the bounds follow a moving origin as expected when the vector
     * reference is moved.
     */
    @Test
    public void movingBoundsTest() {
        SquareVector origin = new SquareVector(0, 0);
        BoundingBox boxA = new BoundingBox(origin, 30, 30);
        BoundingBox boxB = new BoundingBox(new SquareVector(50, 0), 10, 10);

        // No overlap initially
        assertFalse(boxA.overlaps(boxB));

        // Move origin so that they overlap
        origin.setCol(50);
        assertTrue(boxA.overlaps(boxB));
    }

    /**
     * Tests that using the BoundingBox(BoundingBox bounds) constructor
     * creates a clone of the bounding box.
     */
    @Test
    public void cloningConstructorTest() {
        SquareVector origin = new SquareVector(0, 0);
        BoundingBox boxA = new BoundingBox(origin, 30, 30);
        BoundingBox boxB = new BoundingBox(boxA);

        // These will initially overlap
        assertTrue(boxA.overlaps(boxB));

        // Move origin so they no longer overlap
        origin.setCol(50);
        assertFalse(boxA.overlaps(boxB));
    }
}
