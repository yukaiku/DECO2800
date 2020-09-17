package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public abstract class OverlayComponent {
    protected List<Actor> actors;
    protected OverlayRenderer overlayRenderer;

    public OverlayComponent(OverlayRenderer overlayRenderer) {
        this.overlayRenderer = overlayRenderer;
    }

    /**
     * @param batch
     */
    public abstract void render(SpriteBatch batch);
}
