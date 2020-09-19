package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.renderers.components.DebugComponent;
import deco2800.thomas.renderers.components.HotbarComponent;
import deco2800.thomas.renderers.components.QuestTrackerComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * The overlay renderer holds a list of components and it
 * will render them continuously in the render method as well
 * as update its position to with the camera.
 */
public class OverlayRenderer implements Renderer {
    private float x;
    private float y;
    private float width;
    private float height;
    private List<OverlayComponent> components;

    /**
     * In order to render a component we have to add it to
     * the component list. If we want to remove a component out of
     * the overlay, we just need to remove it out of the component list
     */
    public OverlayRenderer() {
        this.components = new ArrayList<>();
        this.components.add(new DebugComponent(this));
        this.components.add(new HotbarComponent(this));
        this.components.add(new QuestTrackerComponent(this));
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.setX(camera.position.x - camera.viewportWidth / 2);
        this.setY(camera.position.y - camera.viewportHeight / 2);
        this.setWidth(camera.viewportWidth);
        this.setHeight(camera.viewportHeight);

        for (OverlayComponent component : this.components) {
            component.render(batch, camera);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
