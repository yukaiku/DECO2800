package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EffectRenderer implements Renderer {
    private static final String COMMON_DIR_PATH = "resources/storyline/effect/";
    private String effect = "";


    private ParticleEffect particleEffect;

    public EffectRenderer() {
        particleEffect = new ParticleEffect();
        particleEffect.start();
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * Load the correct Particle Effect file and texture(s) based on Zone Type
     */
    public void loadParticleFile() {
        String filePath = effect + "/" + effect + ".p";
        String textureDir = effect;

        particleEffect.load(Gdx.files.internal(COMMON_DIR_PATH + filePath),
                Gdx.files.internal(COMMON_DIR_PATH + textureDir));
    }

    /**
     * Called when the Player moves to the next Zone:
     * - Reset allowRendering back to its original value when the object was first initialized
     * and counter back to 0.
     * - Get new according eventDuration for the current new Zone.
     * - Load new according files for the current new Zone.
     */

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        particleEffect.getEmitters().first().setPosition(camera.position.x - camera.viewportWidth / 2,
                camera.position.y + camera.viewportHeight / 2);

        particleEffect.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        particleEffect.draw(batch);
        batch.end();

        if (particleEffect.isComplete()) {
            particleEffect.reset();
        }
    }
}
