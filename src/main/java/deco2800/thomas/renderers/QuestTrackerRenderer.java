package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.*;

public class QuestTrackerRenderer implements Renderer {

    BitmapFont font;
    float orbWidth;

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // get quest progress
        int orbCount = PlayerPeon.questTracker();
        //get the width of the orb to adjust the quest tracker text
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture("orb");
        Sprite sprite = new Sprite(img);
        orbWidth = sprite.getWidth();

        batch.begin();
        for (int i = 0; i < orbCount; i++) {
            /*
            * To be edited to allow different orb images if needed
            * */
            batch.draw(sprite,  camera.position.x + camera.viewportWidth / 2 - sprite.getWidth() * (3-i+1),
                    camera.position.y + camera.viewportHeight / 2 - sprite.getHeight());

        }
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(2f);
        }
        font.draw(batch, "orbs: ", camera.position.x + camera.viewportWidth/2 - 7 - orbWidth * 5,
                camera.position.y + camera.viewportHeight / 2 - orbWidth/4);
        batch.end();

    }
}