package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class CharacterInfo implements Renderer {
    private String texture;

    /**
     * Set the texture based on the character
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Render the black background
        BlackBackground black = new BlackBackground();
        black.render(batch, camera);

        // Render the modal
        batch.begin();
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture(texture);
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2,
                camera.position.y - sprite.getHeight()/2);
        batch.end();
    }
}
