package deco2800.thomas.util;

/**
 * Represents an Axis Aligned Bounding Box for collision checking,
 * implemented with existing SquareVectors as used in the game engine.
 */
public class BoundingBox {
    private SquareVector bottomLeft;
    private SquareVector topRight;

    /**
     * Creates a new instance of a BoundingBox with the given parameters.
     * @param origin x/y coordinates of bottom left hand corner
     * @param width width of box
     * @param height height of box
     * @throws IllegalArgumentException when the width, or height of box <= 0
     */
    public BoundingBox(SquareVector origin, float width, float height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Width or height of bounding box cannot be negative.");
        }
        bottomLeft = new SquareVector(origin);
        topRight = new SquareVector(bottomLeft.getCol() + width, bottomLeft.getRow() + height);
    }

    /**
     * Creates a new instance of a BoundingBox by copying an existing instance.
     * @param bounds existing BoundingBox to copy.
     */
    public BoundingBox(BoundingBox bounds) {
        bottomLeft = new SquareVector(bounds.getLeft(), bounds.getBottom());
        topRight = new SquareVector(bounds.getRight(), bounds.getTop());
    }

    /**
     * Returns the x position of the left side of the box.
     * @return left side col
     */
    public float getLeft() {
        return bottomLeft.getCol();
    }

    /**
     * Returns the x position of the right side of the box.
     * @return right side col
     */
    public float getRight() {
        return topRight.getCol();
    }

    /**
     * Returns the y position of the bottom side of the box.
     * @return bottom side row
     */
    public float getBottom() {
        return bottomLeft.getRow();
    }

    /**
     * Returns the y position of the top side of the box.
     * @return top side row
     */
    public float getTop() {
        return topRight.getRow();
    }

    /**
     * Returns whether two given bounding boxes overlap.
     * @param bounds BoundingBox to compare to.
     * @return True if boxes overlap, false otherwise.
     */
    public boolean boundingBoxOverlaps(BoundingBox bounds) {
        if (this.getLeft() > bounds.getRight() || this.getBottom() > bounds.getTop() ||
                this.getRight() < bounds.getLeft() || this.getTop() < bounds.getBottom()) {
            return false;
        }
        return true;
    }
}
