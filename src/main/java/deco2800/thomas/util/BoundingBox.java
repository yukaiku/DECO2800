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
     */
    public BoundingBox(SquareVector origin, float width, float height) {
        bottomLeft = new SquareVector(origin);
        topRight = new SquareVector(bottomLeft.getCol() + width, topRight.getRow() + height);
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
}
