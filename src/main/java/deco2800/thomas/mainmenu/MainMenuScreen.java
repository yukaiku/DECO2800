package deco2800.thomas.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.thomas.GameScreen;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class MainMenuScreen implements Screen {
	final ThomasGame game;
	private Stage stage;
	private Skin skin;

	/**
	 * Constructor of the MainMenuScreen.
	 *
	 * @param game the game to run
	 */
	public MainMenuScreen(final ThomasGame game) {
		this.game = game;

		stage = new Stage(new ExtendViewport(1280, 720), game.batch);
		skin = GameManager.get().getSkin();

		Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
		background.setFillParent(true);
		stage.addActor(background);

		Label logo = new Label("Polyhedron", skin);
		logo.setFontScale(5.0f);
//		logo.setPosition(1280 / 2 - 225, 720 / 2 + 100);
		logo.setPosition(180, 540);
		logo.addAction(Actions.moveTo(280, 540, 0.4f, Interpolation.PowOut.pow3Out));
		stage.addActor(logo);

		Button envTeamButton = new TextButton("ENV TEAM", skin, "main_menu");
		envTeamButton.setPosition(200, 330);
		envTeamButton.addAction(Actions.moveTo(300, 330, 0.6f, Interpolation.PowOut.pow3Out));
		stage.addActor(envTeamButton);

		Button testWorldBtn = new TextButton("DEBUG START", skin, "main_menu");
		testWorldBtn.setPosition(200, 280);
		testWorldBtn.addAction(Actions.moveTo(300, 280, 0.8f, Interpolation.PowOut.pow3Out));
		stage.addActor(testWorldBtn);

		Button newGameBtn = new TextButton("NEW GAME", skin, "main_menu");
		newGameBtn.setPosition(200, 210);
		newGameBtn.addAction(Actions.moveTo(300, 210, 1f, Interpolation.PowOut.pow3Out));
		stage.addActor(newGameBtn);

		Button exitBtn = new TextButton("EXIT", skin, "main_menu");
		exitBtn.setPosition(200, 160);
		exitBtn.addAction(Actions.moveTo(300, 160, 1.2f, Interpolation.PowOut.pow3Out));
		stage.addActor(exitBtn);

		envTeamButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switchScreen(GameScreen.gameType.ENV_TEAM_GAME);
			}
		});

		testWorldBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switchScreen(GameScreen.gameType.TEST_WORLD);
			}
		});

		newGameBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switchScreen(GameScreen.gameType.NEW_GAME);
			}
		});

		exitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}

	/**
	 * Add transition when switching screens
	 */
	public void switchScreen(GameScreen.gameType gameType) {
		stage.getRoot().getColor().a = 1;
		SequenceAction sequenceAction = new SequenceAction();
		sequenceAction.addAction(Actions.fadeOut(0.3f));
		sequenceAction.addAction(Actions.run(() -> game.setScreen(new GameScreen(game, gameType))));
		stage.getRoot().addAction(sequenceAction);
	}

	/**
	 * Begins things that need to begin when shown.
	 */
	public void show() {
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.8f));
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Pauses the screen.
	 */
	public void pause() {
		//do nothing
	}

	/**
	 * Resumes the screen.
	 */
	public void resume() {
		//do nothing
	}

	/**
	 * Hides the screen.
	 */
	public void hide() {
		//do nothing
	}

	/**
	 * Resizes the main menu stage to a new width and height.
	 *
	 * @param width  the new width for the menu stage
	 * @param height the new width for the menu stage
	 */
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	/**
	 * Renders the menu.
	 *
	 * @param delta
	 */
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	/**
	 * Disposes of the stage that the menu is on.
	 */
	public void dispose() {
		stage.dispose();
	}
}