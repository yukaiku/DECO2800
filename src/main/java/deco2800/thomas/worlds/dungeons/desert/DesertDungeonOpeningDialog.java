package deco2800.thomas.worlds.dungeons.desert;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DesertDungeonOpeningDialog extends DesertDungeonDialog {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;
    private static final int POS_X = Gdx.graphics.getWidth() / 2;
    private static final int POS_Y = Gdx.graphics.getHeight() / 2;

    public DesertDungeonOpeningDialog(String title, Skin skin) {
        super(title, skin);
        text("This Orc has grown super strong and is immune to your attacks.\n Find another way to damage him!");
        button("OK");
    }

    public DesertDungeonDialog show() {
        return this.show(WIDTH, HEIGHT, POS_X, POS_Y);
    }

    @Override
    public DesertDungeonDialog show(int sizeX, int sizeY, int posX, int posY) {
        return super.show(sizeX, sizeY, posX, posY);
    }
}
