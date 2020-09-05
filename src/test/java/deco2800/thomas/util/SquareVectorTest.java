package deco2800.thomas.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareVectorTest {

	@Test
	public void testDistanceSameLocation() {
		assertEquals(0, new SquareVector(0, 0).distance(new SquareVector(0, 0)), 0.01);
	}

	@Test
	public void testDistanceDiagonalOneCell() {
		assertEquals(2, new SquareVector(0, 0).distance(new SquareVector(1, 1)), 0.01);
	}

	@Test
	public void testDistanceLongerDistance() {
		assertEquals(20, new SquareVector(11, 9).distance(new SquareVector(0, 0)), 0.01);
	}

	@Test
	public void testSetCol() {
		SquareVector vector = new SquareVector(3, 4);
		vector.setCol(19);
		assertEquals(19, vector.getCol(), 0);
	}

	@Test
	public void testSetRow() {
		SquareVector vector = new SquareVector(3, 4);
		vector.setRow(19);
		assertEquals(19, vector.getRow(), 0);
	}

	@Test
	public void testConstructor() {
		SquareVector vector = new SquareVector(5, 4);
		assertEquals(4, vector.getRow(), 0);
		assertEquals(5, vector.getCol(), 0);
	}

	@Test
	public void testCloneConstructor() {
		SquareVector vector1 = new SquareVector(1, 1);
		SquareVector vector2 = new SquareVector(vector1);

		assertEquals(1, vector2.getCol(), 0);
		assertEquals(1, vector2.getRow(), 0);
		assertEquals(vector1, vector2);
	}

	@Test
	public void testEmptyConstructor() {
		SquareVector vector = new SquareVector();
		assertEquals(0, vector.getCol(), 0);
		assertEquals(0, vector.getRow(), 0);
	}

}
