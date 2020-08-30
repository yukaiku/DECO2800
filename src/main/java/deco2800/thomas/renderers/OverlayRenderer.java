package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.NetworkManager;
import deco2800.thomas.managers.OnScreenMessageManager;
import deco2800.thomas.managers.PathFindingService;
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
		}

//		System.out.println("Cam pos x: " + camera.position.x);
//		System.out.println("Cam pos y: " + camera.position.y);
//		System.out.println("Cam pos z: " + camera.position.z);
//		System.out.println("Cam vpwidth: " + camera.viewportWidth);
//		System.out.println("Cam vpheight: " + camera.viewportHeight);

		// Dark screen overlay
//		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//		shapeRenderer.setColor(new Color(0, 0, 0, 0.8f));
//		shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
//
//		shapeRenderer.end();
//		Gdx.gl.glDisable(GL20.GL_BLEND);

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
		debugLine(batch, camera, line++, "== Game Info ==");
		debugLine(batch, camera, line++,
				String.format("Rendered: %d/%d entities, %d/%d tiles", GameManager.get().entitiesRendered,
						GameManager.get().entitiesCount, GameManager.get().tilesRendered,
						GameManager.get().tilesCount));
		debugLine(batch, camera, line++, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
		debugLine(batch, camera, line++,
				String.format("RAM: %dMB PEAK: %dMB", Gdx.app.getJavaHeap() / 1000000, peakRAM / 1000000));

		float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
		debugLine(batch, camera, line++, String.format("Mouse: X:%d Y:%d", Gdx.input.getX(), Gdx.input.getY()));
		debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", mouse[0], mouse[1]));

		float[] ColRow = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
		debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", ColRow[0], ColRow[1]));

		line++;

		debugLine(batch, camera, line++, "PathfindingService");
		debugLine(batch, camera, line++, GameManager.get().getManager(PathFindingService.class).toString());

		line++;
		debugLine(batch, camera, line++, "== Networking ==");
		debugLine(batch, camera, line++,
				String.format("ID: %d", GameManager.get().getManager(NetworkManager.class).getID()));
		debugLine(batch, camera, line++, String.format("Messages Received: %d",
				GameManager.get().getManager(NetworkManager.class).getMessagesReceived()));
		debugLine(batch, camera, line++,
				String.format("Messages Sent: %d", GameManager.get().getManager(NetworkManager.class).getMessagesSent()));
		debugLine(batch, camera, line++,
				String.format("Username: %s", GameManager.get().getManager(NetworkManager.class).getUsername()));
	}
}
