package deco2800.thomas.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.screens.CharactersScreen;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.screens.SettingScreen;

public class MainMenuScreen implements Screen {
	final ThomasGame game;
	private final Stage stage;
	private boolean soundPlayed = false;

	/**
	 * Constructor of the MainMenuScreen.
	 *
	 * @param game the game to run
	 */
	public MainMenuScreen(final ThomasGame game) {
		this.game = game;
		stage = new Stage(new ExtendViewport(1920, 1000), game.getBatch());

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
		small.setFontScale(0.8f);
		logo.setFontScale(3.4f);
		logo.setPosition(200, 770);
		small.setPosition(268, 700);
		logo.addAction(Actions.moveTo(300, 770, 0.8f, Interpolation.PowOut.pow3Out));
		small.addAction(Actions.moveTo(368, 700, 0.8f, Interpolation.PowOut.pow3Out));
		stage.addActor(logo);
		stage.addActor(small);

		Texture menuTex = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
		menuTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
		BitmapFont menuFont = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
				new TextureRegion(menuTex), false);

//		Button envTeamButton = new TextButton("ENV TEAM", createButtonStyle(menuFont));
//		envTeamButton.setPosition(200, 430);
//		envTeamButton.addAction(Actions.moveTo(300, 430, 1.2f, Interpolation.PowOut.pow3Out));
//		addButtonListener(envTeamButton);
//		stage.addActor(envTeamButton);
//
//		Button testWorldBtn = new TextButton("DEBUG START", createButtonStyle(menuFont));
//		testWorldBtn.setPosition(200, 380);
//		testWorldBtn.addAction(Actions.moveTo(300, 380, 1.2f, Interpolation.PowOut.pow3Out));
//		addButtonListener(testWorldBtn);
//		stage.addActor(testWorldBtn);

		Button newGameBtn = new TextButton("NEW GAME", createButtonStyle(menuFont));
		newGameBtn.setTransform(true);
		newGameBtn.setPosition(200, 480);
		newGameBtn.setScale(1.4f);
		newGameBtn.addAction(Actions.moveTo(300, 480, 1.2f, Interpolation.PowOut.pow3Out));
		addButtonListener(newGameBtn);
		stage.addActor(newGameBtn);

		Button settingBtn = new TextButton("SETTING", createButtonStyle(menuFont));
		settingBtn.setTransform(true);
		settingBtn.setPosition(200, 410);
		settingBtn.setScale(1.4f);
		settingBtn.addAction(Actions.moveTo(300, 410, 1.2f, Interpolation.PowOut.pow3Out));
		addButtonListener(settingBtn);
		stage.addActor(settingBtn);

		Button exitBtn = new TextButton("QUIT", createButtonStyle(menuFont));
		exitBtn.setTransform(true);
		exitBtn.setPosition(200, 340);
		exitBtn.setScale(1.4f);
		exitBtn.addAction(Actions.moveTo(300, 340, 1.2f, Interpolation.PowOut.pow3Out));
		addButtonListener(exitBtn);
		stage.addActor(exitBtn);

//		envTeamButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				switchScreen(GameScreen.gameType.ENV_TEAM_GAME);
//				GameManager.getManagerFromInstance(SoundManager.class).playSound("button2");
//			}
//		});
//
//		testWorldBtn.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				switchScreen(GameScreen.gameType.TEST_WORLD);
//				GameManager.getManagerFromInstance(SoundManager.class).playSound("button2");
//			}
//		});

		newGameBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switchScreen(GameScreen.gameType.NEW_GAME);
				GameManager.getManagerFromInstance(SoundManager.class).playSound("button2");
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
//				System.exit(0);
			}
		});
	}

	public TextButton.TextButtonStyle createButtonStyle(BitmapFont menuFont) {
		TextButton.TextButtonStyle menuStyle = new TextButton.TextButtonStyle();
		menuStyle.font = menuFont;
		menuStyle.fontColor = Color.valueOf("#cccccc");
		return menuStyle;
	}

	public void addButtonListener(Button button) {
		button.addListener(new InputListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				((TextButton.TextButtonStyle)(button.getStyle())).fontColor = Color.valueOf("#ffff00");
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
				if (!soundPlayed) {
					GameManager.getManagerFromInstance(SoundManager.class).playSound("button1");
					soundPlayed = true;
				}
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				((TextButton.TextButtonStyle)(button.getStyle())).fontColor = Color.valueOf("#cccccc");
				soundPlayed = false;
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
	 * @param delta the render interval
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