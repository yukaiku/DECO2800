package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;

public class CurrencyRenderer implements Renderer {

    BitmapFont font;

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // get quest progress
        float wallet = GameManager.get().getWorld().getPlayerEntity().getWallet();

        batch.begin();
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
        font.draw(batch, String.format("Bank: $%.2f", wallet), camera.position.x - (camera.viewportWidth / 2) + 25,
                camera.position.y - (camera.viewportHeight / 2) + 50);
        batch.end();

    }
}