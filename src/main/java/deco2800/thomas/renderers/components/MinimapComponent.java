package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class MinimapComponent extends OverlayComponent {
    BitmapFont font;
    List<Tile> tileMap;
    List<EnemyPeon> enemyList;
    List<EnemyPeon> specialEnemyList;

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
            renderTile(batch, t, height, width);
            width++;
        }

        if (GameManager.getManagerFromInstance(EnemyManager.class) != null) {
            enemyList = GameManager.getManagerFromInstance(EnemyManager.class).getWildEnemiesAlive();
            specialEnemyList = GameManager.getManagerFromInstance(EnemyManager.class).getSpecialEnemiesAlive();

            for (EnemyPeon e : enemyList) {
                renderEnemy(batch, e);
            }
            for (EnemyPeon e : specialEnemyList) {
                renderEnemy(batch, e);
            }

            Boss boss = GameManager.getManagerFromInstance(EnemyManager.class).getBoss();
            if (boss != null) {
                renderBoss(batch, boss);
            }
        }

        PlayerPeon playerPeon = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (playerPeon != null) {
            renderPlayer(batch, playerPeon);
        }

        batch.end();
    }

    private void renderEnemy(SpriteBatch batch, EnemyPeon enemy) {
        Texture tex = GameManager.getManagerFromInstance(TextureManager.class).getTexture(enemy.getTexture());
        batch.draw(tex, overlayRenderer.getX() + overlayRenderer.getWidth() - 310 + 6.3f * (enemy.getCol() + 25),
                overlayRenderer.getY() + overlayRenderer.getHeight() - 775 +  6.3f * (enemy.getRow() - 24), tex.getWidth() * 0.075f,
                tex.getHeight() * 0.075f);
    }

    private void renderTile(SpriteBatch batch, Tile tile, int height, int width) {
        Texture tex = tile.getTexture();
        batch.draw(tex, overlayRenderer.getX() + overlayRenderer.getWidth() - 310 + 6.3f * width,
                overlayRenderer.getY() + overlayRenderer.getHeight() - 775 - 6.3f * height, tex.getWidth() * 0.02f,
                tex.getHeight() * 0.02f);
    }

    private void renderPlayer(SpriteBatch batch, PlayerPeon player) {
        Texture tex = GameManager.getManagerFromInstance(TextureManager.class).getTexture(player.getTexture());
        batch.draw(tex, overlayRenderer.getX() + overlayRenderer.getWidth() - 310 + 6.3f * (player.getCol() + 25),
                overlayRenderer.getY() + overlayRenderer.getHeight() - 775 +  6.3f * (player.getRow() - 24), tex.getWidth() * 0.1f,
                tex.getHeight() * 0.1f);
    }
    private void renderBoss(SpriteBatch batch, EnemyPeon boss) {
        Texture tex = GameManager.getManagerFromInstance(TextureManager.class).getTexture(boss.getTexture());
        batch.draw(tex, overlayRenderer.getX() + overlayRenderer.getWidth() - 310 + 6.3f * (boss.getCol() + 25),
                overlayRenderer.getY() + overlayRenderer.getHeight() - 775 +  6.3f * (boss.getRow() - 24), tex.getWidth() * 0.035f,
                tex.getHeight() * 0.035f);
    }
}
