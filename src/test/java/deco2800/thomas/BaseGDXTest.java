package deco2800.thomas;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

/**
 * All unit tests for GUI classes using libGDX features
 * (i.e. all of them in this project) NEED to extend this class.
 * @author Source: http://manabreak.eu/java/2016/10/21/unittesting-libgdx.html
 */
public class BaseGDXTest {
    private static Application game;

    @BeforeClass
    public static void setUpBeforeClass() {
        game = new HeadlessApplication(
        new ApplicationListener() {
                @Override public void create() {}
                @Override public void resize(int width, int height) {}
                @Override public void render() {}
                @Override public void pause() {}
                @Override public void resume() {}
                @Override public void dispose() {}
            });
        // Use Mockito to mock the OpenGL methods
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterClass
    public static void tearDownAfterClass() {
        game.exit();
        game = null;
    }
}
