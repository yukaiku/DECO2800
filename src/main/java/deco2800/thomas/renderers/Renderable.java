package deco2800.thomas.renderers;

/**
 * An object that can be rendered on the screen.
 * Renderables should have a texture that they can return
 * when asked using the onRender function.
 *
 * Textures should be size suitable to the game.
 */
public interface Renderable {

    /**
     * Renderables must impliment the onRender function.
     * This function allows the current rendering system to request a texture
     * from the object being rendered.
     *
     * Returning null will render an error image in this items place.
     * @return The texture to be rendered onto the screen
     */
    String getTexture();

    /**
     * Gets the col coordinate of the renderable object
     * @return the col coordinate of the object
     */
    float getCol();

    /**
     * Getter for the row coordinate of the renderable object
     * @return the row coordinate of the object
     */
    float getRow();

    /**
     * Getter for the height coordinate of the renderable object
     * @return the height coordinate of the object
     */
    int getHeight();

    /**
     * Gets the length of the renderable in the x direction
     * @return the lenghth in the x driection
     */
    float getColRenderLength();

    /**
     * Gets the length of the renderable in the y direction
     * @return the length in the y direction
     */
    float getRowRenderLength();
}
