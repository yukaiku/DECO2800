package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
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
        batch.begin();
        int percentage = (int) Math.round(((float) boss.getCurrentHealth() / boss.getMaxHealth()) * 100.0);
        Sprite sprite = cachedHealthSprites.get(String.format("%s%d", this.bossType, percentage - (percentage % 5)));
        sprite.setPosition(overlayRenderer.getX() + 0.3f * overlayRenderer.getWidth(),
                overlayRenderer.getY() + 0.85f * overlayRenderer.getHeight());
        float ratio = sprite.getWidth() / sprite.getHeight();
        sprite.setSize(scaleFactor * 0.15f * ratio * overlayRenderer.getWidth(),
                scaleFactor * 0.15f * overlayRenderer.getWidth());
        sprite.draw(batch);

        font.getData().setScale(1f);
        font.draw(batch, String.format("%s", boss.getObjectName()),
                overlayRenderer.getX() + 550,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 24);
        font.getData().setScale(0.4f);
        font.draw(batch, String.format("%d/%d", boss.getCurrentHealth(), boss.getMaxHealth()),
                overlayRenderer.getX() + 620,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 70);
        batch.end();
    }
}
