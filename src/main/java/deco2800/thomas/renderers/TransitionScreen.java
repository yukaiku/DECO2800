package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.TutorialWorld;
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

        // Render the modal
        batch.begin();
        Texture img = null;
        // Check what world the player is in to display the respective transition screen
        if (GameManager.get().getWorld() instanceof TundraWorld) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("trs-tundra");
        } else if (GameManager.get().getWorld() instanceof VolcanoWorld) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("trs-volcano");
        } else if (GameManager.get().getWorld() instanceof SwampWorld) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("trs-swamp");
        } else if (GameManager.get().getWorld() instanceof DesertWorld) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("trs-desert");
        } else if (GameManager.get().getWorld() instanceof TutorialWorld) {
            img = GameManager.get().getManager(TextureManager.class).getTexture("dialog-box");
        }
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2,
                camera.position.y - sprite.getHeight()/2);
        batch.end();
    }
}
