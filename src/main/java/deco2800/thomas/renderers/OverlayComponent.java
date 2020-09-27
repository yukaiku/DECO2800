package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * An abstract class for components displayed in the overlay renderer
 */
public abstract class OverlayComponent {
    /* Reference to the parent overlay */
    protected OverlayRenderer overlayRenderer;

    public OverlayComponent(OverlayRenderer overlayRenderer) {
        this.overlayRenderer = overlayRenderer;
    }

    /**
     * Each component will have a render method to let the
     * overlay renderer call it continuously
     *
     * @param batch the sprite batch to draw into
     */
    public abstract void render(SpriteBatch batch);
}
