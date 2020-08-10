package deco2800.thomas.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2Test {
    private Vector2 vec = null;

    @Before
    public void setup() {
        vec = new Vector2(0 ,0);
    }

    @Test
    public void testConstructor() {
        Vector2 vec1 = new Vector2(3, 4);
        assertEquals(3, vec1.getX(), 0);
        assertEquals(4, vec1.getY(), 0);
    }

    @Test
    public void testGetXZero() {
        assertEquals(0, vec.getX(), 0);
    }

    @Test
    public void testGetYZero() {
        assertEquals(0, vec.getY(), 0);
    }

    @Test
    public void testSetXPositive() {
        vec.setX(24);
        assertEquals(24, vec.getX(), 0);
    }

    @Test
    public void testSetXNegative() {
        vec.setX(-24);
        assertEquals(-24, vec.getX(), 0);
    }

    @Test
    public void testSetYPositive() {
        vec.setY(24);
        assertEquals(24, vec.getY(), 0);
    }

    @Test
    public void testSetYNegative() {
        vec.setY(-24);
        assertEquals(-24, vec.getY(), 0);
    }

    @Test
    public void testEqualsNewVector2() {
        Vector2 vec1 = new Vector2(0, 0);
        assertEquals(vec1, vec);
        assertEquals(vec, vec1);
    }

    @Test
    public void testEqualsItself() {
        assertEquals(vec, vec);
    }

    @Test
    public void testEqualsXSameYDifferent() {
        Vector2 vec1 = new Vector2(2, 4);
        Vector2 vec2 = new Vector2(2, 5);
        assertNotEquals(vec1, vec2);
    }

    @Test
    public void testEqualsYSameXDifferent() {
        Vector2 vec1 = new Vector2(3, 4);
        Vector2 vec2 = new Vector2(2, 4);
        assertNotEquals(vec1, vec2);
    }

    @Test
    public void testNotEqualNonVector2Object() {
        Object fakeVec = new Object();
        assertNotEquals(fakeVec, vec);
        assertNotEquals(vec, fakeVec);
    }

    @Test
    public void testNotEqualsSwappedPositions() {
        Vector2 vec1 = new Vector2(3, 4);
        Vector2 vec2 = new Vector2(4, 3);
        assertNotEquals(vec1, vec2);
        assertNotEquals(vec2, vec1);
    }

    //Equal objects must have same hashCode
    @Test
    public void testHashCode() {
        Vector2 vec1 = new Vector2(0, 0);
        assertEquals(vec.hashCode(), vec1.hashCode());
    }
}
