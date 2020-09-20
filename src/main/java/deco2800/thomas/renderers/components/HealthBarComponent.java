package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.HealthTracker;
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

        // Temporary initialisation a HealthTracker. Will be replaced by an
        // appropriate reference to the actual player HealthTracker
        // TODO: Properly integrate with actual player HealthTracker
        playerHealth = new HealthTracker(100);
    }

    /**
     * Render the healthBar texture to the current batch from the
     * OverlayRenderer
     * @param batch the sprite batch to draw into
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.begin();

        // Get sprite using assignHealthTexture
        Sprite healthTexture = new Sprite(
                GameManager.get().getManager(TextureManager.class).getTexture(
                        this.assignHealthTexture(playerHealth)));

        // Draw texture to batch
        // Temporarily using x = 0 and y = 0
        // TODO: Properly integrate with other UI elements to get appropriate
        //  x and y coordinates
        batch.draw(healthTexture, 0, 0);

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

        // Round down to nearest multiple of 5
        int healthValueRounded = playerHealth.getCurrentHealthValue()
                - (playerHealth.getCurrentHealthValue() % 5);

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
