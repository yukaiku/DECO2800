package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.entities.enemies.bosses.DesertDragon;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

import java.util.Arrays;

public class BossHealthBarComponent extends OverlayComponent {
    /** HealthTracker to be referenced by assignHealthTexture*/
    private HealthTracker bossHealth;
    private Boss boss;
    private boolean bossExists;
    private float scaleDown;

    public BossHealthBarComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);

        // Set Boss HealthTracker
        try {
            bossHealth =
                    GameManager.get().getManager(EnemyManager.class).getBoss().getHealthTracker();
            boss = (Dragon)GameManager.get()
                    .getManager(EnemyManager.class)
                    .getBoss();
        } catch (NullPointerException err) {
            bossHealth = new HealthTracker(100);
            boss = new DesertDragon(200, 0.03f, 4);
        }

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

        // Updates Boss Health tracker
        assignBossHealth();
        if (bossExists) {
            // Get sprite using assignHealthTexture
            Sprite healthTexture = new Sprite(
                    GameManager.get().getManager(TextureManager.class).getTexture(
                            this.assignHealthTexture(bossHealth)));

            // Get Boss Position
//            float x_pos = ((Dragon) boss).getPosition().getCol();
//            float y_pos = ((Dragon) boss).getPosition().getRow();

            float x_pos =
                    overlayRenderer.getX() +
                            0.25f * overlayRenderer.getWidth();
            float y_pos =
                    overlayRenderer.getY() +
                            (0.8f * overlayRenderer.getHeight());

            healthTexture.setPosition(x_pos, y_pos);

            float xy_ratio =
                    healthTexture.getWidth() / healthTexture.getHeight();
            float x_size =
                    overlayRenderer.getWidth() * xy_ratio * scaleDown * 0.2f;
            float y_size =
                    overlayRenderer.getWidth() * scaleDown * 0.2f;

            healthTexture.setSize(x_size, y_size);

            healthTexture.draw(batch);
        }

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
     * @param bossHealth HealthTracker instance for which the appropriate
     *                     texture is being assigned
     * @return texture id for interacting with the TextureManager
     */
    private String assignHealthTexture(HealthTracker bossHealth) {
        StringBuilder textureId = new StringBuilder("bossHealth-");

        // Append Boss Type
        String bossVar =  ((Dragon) boss).getVariation();

        if (bossVar.equals(EnemyIndex.Variation.SWAMP
                .name().toLowerCase())){
            textureId.append("swamp");
        } else if (bossVar.equals(EnemyIndex.Variation.DESERT
                .name().toLowerCase())) {
            textureId.append("desert");
        } else if (bossVar.equals(EnemyIndex.Variation.VOLCANO
                .name().toLowerCase())) {
            textureId.append("volcano");
        } else if (bossVar.equals(EnemyIndex.Variation.TUNDRA
                .name().toLowerCase())) {
            textureId.append("tundra");
        }

        // Get most recent Health Value
        int health = bossHealth.getCurrentHealthValue();
        int maxHealth = bossHealth.getMaxHealthValue();
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
     */
    public void assignBossHealth() {

        try {
            bossHealth =
                    GameManager.get().getManager(EnemyManager.class).getBoss().getHealthTracker();
            boss = (Dragon)GameManager.get()
                    .getManager(EnemyManager.class)
                    .getBoss();
            bossExists = true;
            setScale();
        } catch (NullPointerException err) {
            bossHealth = new HealthTracker(100);
            boss = new DesertDragon(200, 0.03f, 4);
            bossExists = false;
        }
    }

    public void setScale(){

        String bossVar =  ((Dragon) boss).getVariation();

        if (bossVar.equals(EnemyIndex.Variation.SWAMP
                .name().toLowerCase())){
            this.scaleDown = 1f;
        } else if (bossVar.equals(EnemyIndex.Variation.DESERT
                .name().toLowerCase())) {
            this.scaleDown = 1f;
        } else if (bossVar.equals(EnemyIndex.Variation.VOLCANO
                .name().toLowerCase())) {
            this.scaleDown = 0.5f;
        } else if (bossVar.equals(EnemyIndex.Variation.TUNDRA
                .name().toLowerCase())) {
            this.scaleDown = 0.5f;
        }
    }

}

