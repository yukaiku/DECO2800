package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.Arrays;
import java.util.HashMap;

public class BossHealthComponent extends OverlayComponent {
    private boolean render = false;

    // Tracked boss
    private Boss boss = null;
    private String bossType = "";

    // Cached health bar images
    private final HashMap<String, Sprite> cachedHealthSprites = new HashMap<>();
    private float scaleFactor;
    private final BitmapFont font;
    private float blinkAlpha = 0f;
    private boolean blinkUp = true;

    // Render constants
    private final static float BOSS_NAME_LEFT_ANCHOR = 0.43f;
    private final static float BOSS_NAME_TOP_MARGIN = 24;
    private final static float HEALTH_VALUE_LEFT_ANCHOR = 0.48f;
    private final static float HEALTH_VALUE_TOP_MARGIN = 70;
    private final static float HEALTH_BAR_LEFT_ANCHOR = 0.3f;
    private final static float HEALTH_BAR_TOP_ANCHOR = 0.85f;
    private final static float HEALTH_BAR_BASE_SCALE = 0.15f;

    public BossHealthComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
        TextureManager textureManager = GameManager.get().getManager(TextureManager.class);
        for (String type : Arrays.asList("swamp", "tundra", "desert", "volcano")) {
            for (int i = 0; i <= 100; i += 5) {
                cachedHealthSprites.put(String.format("%s%d", type, i),
                        new Sprite(textureManager.getTexture(String.format("bossHealth-%s%d", type, i))));
            }
        }
        Texture textTex = new Texture(Gdx.files.internal("resources/fonts/title.png"), true);
        textTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("resources/fonts/title.fnt"),
                new TextureRegion(textTex), false);
    }

    public boolean setRender(boolean render) {
        this.render = render && this.boss != null;
        return this.boss != null;
    }

    public void onBossStart(Boss boss) {
        this.boss = boss;
        this.bossType = ((Dragon) boss).getVariationString();
        this.render = true;
        switch (this.bossType) {
            case "swamp":
            case "desert":
                this.scaleFactor = 1f;
                break;
            case "volcano":
            case "tundra":
                this.scaleFactor = 0.5f;
                break;
        }
    }

    public void onBossDefeat() {
        this.render = false;
        this.boss = null;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!this.render) return;
        float overlayRendererX = overlayRenderer.getX();
        float overlayRendererY = overlayRenderer.getY();
        float overlayRendererWidth = overlayRenderer.getWidth();
        float overlayRendererHeight = overlayRenderer.getHeight();
        int percentage = (int) Math.round(((float) boss.getCurrentHealth() / boss.getMaxHealth()) * 100.0);

        batch.begin();
        float alpha = 1f;

        // Make component semi-transparent when player moves to the top.
        AbstractWorld world = GameManager.get().getWorld();
        PlayerPeon player = (PlayerPeon) world.getPlayerEntity();
        if (player.getRow() > (float) world.getHeight() - 3) {
            alpha = 0.4f;
        }

        // boss health bar
        Sprite sprite = cachedHealthSprites.get(String.format("%s%d", this.bossType, percentage - (percentage % 5)));
        sprite.setPosition(overlayRendererX + HEALTH_BAR_LEFT_ANCHOR * overlayRendererWidth,
                overlayRendererY + HEALTH_BAR_TOP_ANCHOR * overlayRendererHeight);
        float ratio = sprite.getWidth() / sprite.getHeight();
        sprite.setSize(scaleFactor * HEALTH_BAR_BASE_SCALE * ratio * overlayRendererWidth,
                scaleFactor * HEALTH_BAR_BASE_SCALE * overlayRendererWidth);
        sprite.setAlpha(alpha);
        sprite.draw(batch);

        // boss name and drop shadow
        font.setColor(0, 0, 0, alpha);
        font.getData().setScale(1f);
        font.draw(batch, String.format("%s", boss.getObjectName()), overlayRendererX + BOSS_NAME_LEFT_ANCHOR *
                overlayRendererWidth + 1, overlayRendererY + overlayRendererHeight - BOSS_NAME_TOP_MARGIN - 1);
        font.setColor(255, 255, 255, alpha);
        font.draw(batch, String.format("%s", boss.getObjectName()), overlayRendererX + BOSS_NAME_LEFT_ANCHOR *
                overlayRendererWidth, overlayRendererY + overlayRendererHeight - BOSS_NAME_TOP_MARGIN);

        // boss health value and drop shadow
        font.getData().setScale(0.4f);
        font.setColor(0, 0, 0, alpha);
        font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                overlayRendererX + HEALTH_VALUE_LEFT_ANCHOR * overlayRendererWidth + 1,
                overlayRendererY + overlayRendererHeight - HEALTH_VALUE_TOP_MARGIN - 1);
        font.setColor(255, 255, 255, alpha);
        font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                overlayRendererX + HEALTH_VALUE_LEFT_ANCHOR * overlayRendererWidth,
                overlayRendererY + overlayRendererHeight - HEALTH_VALUE_TOP_MARGIN);

        // blinking effect overlay
        if (percentage <= 20) {
            float overlayBlinkAlpha;
            if (blinkUp) {
                blinkAlpha += 0.04f;
                if (blinkAlpha >= 1.3f) blinkUp = false;
                overlayBlinkAlpha = Math.min(alpha, blinkAlpha);
            } else {
                blinkAlpha -= 0.04f;
                if (blinkAlpha <= -0.3f) blinkUp = true;
                overlayBlinkAlpha = Math.max(0f, Math.min(alpha, blinkAlpha));
            }
            font.setColor(255, 0, 0, overlayBlinkAlpha);
            font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                    overlayRendererX + HEALTH_VALUE_LEFT_ANCHOR * overlayRendererWidth,
                    overlayRendererY + overlayRendererHeight - HEALTH_VALUE_TOP_MARGIN);
        }
        batch.setColor(255, 255, 255, 1f);
        batch.end();
    }
}
