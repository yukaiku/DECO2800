package deco2800.thomas.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.thomas.screens.CharactersScreen;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.screens.SettingScreen;

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

		stage = new Stage(new ExtendViewport(1920, 1000), game.getBatch());
		skin = GameManager.get().getSkin();

		Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
		background.setFillParent(true);
		stage.addActor(background);

		Texture titleTex = new Texture(Gdx.files.internal("resources/fonts/title.png"), true);
		titleTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
		BitmapFont titleFont = new BitmapFont(Gdx.files.internal("resources/fonts/title.fnt"), new TextureRegion(titleTex),
				false);
		Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
		Label logo = new Label("Polyhedron", titleStyle);
		Label small = new Label("Studio Fd ii", titleStyle);
		small.setFontScale(0.6f);
		logo.setFontScale(2.2f);
		logo.setPosition(200, 540);
		small.setPosition(250, 495);
		logo.addAction(Actions.moveTo(300, 540, 0.4f, Interpolation.PowOut.pow3Out));
		small.addAction(Actions.moveTo(350, 495, 0.4f, Interpolation.PowOut.pow3Out));
		stage.addActor(logo);
		stage.addActor(small);

		Texture menuTex = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
		menuTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
		BitmapFont menuFont = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
				new TextureRegion(menuTex), false);
		TextButton.TextButtonStyle menuStyle = new TextButton.TextButtonStyle();
		menuStyle.font = menuFont;
		menuStyle.fontColor = Color.valueOf("#cccccc");

		Button envTeamButton = new TextButton("ENV TEAM", menuStyle);
		envTeamButton.setPosition(200, 330);
		envTeamButton.addAction(Actions.moveTo(300, 330, 1f, Interpolation.PowOut.pow3Out));
		stage.addActor(envTeamButton);

		Button testWorldBtn = new TextButton("DEBUG START", menuStyle);
		testWorldBtn.setPosition(200, 280);
		testWorldBtn.addAction(Actions.moveTo(300, 280, 1f, Interpolation.PowOut.pow3Out));
		stage.addActor(testWorldBtn);

		Button newGameBtn = new TextButton("NEW GAME", menuStyle);
		newGameBtn.setPosition(200, 210);
		newGameBtn.addAction(Actions.moveTo(300, 210, 1f, Interpolation.PowOut.pow3Out));
		stage.addActor(newGameBtn);

		Button settingBtn = new TextButton("SETTING", menuStyle);
		settingBtn.setPosition(200, 160);
		settingBtn.addAction(Actions.moveTo(300, 160, 1f, Interpolation.PowOut.pow3Out));
		stage.addActor(settingBtn);

		Button exitBtn = new TextButton("QUIT", menuStyle);
		exitBtn.setPosition(200, 110);
		exitBtn.addAction(Actions.moveTo(300, 110, 1f, Interpolation.PowOut.pow3Out));
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

		settingBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SettingScreen(game));
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
		if (gameType == GameScreen.gameType.NEW_GAME) {
			sequenceAction.addAction(Actions.run(() -> game.setScreen(new CharactersScreen(game))));
		}
		else {
			sequenceAction.addAction(Actions.run(() -> game.setScreen(new GameScreen(game, gameType))));
		}
		stage.getRoot().addAction(sequenceAction);
	}

	/**
	 * Begins things that need to begin when shown.
	 */
	public void show() {
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.8f));
		Gdx.input.setInputProcessor(stage);
		GameManager.getManagerFromInstance(SoundManager.class).playAmbience("menuAmbience");
		GameManager.getManagerFromInstance(SoundManager.class).stopMusic();
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