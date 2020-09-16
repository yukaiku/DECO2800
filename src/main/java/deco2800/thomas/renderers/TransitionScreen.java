package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.thomas.managers.*;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.swamp.SwampWorld;
import deco2800.thomas.worlds.tundra.TundraWorld;
import deco2800.thomas.worlds.volcano.VolcanoWorld;

public class TransitionScreen implements Renderer {
    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Render the black background
        BlackBackground black = new BlackBackground();
        black.render(batch, camera);

        // Check what world the player is in to display the respective transition screen
        if (GameManager.get().getWorld() instanceof TundraWorld) {
            // System.out.println("tundra");
        }
        if (GameManager.get().getWorld() instanceof VolcanoWorld) {
            // System.out.println("volcano");
        }
        if (GameManager.get().getWorld() instanceof SwampWorld) {
            // System.out.println("swamp");
        }
        if (GameManager.get().getWorld() instanceof DesertWorld) {
            // System.out.println("desert");
        }

        // Render the modal
        batch.begin();
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture("pause");
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2,
                camera.position.y - sprite.getHeight()/2);
        batch.end();
    }
}
