package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class Result implements Renderer{
    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Render the black background
        BlackBackground black = new BlackBackground();
        black.render(batch, camera);

        // Render the modal
        batch.begin();
        Texture img;
        if (GameManager.get().getState() == GameManager.State.GAMEOVER) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("defeat");
        } else {
            img = GameManager.get().getManager(TextureManager.class).getTexture("victory");
        }
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2,
                camera.position.y - sprite.getHeight()/2);
        batch.end();
    }
}
