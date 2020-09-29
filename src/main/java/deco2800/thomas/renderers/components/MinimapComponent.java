package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class MinimapComponent extends OverlayComponent {
    BitmapFont font;
    List<Tile> tileMap;

    public MinimapComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        int width = 0;
        int height = 0;
        tileMap = GameManager.get().getWorld().getTiles();
        batch.begin();
        for (Tile t : tileMap) {
            if (width > 49) {
                height++;
                width = 0;
            }
            renderTile(batch, tileMap, t, height, width);
            width++;
        }
        batch.end();
    }

    private void renderTile(SpriteBatch batch, List<Tile> tileMap, Tile tile, int height, int width) {
        float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());
        Texture tex = tile.getTexture();
        batch.draw(tex, overlayRenderer.getX() + overlayRenderer.getWidth() - 1950 + 6.3f * width,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 6.3f * height, tex.getWidth() * 0.02f,
                tex.getHeight() * 0.02f);
    }
}
