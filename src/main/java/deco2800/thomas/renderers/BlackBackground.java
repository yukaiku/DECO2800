package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BlackBackground implements Renderer{
    ShapeRenderer shapeRenderer;

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        // Render the black background
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0, 0, 0, 1f));
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
