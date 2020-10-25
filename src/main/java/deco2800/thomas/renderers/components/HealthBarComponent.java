package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

/**
 * A component in the overlay renderer to display the player's HealthBar
 */
public class HealthBarComponent extends OverlayComponent {

    /** HealthTracker to be referenced by assignHealthTexture*/
    private HealthTracker playerHealth;

    public HealthBarComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);

        // Set player HealthTracker
        playerHealth = ((Peon)GameManager.get().getWorld().getPlayerEntity())
                .getHealthTracker();
    }

    /**
     * Render the healthBar texture to the current batch from the
     * OverlayRenderer
     * @param batch the sprite batch to draw into
     */
    @Override
    public void render(SpriteBatch batch) {
        // Begin Batch
        batch.begin();

        // Get sprite using assignHealthTexture
        Sprite healthTexture = new Sprite(
                GameManager.get().getManager(TextureManager.class).getTexture(
                        this.assignHealthTexture(playerHealth)));

        // Updates player Health tracker
        assignPlayerHealth(((Peon)GameManager.get().getWorld()
                .getPlayerEntity()).getHealthTracker());

        healthTexture.setPosition(overlayRenderer.getX() + 0.8f * overlayRenderer.getWidth(),
                overlayRenderer.getY() + 0.8f * overlayRenderer.getHeight());
        healthTexture.setSize(0.2f * overlayRenderer.getWidth(),
                0.1f * overlayRenderer.getHeight());
        healthTexture.draw(batch);

        // End batch
        batch.end();
    }

    /**
     * Assigns a string for the TextureManager to access a corresponding
     * healthBar texture for the currentHealthValue of the player's
     * healthTracker.
     *
     * This is done by rounding down the currentHealthValue to the nearest
     * multiple of five (5) and creating a string of the form "healthXX",
     * where XX are the digits of the rounded down health value.
     *
     * @param playerHealth HealthTracker instance for which the appropriate
     *                     texture is being assigned
     * @return texture id for interacting with the TextureManager
     */
    private String assignHealthTexture(HealthTracker playerHealth) {
        StringBuilder textureId = new StringBuilder("health");

        // Get most recent Health Value
        int health = playerHealth.getCurrentHealthValue();
        int maxHealth = playerHealth.getMaxHealthValue();
        int percentValue =
                (int) Math.round(((float) health / maxHealth) * 100.0);

        // Round down to nearest multiple of 5
        int healthValueRounded;
        try {
            healthValueRounded = percentValue - (percentValue % 5);
        } catch (ArithmeticException err) {
            healthValueRounded = 0;
        }

        // Append to textureId
        textureId.append(healthValueRounded);

        return textureId.toString();
    }

    /**
     * Set the current HealthTracker instance of the renderer to a new
     * HealthTracker.
     *
     * @param playerHealth HealthTracker instance to which this renderer will
     *                    refer.
     */
    public void assignPlayerHealth(HealthTracker playerHealth) {
        this.playerHealth = playerHealth;
    }

}
