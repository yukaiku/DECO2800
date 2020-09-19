package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.Agent.QuestTracker;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

import java.util.List;

public class QuestTrackerComponent extends OverlayComponent {

    BitmapFont font;

    public QuestTrackerComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera){
        // get quest progress
        List<Orb> orbs = QuestTracker.orbTracker();
        //draws the orb
        batch.begin();
        for (int i = 0; i < orbs.size(); i++) {
            Texture img = GameManager.get().getManager(TextureManager.class).getTexture(orbs.get(i).getTexture());
            Sprite sprite = new Sprite(img);
            batch.draw(sprite,  camera.position.x + camera.viewportWidth / 2 - 55 * (3-i+1),  camera.position.y + camera.viewportHeight / 2 - 50, 50,50);

        }
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
        font.draw(batch, "orbs: ", camera.position.x + camera.viewportWidth/2 - 7 - 57 * 5,camera.position.y + camera.viewportHeight / 2 - 50/4);
        batch.end();
    }
}
