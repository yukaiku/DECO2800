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
    }

    /**
     * Tests bounds works with overlapping boxes.
     */
    @Test
    public void overlappingTest() {
        BoundingBox boxA = new BoundingBox(new SquareVector(0, 0), 30, 30);
        BoundingBox boxB = new BoundingBox(new SquareVector(20, 20), 10, 10);
        assertTrue(boxA.overlaps(boxB));
    }
}
