package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.WorldUtil;
import org.lwjgl.Sys;

public class QuestTrackerRenderer implements Renderer {

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        //get quest progress
        int orbCount = PlayerPeon.questTracker();
        batch.begin();
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture("orb");
        Sprite sprite = new Sprite(img);
        batch.draw(sprite,  camera.position.x + camera.viewportWidth / 2 - sprite.getWidth(),  camera.position.y + camera.viewportHeight / 2 - sprite.getHeight());
        batch.end();
        System.out.println(camera.position.x);
    }
}
