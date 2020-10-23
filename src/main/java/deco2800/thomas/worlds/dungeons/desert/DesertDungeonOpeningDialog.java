package deco2800.thomas.worlds.dungeons.desert;

import com.badlogic.gdx.Gdx;

/**
 * A dialog box which is shown upon entering the Desert Dungeon. This dialog
 * gives a hint on how to kill the immune enemy.
 */
public class DesertDungeonOpeningDialog extends DesertDungeonDialog {

    // the width and height of the dialog box
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    // the x and y positions of the dialog box
    private static final int POS_X = Gdx.graphics.getWidth() / 2;
    private static final int POS_Y = Gdx.graphics.getHeight() / 2;

    /**
     * Creates a new DesertDungeonOpeningDialog with specified title and skin.
     *
     * @param title The specified title
     * @param skin The specified skin
     */
    public DesertDungeonOpeningDialog(String title) {
        super(title);
        text("This Orc has grown super strong and is immune to your attacks.\n Find another way to damage him!");
        button("OK");
    }

    /**
     * Shows the dialog box with default dimensions and position.
     *
     * @return A new DesertDungeonDialog to be shown.
     */
    public DesertDungeonDialog show() {
        return this.show(WIDTH, HEIGHT, POS_X, POS_Y);
    }

    /**
     * Shows the dialog box with specified dimensions and position.
     *
     * @param sizeX The x-size of the dialog box
     * @param sizeY The y-size of the dialog box
     * @param posX The x-position of the dialog box
     * @param posY The y-position of the dialog box
     * @return A new DesertDungeonDialog to be shown.
     */
    @Override
    public DesertDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
        return super.show(sizeX, sizeY, posX, posY);
    }
}
