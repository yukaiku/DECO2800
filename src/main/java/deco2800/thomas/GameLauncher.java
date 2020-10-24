package deco2800.thomas;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * DesktopLauncher
 *
 * @author Tim Hadwen
 */
     public class GameLauncher {
	/**
	 * Private constructor to hide the implicit constructor
	 */
	private GameLauncher() {
	}

	/**
	 * Main function for the game.
	 *
	 * @param arg Command line arguments (we won't use these)
	 */
	@SuppressWarnings("unused")
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1000;
		config.fullscreen = true;
		config.resizable = false;
		config.forceExit = true;
		config.title = "DECO2800 2020: Polyhedron";
		LwjglApplication app = new LwjglApplication(new ThomasGame(), config);
	}
}