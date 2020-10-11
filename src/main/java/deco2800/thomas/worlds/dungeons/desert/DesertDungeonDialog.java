package deco2800.thomas.worlds.dungeons.desert;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class DesertDungeonDialog extends Dialog {
    protected static Stage stage;
    protected static Skin skin;

    public DesertDungeonDialog(String title, Skin skin) {
        super(title, skin);
        DesertDungeonDialog.skin = skin;
    }

    public static void setup(Stage stage, Skin skin) {
        DesertDungeonDialog.stage = stage;
        DesertDungeonDialog.skin = skin;
    }

    @Override
    protected void result(Object object) {

    }

    public DesertDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
        super.show(stage);
        setSize(sizeX, sizeY);
        setX(posX);
        setY(posY);
        return this;
    }
}
