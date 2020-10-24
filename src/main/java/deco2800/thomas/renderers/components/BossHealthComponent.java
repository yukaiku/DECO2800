package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

import java.util.Arrays;
import java.util.HashMap;

public class BossHealthComponent extends OverlayComponent {
    private boolean render = false;
    // the tracked boss
    private Boss boss = null;
    private String bossType = "";
    // Cached health bar images
    private final HashMap<String, Sprite> cachedHealthSprites = new HashMap<>();
    private float scaleFactor;
    private final BitmapFont font;
    private float blinkAlpha = 0f;
    private boolean blinkUp = true;

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

    public void onBossStart(Boss boss) {
        this.boss = boss;
        this.bossType = ((Dragon) boss).getVariation();
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

        // boss health bar
        Sprite sprite = cachedHealthSprites.get(String.format("%s%d", this.bossType, percentage - (percentage % 5)));
        sprite.setPosition(overlayRendererX + 0.3f * overlayRendererWidth,
                overlayRendererY + 0.85f * overlayRendererHeight);
        float ratio = sprite.getWidth() / sprite.getHeight();
        sprite.setSize(scaleFactor * 0.15f * ratio * overlayRendererWidth,
                scaleFactor * 0.15f * overlayRendererWidth);
        sprite.draw(batch);

        // boss name and drop shadow
        font.setColor(Color.valueOf("#000000"));
        font.getData().setScale(1f);
        font.draw(batch, String.format("%s", boss.getObjectName()), overlayRendererX + 0.43f *
                overlayRendererWidth + 1, overlayRendererY + overlayRendererHeight - 24 - 1);
        font.setColor(Color.valueOf("#ffffff"));
        font.draw(batch, String.format("%s", boss.getObjectName()), overlayRendererX + 0.43f * overlayRendererWidth,
                overlayRendererY + overlayRendererHeight - 24);

        // boss health value and drop shadow
        font.getData().setScale(0.4f);
        font.setColor(Color.valueOf("#000000"));
        font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                overlayRendererX + 0.48f * overlayRendererWidth + 1,
                overlayRendererY + overlayRendererHeight - 70 - 1);
        font.setColor(Color.valueOf("#ffffff"));
        font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                overlayRendererX + 0.48f * overlayRendererWidth,
                overlayRendererY + overlayRendererHeight - 70);

        // blinking effect overlay
        if (percentage <= 20) {
            float alpha;
            if (blinkUp) {
                blinkAlpha += 0.04f;
                if (blinkAlpha >= 1.3f) blinkUp = false;
                alpha = Math.min(1f, blinkAlpha);
            } else {
                blinkAlpha -= 0.04f;
                if (blinkAlpha <= -0.3f) blinkUp = true;
                alpha = Math.max(0f, blinkAlpha);
            }
            font.setColor(255f, 0, 0, alpha);
            font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                    overlayRendererX + 0.48f * overlayRendererWidth,
                    overlayRendererY + overlayRendererHeight - 70);
        }
        batch.end();
    }
}
