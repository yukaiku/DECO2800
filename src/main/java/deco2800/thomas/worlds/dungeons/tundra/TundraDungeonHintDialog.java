package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TundraDungeonHintDialog extends TundraDungeonDialog {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private static final int POS_X = Gdx.graphics.getWidth() / 2;
	private static final int POS_Y = Gdx.graphics.getHeight() / 2;

	private static boolean lock = false;

	public TundraDungeonHintDialog(String title, Skin skin, char hiddenLetter) {
		super(title, skin);
		text("Letter found: " + hiddenLetter);
		button("OK");
	}

	public static boolean isLocked() {
		return lock;
	}

	public static void lock() {
		lock = true;
	}

	public static void release() {
		lock = false;
	}

	public void show() {
		show(WIDTH, HEIGHT, POS_X, POS_Y);
	}
}
