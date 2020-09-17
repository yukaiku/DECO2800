package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.renderers.components.DebugComponent;

import java.util.ArrayList;
import java.util.List;

public class OverlayRenderer implements Renderer {
    private float originX;
    private float originY;
    private float width;
    private float height;
    private List<OverlayComponent> components;

    public OverlayRenderer() {
        this.components = new ArrayList<>();
        this.components.add(new DebugComponent(this));
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.setOriginX(camera.position.x - camera.viewportWidth / 2);
        this.setOriginY(camera.position.y - camera.viewportHeight / 2);
        this.setWidth(camera.viewportWidth);
        this.setHeight(camera.viewportHeight);

        for (OverlayComponent component : this.components) {
            component.render(batch);
        }
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
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
