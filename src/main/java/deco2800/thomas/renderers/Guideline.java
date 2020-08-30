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
import deco2800.thomas.managers.*;
import deco2800.thomas.util.WorldUtil;

public class Guideline implements Renderer {

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.begin();
        Texture img = GameManager.get().getManager(TextureManager.class).getTexture("dialog-box");
        Sprite sprite = new Sprite(img);
        System.out.println("cam-x:" + camera.position.x + "/ cam-y:" + camera.position.y + "/ cam-w:" + camera.viewportWidth + "/ cam-h:" + camera.viewportHeight);
        batch.draw(sprite, camera.position.x - sprite.getWidth()/2, camera.position.y + camera.viewportHeight / 2 - sprite.getHeight());
        batch.end();
    }
}
