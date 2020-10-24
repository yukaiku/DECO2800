package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import deco2800.thomas.worlds.dungeons.TundraDungeon;

public class TundraDungeonTextDialog extends TundraDungeonDialog {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private static final int POS_X = Gdx.graphics.getWidth() / 2;
	private static final int POS_Y = Gdx.graphics.getHeight() / 2;
	private static final int TEXTFIELD_LENGTH = 150;

	private TundraDungeon tundraDungeon;
	private TextField textField;

	/**
	 * Lock mechanism to ensure that instances of the class do not show when not allowed
	 * (in the onTick() method of the dungeon class)
	 */
	private static boolean lock;

	/**
	 * Create a TundraDungeonTextDialog
	 * @param tundraDungeon the tundra dungeon
	 * @param title title of the dialog
	 * @param skin skin of the dialog
	 */
	public TundraDungeonTextDialog(TundraDungeon tundraDungeon, String title, Skin skin) {
		super(title, skin);
		this.tundraDungeon = tundraDungeon;
		textField = new TextField("", getSkin());
		text("Type your answer here: ");
		Table contentTable = getContentTable();
		contentTable.add(textField).width(TEXTFIELD_LENGTH);
		button("Guess");
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

	public TundraDungeonDialog show() {
		stage.setKeyboardFocus(textField);
		return this.show(WIDTH, HEIGHT, POS_X, POS_Y);
	}

	@Override
	protected void result(Object object) {
		String textContent = textField.getText();
		tundraDungeon.setPlayerAnswer(textContent);
	}
}
