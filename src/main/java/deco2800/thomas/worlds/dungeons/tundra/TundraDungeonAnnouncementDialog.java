package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TundraDungeonAnnouncementDialog extends TundraDungeonDialog {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private static final int POS_X = Gdx.graphics.getWidth() / 2;
	private static final int POS_Y = Gdx.graphics.getHeight() / 2;

	public TundraDungeonAnnouncementDialog(String title, Skin skin) {
		super(title, skin);
		text("Your answer is wrong!");
		button("OK");
	}

	public TundraDungeonDialog show() {
		return this.show(WIDTH, HEIGHT, POS_X, POS_Y);
	}

	@Override
	public TundraDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
		return super.show(sizeX, sizeY, posX, posY);
	}
}
