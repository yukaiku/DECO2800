package deco2800.thomas.util;

import com.google.gson.annotations.Expose;

public class SquareVector {
	@Expose
	private float col; // col corresponds to the X coordinate
	@Expose
	private float row; // row corresponds to the Y coordinate

	/**
	 * Constructor for a SquareVector
	 *
	 * @param col the col value for the vector
	 * @param row the row value for the vector
	 */
	public SquareVector(float col, float row) {
		this.col = col;
		this.row = row;
	}

	public SquareVector(SquareVector vector) {
		this.col = vector.col;
		this.row = vector.row;
	}

	public SquareVector(String vector) {
		String[] pos = vector.split(",", 2);
		this.col = Float.parseFloat(pos[0]);
		this.row = Float.parseFloat(pos[1]);
	}


	public SquareVector() {

	}

	/**
	 * Getter for the col value of the vector
	 *
	 * @return the col value
	 */
	public float getCol() {
		return col;
	}

	/**
	 * Getter for the row value of the vector
	 *
	 * @return the row value
	 */
	public float getRow() {
		return row;
	}

	/**
	 * Setter for the column value
	 *
	 * @param col the column value to be set
	 */
	public void setCol(float col) {
		this.col = col;
	}

	/**
	 * Setter for the row value
	 *
	 * @param col the row value to be set
	 */
	public void setRow(float col) {
		this.row = col;
	}

	public SquareVector add(SquareVector toAdd) {
		float row = getRow() + toAdd.getRow();
		float col = getCol() + toAdd.getCol();
		return new SquareVector(col, row);
	}

	/**
	 * Calculates the distance between two coordinates on a 2D plane. Based off of the cubeDistance function.
	 * Uses Manhattan distance.
	 *
	 * @param vcol the x coordinate
	 * @param vrow the y coordinate
	 * @return the distance between the two coordinates
	 */
	public float distance(float vcol, float vrow) {
		return distance(new SquareVector(vcol, vrow));
	}


	public float distance(SquareVector vector) {
		return Math.abs(Math.round(getCol()) - Math.round(vector.getCol()))
				+ (float)Math.abs(Math.round(getRow()) - Math.round(vector.getRow()));
	}

	private float distanceAsCartesian(SquareVector point) {
		return (float) Math.sqrt(Math.pow(point.col - col, 2) + Math.pow(point.row - row, 2));
	}


	public void moveToward(SquareVector point, double distance) {
		if (distanceAsCartesian(point) < distance) {
			this.col = point.col;
			this.row = point.row;
			return;
		}

		double deltaCol = this.col - point.col;
		double deltaRow = this.row - point.row;
		double angle;

		angle = Math.atan2(deltaRow, deltaCol) + Math.PI;

		double xShift = Math.cos(angle) * distance;
		double yShift = Math.sin(angle) * distance;

		this.col += xShift;
		this.row += yShift;
	}

	public boolean isCloseEnoughToBeTheSame(SquareVector vector) {
		return MathUtil.floatEquality(this.getCol(), vector.getCol())
				&& MathUtil.floatEquality(this.getRow(), vector.getRow());
	}

	public boolean isCloseEnoughToBeTheSame(SquareVector vector, float e) {
		return MathUtil.floatEquality(this.getCol(), vector.getCol(), e)
				&& MathUtil.floatEquality(this.getRow(), vector.getRow(), e);
	}

	/**
	 * Equals Method returns true iff the two objects are equal
	 * based on their col and row values.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SquareVector)) {
			return false;
		}
		SquareVector vector = (SquareVector) obj;
		return isCloseEnoughToBeTheSame(vector);
	}

	/**
	 * Returns whether a given SquareVector is set on the same Tile
	 * as this SquareVector.
	 *
	 * @param obj The other SquareVector.
	 * @return Whether this SquareVector and obj share a Tile.
	 */
	public boolean tileEquals(Object obj) {
		if (!(obj instanceof SquareVector)) {
			return false;
		}
		boolean colTrue;
		boolean rowTrue;
		SquareVector vector = (SquareVector) obj;

		// round all positions to make comparisons possible
		float roundCol = Math.round(getCol());
		float roundRow = Math.round(getRow());
		float roundVecCol = Math.round(vector.getCol());
		float roundVecRow = Math.round(vector.getRow());

		// check if the columns originate from the same Tile
		if (roundCol <= getCol()) {
			colTrue = roundCol == roundVecCol || roundCol == Math.round(vector.getCol() - 0.5);
		} else {
			colTrue = roundCol == roundVecCol || roundCol == Math.round(vector.getCol() + 0.5);
		}

		// check if the rows originate from the same Tile
		if (roundRow <= getRow()) {
			rowTrue = roundRow == roundVecRow || roundRow == Math.round(vector.getRow() - 0.5);
		} else {
			rowTrue = roundRow == roundVecRow || roundRow == Math.round(vector.getRow() + 0.5);
		}

		return rowTrue && colTrue;
	}

	@Override
	public int hashCode() {
		return ((31 * (int) this.getCol()) + 17) * (int) this.getRow();
	}

	@Override
	public String toString() {
		return String.format("%f, %f", col, row);
	}

}
