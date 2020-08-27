package deco2800.thomas.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Array2DTest {
	private Array2D<Object> array;

	@Test
	public void testConstructor() {
		array = new Array2D<Object>(4, 3);
		assertEquals(4, array.getWidth());
		assertEquals(3, array.getHeight());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroWidthConstructor() {
		array = new Array2D<Object>(0, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroHeightConstructor() {
		array = new Array2D<Object>(3, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeWidthConstructor() {
		array = new Array2D<Object>(-2, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeHeightConstructor() {
		array = new Array2D<Object>(3, -2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeXGet() {
		array = new Array2D<Object>(3, 5);
		array.get(-1, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeYGet() {
		array = new Array2D<Object>(3, 5);
		array.get(1, -4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLargerThanWidthXGet() {
		array = new Array2D<Object>(3, 5);
		array.get(3, 4);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLargerThanHeightYGet() {
		array = new Array2D<Object>(3, 5);
		array.get(2, 5);
	}

	@Test
	public void testGetEmptyCell() {
		array = new Array2D<Object>(3, 5);
		Object test = array.get(1, 3);
		assertNull(test);
	}

	@Test
	public void testGetValidCell() {
		array = new Array2D<Object>(3, 5);
		Object element = new Object();
		array.set(1, 3, element);

		assertEquals(element, array.get(1, 3));
	}

	@Test
	public void testSetValidEmptyCell() {
		array = new Array2D<Object>(3, 5);
		Object element = new Object();
		array.set(1, 3, element);
		assertEquals(element, array.get(1, 3));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeXSet() {
		array = new Array2D<Object>(3, 5);
		array.set(-1, 4, new Object());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNegativeSGet() {
		array = new Array2D<Object>(3, 5);
		array.set(1, -4, new Object());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLargerThanWidthXSet() {
		array = new Array2D<Object>(3, 5);
		array.set(3, 4, new Object());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLargerThanHeightYSet() {
		array = new Array2D<Object>(3, 5);
		array.set(2, 5, new Object());
	}

	@Test
	public void testSetValidFilledCell() {
		array = new Array2D<Object>(3, 5);
		Object element1 = new Object();
		Object element2 = new Object();
		array.set(1, 3, element1);
		array.set(1, 3, element2);
		assertEquals(element2, array.get(1, 3));
	}

	@Test
	public void testGetWidth() {
		array = new Array2D<Object>(3, 5);
		assertEquals(3, array.getWidth());
	}

	@Test
	public void testGetHeight() {
		array = new Array2D<Object>(3, 5);
		assertEquals(5, array.getHeight());
	}

	@Test
	public void testGetListLoopOnceEmpty() {
		array = new Array2D<Object>(1, 1);
		List<Object> result = array.getList();
		ArrayList<Object> list = new ArrayList<>();
		assertEquals(list, result);
	}

	@Test
	public void testGetListLoopOnceWithValue() {
		array = new Array2D<Object>(1, 1);
		Object element = new Object();
		array.set(0, 0, element);
		List<Object> result = array.getList();
		ArrayList<Object> list = new ArrayList<>();
		list.add(element);
		assertEquals(list, result);
	}

	@Test
	public void testGetListLoopMultipleWithValue() {
		array = new Array2D<Object>(3, 4);
		Object element1 = new Object();
		Object element2 = new Object();
		Object element3 = new Object();
		array.set(0, 0, element1);
		array.set(1, 3, element2);
		array.set(2, 2, element3);
		List<Object> result = array.getList();
		ArrayList<Object> list = new ArrayList<>();
		list.add(element1);
		list.add(element3);
		list.add(element2);
		assertEquals(list, result);
	}

	@Test
	public void testGetListLoopMultipleEmpty() {
		array = new Array2D<Object>(3, 4);
		List<Object> result = array.getList();
		ArrayList<Object> list = new ArrayList<>();
		assertEquals(list, result);
	}
}
