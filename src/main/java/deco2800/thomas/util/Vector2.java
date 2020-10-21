package deco2800.thomas.util;

import java.util.Objects;

public class Vector2 {
	private float x;
	private float y;

	/**
	 * Constructor for a Vector2
	 *
	 * @param x the x value for the vector
	 * @param y the y value for the vector
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for the x value of the vector
	 *
	 * @return the x value
	 */
	public float getX() {
		return x;
	}

	/**
	 * Getter for the y value of the vector
	 *
	 * @return the y value
	 */
	public float getY() {
		return y;
	}

	/**
	 * Setter for the x value
	 *
	 * @param x the x value to be set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Setter for the y value
	 *
	 * @param y the y value to be set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Equals Method returns true iff the two objects are equal
	 * based on their X and Y Value.
	 *
	 * @return true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2)) {
			return false;
		}
		Vector2 vector = (Vector2) obj;
        return vector.getX() == this.getX() && vector.getY() == this.getY();
    }

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
