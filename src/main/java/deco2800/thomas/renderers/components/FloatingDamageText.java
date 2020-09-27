package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.Color;
import deco2800.thomas.Tickable;

/**
 * This class represents a single instance of floating damage.
 */
public class FloatingDamageText implements Tickable {
    /* Private fields for instance */
    private int value;
    private Color color;
    private float x;
    private float y;
    private int life;

    /**
     * Creates an instance of FloatingDamageText.
     * @param value Value to render.
     * @param color Color to render text in.
     * @param x X position in World coordinates.
     * @param y Y position in World coordinates.
     * @param life Lifetime of text.
     */
    public FloatingDamageText(int value, Color color, float x, float y, int life) {
        this.value = value;
        this.color = color;
        this.x = x;
        this.y = y;
        this.life = life;
    }

    /**
     * Gets the value of damage to render as a string.
     * @return Value of damage.
     */
    public String getValue() {
        return String.valueOf(value);
    }

    /**
     * Sets the value of damage to render.
     * @param value Value of damage.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the colour this text should be rendered in.
     * @return Colour to render.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the colour this text should be rendered in.
     * @param color Colour to render.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the X and Y position in world coordinates.
     * @param x X position.
     * @param y Y position.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X position in world coordinates.
     * @return X position in world coordinates.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y position in world coordinates.
     * @return Y position in world coordinates.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the remaining life in ticks before this instance should be destroyed.
     * @return Life remaining in ticks.
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets the lifetime in ticks of this instance before it should be destroyed.
     * @param life Lifetime of this instance in ticks.
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Updates this instances of floating damage text.
     * @param i Ticks since game start.
     */
    public void onTick(long i) {
        life--;
        y++;
    }
}
