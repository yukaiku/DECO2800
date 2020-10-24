package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TundraDungeonHintDialog extends TundraDungeonDialog {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private static final int POS_X = Gdx.graphics.getWidth() / 2;
	private static final int POS_Y = Gdx.graphics.getHeight() / 2;

	/**
	 * Lock mechanism to ensure that instances of the class do not show when not allowed
	 * (in the onTick() method of the dungeon class)
	 */
	private static boolean lock = false;

	public TundraDungeonHintDialog(String title, Skin skin, char hiddenLetter) {
		super(title, skin);
		text("Letter found: " + hiddenLetter);
		button("OK");
	}

	/**
	 * Check if an instance of the class is allowed to show
	 * @return true if all instances of the the class is locked from showing
	 */
	public static boolean isLocked() {
		return lock;
	}

	/**
	 * Lock all instances of the class from showing
	 */
	public static void lock() {
		lock = true;
	}

	/**
	 * Release the lock - allowing an instance of the class to show
	 */
	public static void release() {
		lock = false;
	}

	public void show() {
		show(WIDTH, HEIGHT, POS_X, POS_Y);
	}
}
