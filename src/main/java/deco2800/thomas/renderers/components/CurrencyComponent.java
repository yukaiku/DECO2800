package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

public class CurrencyComponent extends OverlayComponent {

    BitmapFont font;

    public  CurrencyComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        // get quest progress
        int wallet = PlayerPeon.checkBalance();

        batch.begin();
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
        font.draw(batch, String.format("Wallet: %d gp", wallet),overlayRenderer.getX() + overlayRenderer.getWidth() - 300,
                        overlayRenderer.getY() + overlayRenderer.getHeight() - 60);
        batch.end();
    }
}