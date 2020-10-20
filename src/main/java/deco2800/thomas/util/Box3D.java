package deco2800.thomas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representation of a box in 3d space, defined by a corner point in XYZ and
 * extends in x (xLength), y (yLength), and z (zLength).
 *
 * @author leggy
 */
public class Box3D {

	private static final Logger LOGGER = LoggerFactory.getLogger(Box3D.class);

	private float x;
	private float y;
	private float z;

	private float xLength;
	private float yLength;
	private float zLength;

	/**
	 * Constructs a new Box3D with the given corner point and dimensions.
	 *
	 * @param x       corner point x-coordinate.
	 * @param y       corner point y-coordinate.
	 * @param z       corner point z-coordinate.
	 * @param xLength xLength (in x).
	 * @param yLength yLength (in y).
	 * @param zLength zLength (in z).
	 */
	public Box3D(float x, float y, float z, float xLength, float yLength, float zLength) {
		LOGGER.info("Creating a box at {},{},{}. Consider not doing this...?", x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
		this.xLength = xLength;
		this.yLength = yLength;
		this.zLength = zLength;
	}

	/**
	 * Constructs a new Box3D based on the given box.
	 *
	 * @param box box to copy.
	 */
	public Box3D(Box3D box) {
		this.x = box.x;
		this.y = box.y;
		this.z = box.z;
		this.xLength = box.xLength;
		this.yLength = box.yLength;
		this.zLength = box.zLength;
	}

	/**
	 * Returns the x coordinate.
	 *
	 * @return Returns the x coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the x coordinate.
	 *
	 * @param x new x coordinate.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate.
	 *
	 * @return Returns the y coordinate.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y coordinate.
	 *
	 * @param y new y coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the z coordinate.
	 *
	 * @return Returns the z coordinate.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the z coordinate.
	 *
	 * @param z new z coordinate.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns the xLength (in x).
	 *
	 * @return Returns the xLength.
	 */
	public float getXLength() {
		return xLength;
	}

	/**
	 * Returns the yLength (in y).
	 *
	 * @return Returns the yLength.
	 */
	public float getYLength() {
		return yLength;
	}

	/**
	 * Returns the zLength (in z).
	 *
	 * @return Returns the zLength.
	 */
	public float getZLength() {
		return zLength;
	}

	/**
	 * Checks for collisions in all directions.
	 *
	 * @param box the Box3D to check for collisions
	 * @return true if there is an overlap, false if there isn't
	 */
	public boolean overlaps(Box3D box) {
		/*
		 * Checking non-collision on all 6 directions.
		 */

		// x smaller
		if (x + xLength < box.x) {
			return false;
		}

		// x larger
		if (x > box.x + box.xLength) {
			return false;
		}

		// y smaller
		if (y + yLength < box.y) {
			return false;
		}

		// y larger
		if (y > box.y + box.yLength) {
			return false;
		}

		// z smaller
		if (z + zLength < box.z) {
			return false;
		}

		// z larger
        return (z <= box.z + box.zLength);
    }

	/**
	 * Gets the diagonal distance of the Box3D.
	 *
	 * @param o the Box3D
	 * @return the diagonal distance of the Box3D
	 */
	public float distance(Box3D o) {
		return (float) (Math.sqrt(Math.pow(o.x - this.x, 2) + Math.pow(o.y - this.y, 2) + Math.pow(o.z - this.z, 2)));
	}

}
