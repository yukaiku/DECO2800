package deco2800.thomas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class CharactersScreen implements Screen {
    static float width = 1920;
    static float height = 1000;
    final ThomasGame game;
    private Stage stage;
    private Button fireButton;
    private Button earthButton;

    /**
     * Constructor of the MainMenuScreen.
     *
     * @param game the game to run
     */
    public CharactersScreen(ThomasGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(1920, 1000), game.batch);

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("fire|earth"));
        background.setFillParent(true);
        stage.addActor(background);

        fireButton = new TextButton("SELECT", GameManager.get().getSkin(), "fire");
        fireButton.setPosition(width/4 - fireButton.getWidth()/2, 0);
        fireButton.addAction(Actions.moveTo(width/4 - fireButton.getWidth()/2, 30, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(fireButton);
        fireButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchGameScreen(GameScreen.gameType.NEW_GAME);
            }
        });

        earthButton = new TextButton("SELECT", GameManager.get().getSkin(), "earth");
        earthButton.setPosition(3*width/4 - earthButton.getWidth()/2, 0);
        earthButton.addAction(Actions.moveTo(3*width/4 - earthButton.getWidth()/2, 30, 0.6f,
                Interpolation.PowOut.pow4Out));
        stage.addActor(earthButton);
        earthButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchGameScreen(GameScreen.gameType.NEW_GAME);
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

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
    }

    /**
     * Resizes the main menu stage to a new width and height.
     *
     * @param width  the new width for the menu stage
     * @param height the new width for the menu stage
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
     * Disposes of the stage that the menu is on.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
