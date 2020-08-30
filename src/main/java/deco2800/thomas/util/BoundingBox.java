package deco2800.thomas.util;

/**
 * Represents an Axis Aligned Bounding Box for collision checking,
 * implemented with existing SquareVectors as used in the game engine.
 * Note: the origin vector is stored as a reference, so if set to the
 * reference of the position vector of an entity, it will automatically
 * update the position of the bounds.
 */
public class BoundingBox {
    private SquareVector bottomLeft;
    private float width, height;

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
        bottomLeft = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a new instance of a BoundingBox by copying an existing instance.
     * @apiNote this will create a clone, and will not copy by reference.
     * @param bounds existing BoundingBox to copy.
     */
    public BoundingBox(BoundingBox bounds) {
        bottomLeft = new SquareVector(bounds.getLeft(), bounds.getBottom());
        width = bounds.getRight() - bounds.getLeft();
        height = bounds.getTop() - bounds.getBottom();
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
        return bottomLeft.getCol() + width;
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
        return bottomLeft.getRow() + height;
    }

    /**
     * Returns whether two given bounding boxes overlap.
     * @param bounds BoundingBox to compare to.
     * @return True if boxes overlap, false otherwise.
     */
    public boolean overlaps(BoundingBox bounds) {
        if (this.getLeft() > bounds.getRight() ||
                this.getBottom() > bounds.getTop() ||
                this.getRight() < bounds.getLeft() ||
                this.getTop() < bounds.getBottom()) {
            return false;
        }
        return true;
    }
}
