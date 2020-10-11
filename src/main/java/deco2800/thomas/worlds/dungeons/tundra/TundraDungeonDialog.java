package deco2800.thomas.worlds.dungeons.tundra;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class TundraDungeonDialog extends Dialog {
	protected static Stage stage;
	protected static Skin skin;

	public TundraDungeonDialog(String title, Skin skin) {
		super(title, skin);
		TundraDungeonDialog.skin = skin;
	}

	public static void setup(Stage stage, Skin skin) {
		TundraDungeonDialog.stage = stage;
		TundraDungeonDialog.skin = skin;
	}

	@Override
	protected void result(Object object) {

	}

	public TundraDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
		super.show(stage);
		setSize(sizeX, sizeY);
		setX(posX);
		setY(posY);
		return this;
	}
}
