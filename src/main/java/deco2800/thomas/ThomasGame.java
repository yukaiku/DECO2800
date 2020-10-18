package deco2800.thomas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.thomas.mainmenu.MainMenuScreen;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.screens.GameScreen;

/**
 * The game wrapper into which different screens are plugged.
 */
public class ThomasGame extends Game {
	/**
	 * The SpriteBatch for the game.
	 */
	private SpriteBatch batch;
	public static final String SAVE_ROOT_DIR = "thomas-saves";
	private FileHandle saveRootHandle;
	private MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;

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
		saveRootHandle = Gdx.files.local(SAVE_ROOT_DIR);
		batch = new SpriteBatch();
		initUISkin();
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	public void setMainMenuScreen() {
		this.setScreen(mainMenuScreen);
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.setScreen(gameScreen);
	}

	/**
	 * Disposes of the game.
	 */
	public void dispose() {
		mainMenuScreen.dispose();
		batch.dispose();
	}

	public void initUISkin() {
		GameManager.get().setSkin(new Skin(Gdx.files.internal("resources/uiskin.skin")));
	}
}
