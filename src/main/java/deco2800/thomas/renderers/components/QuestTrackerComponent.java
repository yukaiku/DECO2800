package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.agent.QuestTracker;
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
    public void render(SpriteBatch batch){
        // get quest progress
        // get quest progress
        List<Orb> orbs = QuestTracker.orbTracker();
        //draws the orb
        Texture img = null;

        batch.begin();
        int i = orbs.size();
        if (i == 0)
            // Blank
            img = GameManager.get().getManager(TextureManager.class).getTexture("orb_0qt");
        else if (i == 1)
            // Swamp orb
            img = GameManager.get().getManager(TextureManager.class).getTexture("orb_1qt");
        else if (i == 2)
            // Tundra orb
            img = GameManager.get().getManager(TextureManager.class).getTexture("orb_2qt");
        else if (i == 3)
            // Desert orb
            img = GameManager.get().getManager(TextureManager.class).getTexture("orb_3qt");
        else if (i == 4) {
            // Volcano
            img = GameManager.get().getManager(TextureManager.class).getTexture("orb_4qt");
        }

        assert img != null;
        Sprite sprite = new Sprite(img);
        batch.draw(sprite, overlayRenderer.getX() + overlayRenderer.getWidth() - 225,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 55, 200, 50);


        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
        font.draw(batch, "Orbs: ", overlayRenderer.getX() + overlayRenderer.getWidth() - 300,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 20);
        batch.end();
    }
}
