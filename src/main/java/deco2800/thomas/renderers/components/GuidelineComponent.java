package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

public class GuidelineComponent extends OverlayComponent {
    private Sprite guideline;
    ShapeRenderer shapeRenderer;

    public GuidelineComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
        guideline = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("control"));
    }

    @Override
    public void render(SpriteBatch batch) {
        if (GameManager.get().getTutorial()) {
            if (shapeRenderer == null) {
                shapeRenderer = new ShapeRenderer();
            }

            // Render the black background
            Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
            shapeRenderer.rect(0, 0, overlayRenderer.getWidth(), overlayRenderer.getHeight());

            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            // Render the guideline
            batch.begin();
            batch.draw(guideline, overlayRenderer.getX() + overlayRenderer.getWidth() / 2 - guideline.getWidth() / 2,
                    overlayRenderer.getY() + overlayRenderer.getHeight() / 2 - guideline.getHeight() / 2);
            batch.end();
        }
    }
}
