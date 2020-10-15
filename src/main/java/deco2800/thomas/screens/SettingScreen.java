package deco2800.thomas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class SettingScreen implements Screen {
    final ThomasGame game;
    private final Stage stage;
    static float width = 1920;
    static float height = 1000;
    static float screenWidth;
    static float screenHeight;

    Texture fontTexture = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
    BitmapFont font = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
            new TextureRegion(fontTexture), false);

    //spriteBatch for renderers
    SpriteBatch spriteBatch = new SpriteBatch();

    /**
     * Constructor of the MainMenuScreen.
     *
     * @param game the game to run
     */
    public SettingScreen(final ThomasGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(1920, 1000), game.batch);
        screenWidth = stage.getViewport().getScreenWidth();
        screenHeight = stage.getViewport().getScreenHeight();

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
        background.setFillParent(true);
        stage.addActor(background);

        fontTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.valueOf("#ffffff");
        buttonStyle.checkedFontColor = Color.valueOf("#ff1100");

        TextButton.TextButtonStyle clickedButtonStyle = new TextButton.TextButtonStyle();
        clickedButtonStyle.font = font;
        clickedButtonStyle.fontColor = Color.valueOf("#ff1100");

        Button windowButton = new TextButton("WINDOW", buttonStyle);
        windowButton.setPosition(width/5 + 400, height/2 + 100);
        stage.addActor(windowButton);

        Button fullScreenButton = new TextButton("FULLSCREEN", buttonStyle);
        fullScreenButton.setPosition(width/5 + 800, height/2 + 100);
        stage.addActor(fullScreenButton);

        Button backButton = new TextButton("BACK", buttonStyle);
        backButton.setPosition(width/2 - backButton.getWidth()/2, 90);
        stage.addActor(backButton);

        if(Gdx.graphics.isFullscreen()) {
            fullScreenButton.setChecked(true);
        } else {
            windowButton.setChecked(true);
        }

        windowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode( (int)(0.8*Gdx.graphics.getDisplayMode().width),
                        (int)(0.8*Gdx.graphics.getDisplayMode().height));
                fullScreenButton.setChecked(false);
                if (!Gdx.graphics.isFullscreen()) {
                    windowButton.setChecked(true);
                }
            }
        });

        fullScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                windowButton.setChecked(false);
                if (Gdx.graphics.isFullscreen()) {
                    fullScreenButton.setChecked(true);
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainMenuScreen();
            }
        });
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.8f));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        spriteBatch.begin();
        font.getData().setScale(1.8f);
        font.draw(spriteBatch, "SCREEN", screenWidth/5, screenHeight/2 + 100);
        font.draw(spriteBatch, "AUDIO", screenWidth/5, screenHeight/2 - 100);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
