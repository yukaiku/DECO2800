package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class MinimapComponent extends OverlayComponent {
    private static final int MAP_WIDTH = 49;
    // The column offsets to render each entity at on the x coordinate
    private static final int COLUMN_OFFSET = 310;
    private static final int ENEMY_COLUMN_OFFSET = 350;
    // The number of positive columns and rows
    private static final int POSITIVE_COLUMN_NUMBER = 25;
    private static final int POSITIVE_ROW_NUMBER = 24;
    // The scalar to render each entity at relative to its position on the map
    private static final float ENTITY_POSITION_SCALAR = 6.3F;
    // Scalars to reduce the size of each entity texture by to fit each
    // texture onto the minimap
    private static final float ENEMY_SIZE_SCALAR = 0.2f;
    private static final float TILE_SIZE_SCALAR = 0.02f;
    private static final float PLAYER_SIZE_SCALAR = 0.1f;
    private static final float BOSS_SIZE_SCALAR = 0.035f;

    public MinimapComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        int width = 0;
        int height = 0;
        String worldType = GameManager.get().getWorld().getType();
        // The minimap is only to be rendered in the four main worlds
        if (!(worldType.equals("Desert") || worldType.equals("Tundra") ||
                worldType.equals("Volcano") || worldType.equals("Swamp"))) {
            return;
        }
        List<Tile> tileMap = GameManager.get().getWorld().getTiles();
        batch.begin();

        // Make minimap semi-transparent when player moves to the top-left corner.
        AbstractWorld world = GameManager.get().getWorld();
        PlayerPeon player = (PlayerPeon) world.getPlayerEntity();
        if (player.getRow() > (float) world.getHeight() - 3 || player.getCol() < -(float) world.getWidth() + 5) {
            batch.setColor(255, 255, 255, 0.4f);
        }

        for (Tile t : tileMap) {
            if (width > MAP_WIDTH) {
                height++;
                width = 0;
            }
            renderTile(batch, t, height, width);
            width++;
        }

        if (GameManager.getManagerFromInstance(EnemyManager.class) != null) {
            List<EnemyPeon> enemyList = GameManager.getManagerFromInstance(EnemyManager.class).getWildEnemiesAlive();
            List<EnemyPeon> specialEnemyList = GameManager.getManagerFromInstance(EnemyManager.class).getSpecialEnemiesAlive();
            for (EnemyPeon e : enemyList) {
                renderEnemy(batch, e);
            }
            for (EnemyPeon e : specialEnemyList) {
                renderEnemy(batch, e);
            }
            Boss boss = GameManager.getManagerFromInstance(EnemyManager.class).getBoss();
            if (boss != null && boss.getCurrentHealth() > 0) {
                renderBoss(batch, boss);
            }
        }

        PlayerPeon playerPeon = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (playerPeon != null) {
            renderPlayer(batch, playerPeon);
        }

        batch.setColor(255, 255, 255, 1f);
        batch.end();
    }

    // Renders an enemy onto the minimap
    private void renderEnemy(SpriteBatch batch, EnemyPeon enemy) {
        Texture tex = enemy.getIcon();
        float x = overlayRenderer.getX() - 30 + ENTITY_POSITION_SCALAR * (enemy.getCol() + POSITIVE_COLUMN_NUMBER);
        float y =  overlayRenderer.getY() + overlayRenderer.getHeight() - 50 + ENTITY_POSITION_SCALAR * (enemy.getRow() - POSITIVE_ROW_NUMBER);

        batch.draw(tex, x, y,
                tex.getWidth() * ENEMY_SIZE_SCALAR,
                tex.getHeight() * ENEMY_SIZE_SCALAR);
    }

    // Renders a tile onto the minimap
    private void renderTile(SpriteBatch batch, Tile tile, int height, int width) {
        Texture tex = tile.getTexture();
        float x = overlayRenderer.getX() + ENTITY_POSITION_SCALAR * width;
        float y =  overlayRenderer.getY() - 20 + overlayRenderer.getHeight() - ENTITY_POSITION_SCALAR * height;
        batch.draw(tex, x, y, tex.getWidth() * TILE_SIZE_SCALAR, tex.getHeight() * TILE_SIZE_SCALAR);
    }

    // Renders the player onto the minimap
    private void renderPlayer(SpriteBatch batch, PlayerPeon player) {
        Texture tex = GameManager.getManagerFromInstance(TextureManager.class).getTexture(player.getTexture());
        float x = overlayRenderer.getX() + ENTITY_POSITION_SCALAR * (player.getCol() + POSITIVE_COLUMN_NUMBER);
        float y = overlayRenderer.getY() + overlayRenderer.getHeight() - 30 + ENTITY_POSITION_SCALAR * (player.getRow() - POSITIVE_ROW_NUMBER);

        batch.draw(tex, x, y, tex.getWidth() * PLAYER_SIZE_SCALAR,
                tex.getHeight() * PLAYER_SIZE_SCALAR);
    }

    private void renderBoss(SpriteBatch batch, EnemyPeon boss) {
        Texture tex = boss.getIcon();
        float x = overlayRenderer.getX() + ENTITY_POSITION_SCALAR * (boss.getCol() + POSITIVE_COLUMN_NUMBER);
        float y = overlayRenderer.getY() + overlayRenderer.getHeight() - 20 + ENTITY_POSITION_SCALAR * (boss.getRow() - POSITIVE_ROW_NUMBER);
        batch.draw(tex, x, y,
                tex.getWidth() * BOSS_SIZE_SCALAR,
                tex.getHeight() * BOSS_SIZE_SCALAR);
    }
}
