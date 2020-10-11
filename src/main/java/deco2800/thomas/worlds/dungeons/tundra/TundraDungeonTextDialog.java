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

	TundraDungeon tundraDungeon;
	TextField textField;

	private static boolean lock;

	public TundraDungeonTextDialog(TundraDungeon tundraDungeon, String title, Skin skin) {
		super(title, skin);
		this.tundraDungeon = tundraDungeon;
		textField = new TextField("", getSkin());
		text("Type your answer here: ");
		Table contentTable = getContentTable();
		contentTable.add(textField).width(TEXTFIELD_LENGTH);
		button("Guess");
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
