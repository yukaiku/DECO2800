package deco2800.thomas.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
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
		logo.setPosition(1280 / 2 - 225, 720 / 2 + 100);
		stage.addActor(logo);

		Button envTeamButton = new TextButton("ENV TEAM", skin, "main_menu");
		envTeamButton.setPosition(10, 250);
		stage.addActor(envTeamButton);

		Button newGameBtn = new TextButton("SINGLE PLAYER", skin, "main_menu");
		newGameBtn.setPosition(10, 200);
		stage.addActor(newGameBtn);

		Button testWorldBtn = new TextButton("TEST WORLD", skin, "main_menu");
		testWorldBtn.setPosition(10, 150);
		stage.addActor(testWorldBtn);

		newGameBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, GameScreen.gameType.NEW_GAME));
			}
		});

		envTeamButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, GameScreen.gameType.ENV_TEAM_GAME));
			}
		});

		testWorldBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, GameScreen.gameType.TEST_WORLD));
			}
		});
	}

	/**
	 * Begins things that need to begin when shown.
	 */
	public void show() {
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