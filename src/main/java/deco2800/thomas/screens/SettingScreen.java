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
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.managers.TextureManager;

public class SettingScreen implements Screen {
    final ThomasGame game;
    private final Stage stage;
    private static final String OS_NAME = "os.name";
    static float width = 1920;
    static float height = 1000;
    float screenWidth;
    float screenHeight;

    Texture fontTexture = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
    BitmapFont font = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
            new TextureRegion(fontTexture), false);

    //spriteBatch for renderers
    SpriteBatch spriteBatch = new SpriteBatch();

    //Buttons
    Button windowButton;
    Button fullScreenButton;
    Button offVolumeButton;
    Button lowVolumeButton;
    Button medVolumeButton;
    Button highVolumeButton;

    /**
     * Constructor of the MainMenuScreen.
     *
     * @param game the game to run
     */
    public SettingScreen(final ThomasGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(1920, 1000), game.getBatch());
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

        // Initialize buttons
        if (!System.getProperty(OS_NAME).toLowerCase().contains("mac")) {
            windowButton = new TextButton("WINDOW", buttonStyle);
            windowButton.setPosition(width / 5 + 400, height / 2 - 100);
            stage.addActor(windowButton);

            fullScreenButton = new TextButton("FULLSCREEN", buttonStyle);
            fullScreenButton.setPosition(width / 5 + 800, height / 2 - 100);
            stage.addActor(fullScreenButton);
        }

        Button backButton = new TextButton("BACK", buttonStyle);
        backButton.setPosition(width/2 - backButton.getWidth()/2, 90);
        stage.addActor(backButton);

        offVolumeButton = new TextButton("OFF", buttonStyle);
        offVolumeButton.setPosition(width/5 + 400, height/2 + 175);
        stage.addActor(offVolumeButton);

        lowVolumeButton = new TextButton("LOW", buttonStyle);
        lowVolumeButton.setPosition(width/5 + 600, height/2 + 175);
        stage.addActor(lowVolumeButton);

        medVolumeButton = new TextButton("MEDIUM", buttonStyle);
        medVolumeButton.setPosition(width/5 + 800, height/2 + 175);
        stage.addActor(medVolumeButton);

        highVolumeButton = new TextButton("HIGH", buttonStyle);
        highVolumeButton.setPosition(width/5 + 1030, height/2 + 175);
        stage.addActor(highVolumeButton);
        if (!System.getProperty(OS_NAME).toLowerCase().contains("mac")) {
            if (Gdx.graphics.isFullscreen()) {
                fullScreenButton.setChecked(true);
            } else {
                windowButton.setChecked(true);
            }
        }

        if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0f) {
            offVolumeButton.setChecked(true);
        } else if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0.3f) {
            lowVolumeButton.setChecked(true);
        } else if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0.6f) {
            medVolumeButton.setChecked(true);
        }else if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 1.0f) {
            highVolumeButton.setChecked(true);
        }

        // Add click listener to buttons
        if (!System.getProperty(OS_NAME).toLowerCase().contains("mac")) {
            windowButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setWindowedScreen();
                }
            });

            fullScreenButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setFullScreen();
                }
            });
        }
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainMenuScreen();
            }
        });

        offVolumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVolume(offVolumeButton);
            }
        });

        lowVolumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVolume(lowVolumeButton);
            }
        });

        medVolumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVolume(medVolumeButton);
            }
        });

        highVolumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVolume(highVolumeButton);
            }
        });
    }

    /**
     * Set the audio volume
     */
    private void setVolume(Button button) {
        if (button == offVolumeButton) {
            lowVolumeButton.setChecked(false);
            medVolumeButton.setChecked(false);
            highVolumeButton.setChecked(false);
            if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0f) {
                offVolumeButton.setChecked(true);
            }
            GameManager.getManagerFromInstance(SoundManager.class).setVolume(0f);
        } else if (button == lowVolumeButton) {
            offVolumeButton.setChecked(false);
            medVolumeButton.setChecked(false);
            highVolumeButton.setChecked(false);
            if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0.3f) {
                lowVolumeButton.setChecked(true);
            }
            GameManager.getManagerFromInstance(SoundManager.class).setVolume(0.3f);
        } else if (button == medVolumeButton) {
            offVolumeButton.setChecked(false);
            lowVolumeButton.setChecked(false);
            highVolumeButton.setChecked(false);
            if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 0.6f) {
                medVolumeButton.setChecked(true);
            }
            GameManager.getManagerFromInstance(SoundManager.class).setVolume(0.6f);
        } else if (button == highVolumeButton) {
            offVolumeButton.setChecked(false);
            lowVolumeButton.setChecked(false);
            medVolumeButton.setChecked(false);
            if (GameManager.getManagerFromInstance(SoundManager.class).getVolume() == 1.0f) {
                highVolumeButton.setChecked(true);
            }
            GameManager.getManagerFromInstance(SoundManager.class).setVolume(1.0f);
        }
    }

    /**
     * Set the game to windowed mode
     */
    private void setWindowedScreen() {
        if (!System.getProperty(OS_NAME).toLowerCase().contains("mac")) {
            Gdx.graphics.setWindowedMode( (int)(0.8*Gdx.graphics.getDisplayMode().width),
                    (int)(0.8*Gdx.graphics.getDisplayMode().height));
        }
        fullScreenButton.setChecked(false);
        if (!Gdx.graphics.isFullscreen()) {
            windowButton.setChecked(true);
        }
    }

    /**
     * Set the game to full-screen mode
     */
    private void setFullScreen() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        windowButton.setChecked(false);
        if (Gdx.graphics.isFullscreen()) {
            fullScreenButton.setChecked(true);
        }
    }

    /**
     * Begins things that need to begin when shown.
     */
    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.8f));
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the screen.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        spriteBatch.begin();
        font.getData().setScale(1.8f);
        if (!System.getProperty(OS_NAME).toLowerCase().contains("mac")) {
            font.draw(spriteBatch, "SCREEN", screenWidth / 5, screenHeight / 2 - 100);
        }
        font.draw(spriteBatch, "AUDIO", screenWidth/5, screenHeight/2 + 100);
        spriteBatch.end();
    }

    /**
     * Resizes the stage to a new width and height.
     *
     * @param width  the new width for the stage
     * @param height the new width for the stage
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Pauses the screen.
     */
    @Override
    public void pause() {
        //do nothing
    }

    /**
     * Resumes the screen.
     */
    @Override
    public void resume() {
        //do nothing
    }

    /**
     * Hides the screen.
     */
    @Override
    public void hide() {
        //do nothing
    }

    /**
     * Disposes of the stage that the screen is on.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}

