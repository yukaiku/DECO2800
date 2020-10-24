package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.OnScreenMessageManager;
import deco2800.thomas.managers.PathFindingService;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.util.WorldUtil;

public class DebugComponent extends OverlayComponent {
    BitmapFont font;
    ShapeRenderer shapeRenderer;

    FPSLogger fpsLogger = new FPSLogger();

    long peakRAM = 0;

    public DebugComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(1f);
//			font.setColor(255, 255, 255, 0.9f);
        }

        // Debug Info and Chat Message Overlay
        batch.begin();

        if (GameManager.get().getDebugMode()) {
            renderDebugText(batch);
        }

        int line = GameManager.get().getManager(OnScreenMessageManager.class).getMessages().size();
        for (String message : GameManager.get().getManager(OnScreenMessageManager.class).getMessages()) {
            chatLine(batch, line--, message);
        }

        if (GameManager.get().getManager(OnScreenMessageManager.class).isTyping()) {
            chatLine(batch, 0, GameManager.get().getManager(OnScreenMessageManager.class).getUnsentMessage());
        }

        if (peakRAM < Gdx.app.getJavaHeap()) {
            peakRAM = Gdx.app.getJavaHeap();
        }

        batch.end();
    }

    private void debugLine(SpriteBatch batch, int line, String string) {
        font.draw(batch, string, overlayRenderer.getX() + 10,
                overlayRenderer.getY() + overlayRenderer.getHeight() - line * 20 - 10);
    }

    private void chatLine(SpriteBatch batch, int line, String string) {
        font.draw(batch, string, overlayRenderer.getX() + 10,
                overlayRenderer.getY() + line * 25 + 25);
    }

    private void renderDebugText(SpriteBatch batch) {
        int line = 0; // Set this to set the line number you want to debug message to
        debugLine(batch, line, "== Game Info ==");
        debugLine(batch, ++line,
                String.format("Rendered: %d/%d entities, %d/%d tiles", GameManager.get().getEntitiesRendered(),
                        GameManager.get().getEntitiesCount(), GameManager.get().getTilesRendered(),
                        GameManager.get().getTilesCount()));
        debugLine(batch, ++line, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
        debugLine(batch, ++line,
                String.format("RAM: %dMB PEAK: %dMB", Gdx.app.getJavaHeap() / 1000000, peakRAM / 1000000));

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        debugLine(batch, ++line, String.format("Mouse: X:%d Y:%d", Gdx.input.getX(), Gdx.input.getY()));
        debugLine(batch, ++line, String.format("World: X:%.0f Y:%.0f", mouse[0], mouse[1]));

        float[] colRow = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
        debugLine(batch, ++line, String.format("World: X:%.0f Y:%.0f", colRow[0], colRow[1]));

        line++;
        debugLine(batch, ++line, "PathfindingService");
        debugLine(batch, ++line, GameManager.get().getManager(PathFindingService.class).toString());

        line++;
//		debugLine(batch, ++line, "== Networking ==");
//		debugLine(batch, ++line,
//				String.format("ID: %d", GameManager.get().getManager(NetworkManager.class).getID()));
//		debugLine(batch, ++line, String.format("Messages Received: %d",
//				GameManager.get().getManager(NetworkManager.class).getMessagesReceived()));
//		debugLine(batch, ++line,
//				String.format("Messages Sent: %d", GameManager.get().getManager(NetworkManager.class).getMessagesSent()));
//		debugLine(batch, ++line,
//				String.format("Username: %s", GameManager.get().getManager(NetworkManager.class).getUsername()));
        line++;

        // PlayerEntity
        if (GameManager.get().getWorld().getPlayerEntity() != null) {
            debugLine(batch, ++line, "== Health ==");
            debugLine(batch, ++line, String.format("Health: %d",
                    ((Peon)GameManager.get().getWorld().getPlayerEntity()).getCurrentHealth()));
            line++;

            debugLine(batch, ++line, "== Speed ==");
            debugLine(batch, ++line, String.format("Speed: %f", GameManager.get().getWorld().getPlayerEntity().getSpeed()));
            line++;
        }

        EnemyManager enemyManager = GameManager.get().getManager(EnemyManager.class);

        if (enemyManager != null) {
            debugLine(batch, ++line, "== Enemies ==");
            debugLine(batch, ++line, String.format("Wild Spawning: %s",
                    enemyManager.checkWildEnemySpawning() ? "active" : "disabled"));
            debugLine(batch, ++line, String.format("Current Enemies: %d", enemyManager.getEnemyCount()));
            if (enemyManager.getEnemyCount() - (enemyManager.getBoss() == null ? 0 : enemyManager.getBoss().isDead() ? 0 : 1) > 0) {
                debugLine(batch, ++line, String.format("(%s%s%s)",
                        enemyManager.getWildEnemiesAlive().size() > 0 ? String.format("%d/%d wild",
                                enemyManager.getWildEnemiesAlive().size(), enemyManager.getWildEnemyCap()) : "",
                        enemyManager.getWildEnemiesAlive().size() > 0 && enemyManager.getSpecialEnemiesAlive().size() > 0 ? ", " : "",
                        enemyManager.getSpecialEnemiesAlive().size() > 0 ? String.format("%d special",
                                enemyManager.getSpecialEnemiesAlive().size()) : ""));
                Boss boss = enemyManager.getBoss();
                debugLine(batch, ++line, String.format("Boss: %s%s", boss == null ? "n/a" : boss.getObjectName(),
                        boss == null ? "" : String.format(" (%d/%d)", boss.getCurrentHealth(), boss.getMaxHealth())));
            }
        }
    }
}
