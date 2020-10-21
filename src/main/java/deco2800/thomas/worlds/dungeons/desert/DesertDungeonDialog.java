package deco2800.thomas.worlds.dungeons.desert;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Abstract class to enable a dialog box in the Desert Dungeon.
 */
public abstract class DesertDungeonDialog extends Dialog {

    // the stage for the dialog box
    protected static Stage stage;

    // the skin for the dialog box
    protected static Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

    /**
     * Creates a new DesertDungeonDialog with a specified title.
     *
     * @param title The specified title
     */
    public DesertDungeonDialog(String title) {
        super(title, skin);
    }

    /**
     * Sets up the DesertDungeonDialog with a specified stage.
     *
     * @param stage The specified stage
     */
    public static void setup(Stage stage) {
        DesertDungeonDialog.stage = stage;
    }

    @Override
    protected void result(Object object) {

    }

    /**
     * Shows the dialog box with specified size and position.
     *
     * @param sizeX The x-size of the dialog box
     * @param sizeY The y-size of the dialog box
     * @param posX The x-position of the dialog box
     * @param posY The y-position of the dialog box
     * @return A DesertDungeonDialog with the specified dimensions and position.
     */
    public DesertDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
        super.show(stage);
        setSize(sizeX, sizeY);
        setX(posX);
        setY(posY);
        return this;
    }
}
