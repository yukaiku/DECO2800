package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import deco2800.thomas.managers.*;

public class PauseModal implements Renderer {
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

		shapeRenderer.setColor(new Color(0, 0, 0, 0.2f));
		shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		// Render the modal
        batch.begin();
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture("dialog-box");
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2,
                camera.position.y + camera.viewportHeight / 2 - sprite.getHeight() * 2);
        batch.end();
    }
}
