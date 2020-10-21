package deco2800.thomas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.thomas.mainmenu.MainMenuScreen;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.screens.LoadingScreen;

import java.util.List;

/**
 * The game wrapper into which different screens are plugged.
 */
public class ThomasGame extends Game {
	/**
	 * The SpriteBatch for the game.
	 */
	private SpriteBatch batch;
	//public static final String SAVE_ROOT_DIR = "thomas-saves";
	//private FileHandle saveRootHandle;
	private MainMenuScreen mainMenuScreen;
	private LoadingScreen loadingScreen;

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	/**
	 * Creates the main menu screen.
	 */
	public void create() {
		//saveRootHandle = Gdx.files.local(SAVE_ROOT_DIR);
		batch = new SpriteBatch();
		initUISkin();
		this.loadingScreen = new LoadingScreen(this);
		this.setScreen(loadingScreen);
		loadingScreen.setLoadingText("Loading game resources");
	}

	// load the main menu screen
	public void loadMainMenuScreen() {
		this.mainMenuScreen = new MainMenuScreen(this);
	}

	// set current screen to main menu screen
	public void setMainMenuScreen() {
		this.setScreen(mainMenuScreen);
	}

	// set current screen to game screen
	public void setGameScreen(GameScreen gameScreen) {
		this.setScreen(gameScreen);
	}

	/**
	 * Disposes of the game.
	 */
	public void dispose() {
		mainMenuScreen.dispose();
		loadingScreen.dispose();
		batch.dispose();
	}

	public void initUISkin() {
		GameManager.get().setSkin(new Skin(Gdx.files.internal("resources/uiskin.skin")));
	}
}
