package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.renderers.components.*;

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
    private final List<OverlayComponent> components;

    public OverlayRenderer() {
        this.components = new ArrayList<>();
    }

    /**
     * In order to render a component we have to add it to the component list. The method
     * will add prepared components into the component list. If we want to remove a component
     * out of the overlay, we just need to remove it out of the component list
     */
    public void setUpComponents() {
        this.getComponents().add(new HealthBarComponent(this));
        // this.getComponents().add(new BossHealthBarComponent(this));
        this.getComponents().add(new MinimapComponent(this));
        this.getComponents().add(new DebugComponent(this));
        this.getComponents().add(new HotbarComponent(this));
        this.getComponents().add(new CurrencyComponent(this));
        this.getComponents().add(new QuestTrackerComponent(this));
        this.getComponents().add(new GuidelineComponent(this));
        this.getComponents().add(new FloatingDamageComponent(this));
        this.getComponents().add(new BossHealthComponent(this));
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.setX(camera.position.x - camera.viewportWidth / 2);
        this.setY(camera.position.y - camera.viewportHeight / 2);
        this.setWidth(camera.viewportWidth);
        this.setHeight(camera.viewportHeight);

        for (OverlayComponent component : this.getComponents()) {
            component.render(batch);
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

    public List<OverlayComponent> getComponents() {
        return this.components;
    }

    /**
     * Returns a particular component based on class.
     * @param type Class to return
     * @return OverlayComponent of type, or null
     */
    public <T extends OverlayComponent> T getComponentByInstance(Class<T> type) {
        for (OverlayComponent component : this.components) {
            if (component.getClass() == type) {
                return type.cast(component);
            }
        }
        return null;
    }
}
