package deco2800.thomas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.combat.Knight;
import deco2800.thomas.combat.Wizard;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.CharacterInfo;
import deco2800.thomas.renderers.EffectRenderer;

public class CharactersScreen implements Screen {
    private static final String WATER = "water";
    private static final String FIRE = "fire";
    static float width = 1920;
    static float height = 1000;
    final ThomasGame game;
    private Stage stage;
    private boolean showSkillsInfo = false;

    EffectRenderer effectRenderer = new EffectRenderer();

    // Character info modal
    CharacterInfo characterInfo = new CharacterInfo();
    // Camera for renderers
    OrthographicCamera cameraOverlay;
    // SpriteBatch for renderers
    SpriteBatch spriteBatch = new SpriteBatch();

    // Background of the screen
    Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("fire-water"));

    // Buttons
    Button fireButton = new TextButton("SELECT", GameManager.get().getSkin(), FIRE);
    Button fireInfoButton = new TextButton("MORE INFO...", GameManager.get().getSkin(), FIRE);
    Button waterButton = new TextButton("SELECT", GameManager.get().getSkin(), WATER);
    Button waterInfoButton = new TextButton("MORE INFO...", GameManager.get().getSkin(), WATER);
    Button backButton = new TextButton("BACK", GameManager.get().getSkin(), "in_game");


    /**
     * Constructor of the CharactersScreen.
     *
     * @param game the game to run
     */
    public CharactersScreen(ThomasGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(width, height), game.getBatch());

        cameraOverlay = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        background.setFillParent(true);
        stage.addActor(background);


        // Setup fireButton
        fireButton.setPosition(width/4 - fireButton.getWidth()/2, 0);
        fireButton.addAction(Actions.moveTo(width/4 - fireButton.getWidth()/2, 50, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(fireButton);
        fireButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.getManagerFromInstance(PlayerManager.class).resetPlayer();
                GameManager.getManagerFromInstance(PlayerManager.class).setWizard(Wizard.FIRE);
                GameManager.getManagerFromInstance(PlayerManager.class).setKnight(Knight.FIRE);
                switchGameScreen(GameScreen.gameType.NEW_GAME);
            }
        });

        // Setup fireInfoButton
        fireInfoButton.setPosition(width/4 - fireInfoButton.getWidth()/2, 0);
        fireInfoButton.addAction(Actions.moveTo(width/4 - fireInfoButton.getWidth()/2, 120, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(fireInfoButton);
        fireInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSkillsInfo = true;
                characterInfo.setTexture("fire-team");
                effectRenderer.setEffect(FIRE);
                effectRenderer.loadParticleFile();
                Skin skin = GameManager.get().getSkin();
                backButton.setStyle(skin.get(FIRE, TextButton.TextButtonStyle.class));
            }
        });

        // Setup waterButton
        waterButton.setPosition(3*width/4 - waterButton.getWidth()/2, 0);
        waterButton.addAction(Actions.moveTo(3*width/4 - waterButton.getWidth()/2, 50, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(waterButton);
        waterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.getManagerFromInstance(PlayerManager.class).resetPlayer();
                GameManager.getManagerFromInstance(PlayerManager.class).setWizard(Wizard.WATER);
                GameManager.getManagerFromInstance(PlayerManager.class).setKnight(Knight.WATER);
                switchGameScreen(GameScreen.gameType.NEW_GAME);
            }
        });

        // Setup waterInfoButton
        waterInfoButton.setPosition(3*width/4 - waterInfoButton.getWidth()/2, 0);
        waterInfoButton.addAction(Actions.moveTo(3*width/4 - waterInfoButton.getWidth()/2, 120, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(waterInfoButton);
        waterInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSkillsInfo = true;
                characterInfo.setTexture("water-team");
                effectRenderer.setEffect(WATER);
                effectRenderer.loadParticleFile();
                Skin skin = GameManager.get().getSkin();
                backButton.setStyle(skin.get(WATER, TextButton.TextButtonStyle.class));
            }
        });

        // Setup backButton
        backButton.setPosition(width/2 - backButton.getWidth()/2, 80);
        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSkillsInfo = false;
            }
        });
    }

    /**
     * Add transition when switching screens
     */
    public void switchGameScreen(GameScreen.gameType gameType) {
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(1f));
        dispose();
        sequenceAction.addAction(Actions.run(() -> game.setScreen(new GameScreen(game, gameType))));
        stage.getRoot().addAction(sequenceAction);
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
     * Render the character info modal
     */
    public void renderSkillsInfo(float delta) {
        // Render the character info modal
        spriteBatch.setProjectionMatrix(cameraOverlay.combined);
        characterInfo.render(spriteBatch, cameraOverlay);
        effectRenderer.render(spriteBatch, cameraOverlay);
        // Hide background and buttons
        background.remove();
        fireButton.remove();
        fireInfoButton.remove();
        waterButton.remove();
        waterInfoButton.remove();
        // Display backButton
        stage.addActor(backButton);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Render the normal screen
     */
    public void renderNormalScreen(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Hide the buttons when the game is running
        backButton.remove();
        // Display background and buttons
        stage.addActor(background);
        stage.addActor(fireButton);
        stage.addActor(fireInfoButton);
        stage.addActor(waterButton);
        stage.addActor(waterInfoButton);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Renders the screen.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        if (showSkillsInfo) {
            renderSkillsInfo(delta);
        } else {
            renderNormalScreen(delta);
        }
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
        // do nothing
    }

    /**
     * Resumes the screen.
     */
    @Override
    public void resume() {
        // do nothing
    }

    /**
     * Hides the screen.
     */
    @Override
    public void hide() {
        // do nothing
    }

    /**
     * Disposes of the stage that the screen is on.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
