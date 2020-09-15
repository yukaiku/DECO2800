package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.thomas.entities.enemies.Boss;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.WorldUtil;

public class OverlayRenderer implements Renderer {

	BitmapFont font;
	ShapeRenderer shapeRenderer;

	FPSLogger fpsLogger = new FPSLogger();

	long peakRAM = 0;

	/**
	 * Renders onto a batch, given a renderables with entities.
	 * It is expected that AbstractWorld contains some entities and a Map to read tiles from.
	 *
	 * @param batch Batch to render onto
	 */
	@Override
	public void render(SpriteBatch batch, OrthographicCamera camera) {
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

		if (GameManager.get().debugMode) {
			renderDebugText(batch, camera);
		}

		int line = GameManager.get().getManager(OnScreenMessageManager.class).getMessages().size();
		for (String message : GameManager.get().getManager(OnScreenMessageManager.class).getMessages()) {
			chatLine(batch, camera, line--, message);
		}

		if (GameManager.get().getManager(OnScreenMessageManager.class).isTyping()) {
			chatLine(batch, camera, 0, GameManager.get().getManager(OnScreenMessageManager.class).getUnsentMessage());
		}

		if (peakRAM < Gdx.app.getJavaHeap()) {
			peakRAM = Gdx.app.getJavaHeap();
		}

		batch.end();
	}

	private void debugLine(SpriteBatch batch, Camera camera, int line, String string) {
		font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
				camera.position.y + camera.viewportHeight / 2 - line * 20 - 10);
	}

	private void chatLine(SpriteBatch batch, Camera camera, int line, String string) {
		font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
				camera.position.y - camera.viewportHeight / 2 + line * 25 + 25);
	}

	private void renderDebugText(SpriteBatch batch, Camera camera) {
		int line = 0; // Set this to set the line number you want to debug message to
		debugLine(batch, camera, line, "== Game Info ==");
		debugLine(batch, camera, ++line,
				String.format("Rendered: %d/%d entities, %d/%d tiles", GameManager.get().entitiesRendered,
						GameManager.get().entitiesCount, GameManager.get().tilesRendered,
						GameManager.get().tilesCount));
		debugLine(batch, camera, ++line, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
		debugLine(batch, camera, ++line,
				String.format("RAM: %dMB PEAK: %dMB", Gdx.app.getJavaHeap() / 1000000, peakRAM / 1000000));

		float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
		debugLine(batch, camera, ++line, String.format("Mouse: X:%d Y:%d", Gdx.input.getX(), Gdx.input.getY()));
		debugLine(batch, camera, ++line, String.format("World: X:%.0f Y:%.0f", mouse[0], mouse[1]));

		float[] ColRow = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
		debugLine(batch, camera, ++line, String.format("World: X:%.0f Y:%.0f", ColRow[0], ColRow[1]));

		line++;
		debugLine(batch, camera, ++line, "PathfindingService");
		debugLine(batch, camera, ++line, GameManager.get().getManager(PathFindingService.class).toString());

		line++;
		debugLine(batch, camera, ++line, "== Networking ==");
		debugLine(batch, camera, ++line,
				String.format("ID: %d", GameManager.get().getManager(NetworkManager.class).getID()));
		debugLine(batch, camera, ++line, String.format("Messages Received: %d",
				GameManager.get().getManager(NetworkManager.class).getMessagesReceived()));
		debugLine(batch, camera, ++line,
				String.format("Messages Sent: %d", GameManager.get().getManager(NetworkManager.class).getMessagesSent()));
		debugLine(batch, camera, ++line,
				String.format("Username: %s", GameManager.get().getManager(NetworkManager.class).getUsername()));
		line++;
		debugLine(batch, camera, ++line, "== Health ==");
		debugLine(batch, camera, ++line, String.format("Health: %d", GameManager.get().getWorld().getPlayerEntity().getCurrentHealth()));
		line++;
		debugLine(batch, camera, ++line, "== Speed ==");
		debugLine(batch, camera, ++line, String.format("Speed: %f", GameManager.get().getWorld().getPlayerEntity().getSpeed()));
		line++;
		EnemyManager enemyManager = GameManager.get().getManager(EnemyManager.class);
		debugLine(batch, camera, ++line, "== Enemies ==");
		debugLine(batch, camera, ++line, String.format("World: %s", enemyManager.getWorldName()));
		debugLine(batch, camera, ++line, String.format("Wild Spawning: %s",
				enemyManager.checkWildEnemySpawning() ? "active" : "disabled"));
		debugLine(batch, camera, ++line, String.format("Current Enemies: %d", enemyManager.getEnemyCount()));
		if (enemyManager.getEnemyCount() - (enemyManager.getBoss() == null ? 0 : enemyManager.getBoss().isDead() ? 0 : 1) > 0) {
			debugLine(batch, camera, ++line, String.format("(%s%s%s)",
					enemyManager.getWildEnemiesAlive().size() > 0 ? String.format("%d/%d wild",
							enemyManager.getWildEnemiesAlive().size(), enemyManager.getWildEnemyCap()) : "",
					enemyManager.getWildEnemiesAlive().size() > 0 && enemyManager.getSpecialEnemiesAlive().size() > 0 ? ", " : "",
					enemyManager.getSpecialEnemiesAlive().size() > 0 ? String.format("%d special",
							enemyManager.getSpecialEnemiesAlive().size()) : ""));
		}
		Boss boss = enemyManager.getBoss();
		debugLine(batch, camera, ++line, String.format("Boss: %s%s", boss == null ? "n/a" : boss.getObjectName(),
				boss == null ? "" : String.format(" (%d/%d)", boss.getCurrentHealth(), boss.getMaxHealth())));
	}
}
