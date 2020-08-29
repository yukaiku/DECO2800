package deco2800.thomas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.handlers.KeyboardManager;
import deco2800.thomas.managers.*;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.renderers.PotateCamera;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.renderers.Renderer3D;
import deco2800.thomas.worlds.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScreen implements Screen, KeyDownObserver {
	private final Logger LOG = LoggerFactory.getLogger(GameScreen.class);
	@SuppressWarnings("unused")
	private final ThomasGame game;
	/**
	 * Set the renderer.
	 * 3D is for Isometric worlds
	 * Check the documentation for each renderer to see how it handles WorldEntity coordinates
	 */
	Renderer3D renderer = new Renderer3D();
	OverlayRenderer rendererDebug = new OverlayRenderer();
	AbstractWorld world;
	static Skin skin;
	//Quest Tracker Label
	Label questTracker = new Label("Orbs: 0/4", GameManager.get().getSkin());

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */
	PotateCamera camera, cameraDebug;

	public Stage stage = new Stage(new ExtendViewport(1280, 720));

	long lastGameTick = 0;


	static public enum gameType {
		LOAD_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new LoadGameWorld();
				DatabaseManager.loadWorld(world);
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		},
		CONNECT_TO_SERVER {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new ServerWorld();
				GameManager.get().getManager(NetworkManager.class).connectToHost("localhost", "duck1234");
				return world;
			}
		},
		NEW_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TestWorld();
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		},
		TUTORIAL{
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TutorialWorld();
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		};

		public abstract AbstractWorld method(); // could also be in an interface that MyEnum implements
	}


	public GameScreen(final ThomasGame game, final gameType startType) {
		/* Create an example world for the engine */
		this.game = game;

		GameManager gameManager = GameManager.get();

		world = startType.method();

		gameManager.setWorld(world);

		// Add first peon to the world
		camera = new PotateCamera(1920, 1080);
		cameraDebug = new PotateCamera(1920, 1080);

		//Add Quest tracker to the game UI
		String questTrackerText = "Orbs: " + Integer.toString(PlayerPeon.questTracker()) + "/4";
		questTracker.setText(questTrackerText);
		questTracker.setFontScale(1.0f);
		questTracker.setPosition(stage.getWidth()-questTracker.getWidth(),stage.getHeight()-questTracker.getHeight());
		stage.addActor(questTracker);

		/* Add the window to the stage */
		GameManager.get().setSkin(skin);
		GameManager.get().setStage(stage);
		GameManager.get().setCamera(camera);

		PathFindingService pathFindingService = new PathFindingService();
		GameManager.get().addManager(pathFindingService);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(GameManager.get().getManager(KeyboardManager.class));
		multiplexer.addProcessor(GameManager.get().getManager(InputManager.class));
		Gdx.input.setInputProcessor(multiplexer);

		GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);
	}

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render(float delta) {

		//Update the quest tracker on each render
		String questTrackerText = "Orbs: " + Integer.toString(PlayerPeon.questTracker()) + "/4";
		questTracker.setText(questTrackerText);

		handleRenderables();

		moveCamera();

		cameraDebug.position.set(camera.position);
		cameraDebug.update();
		camera.update();

		SpriteBatch batchDebug = new SpriteBatch();
		batchDebug.setProjectionMatrix(cameraDebug.combined);

		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// Clear the entire display as we are using lazy rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rerenderMapObjects(batch, camera);
		rendererDebug.render(batchDebug, cameraDebug);

		/* Refresh the experience UI for if information was updated */
		stage.act(delta);
		stage.draw();
		batch.dispose();
	}

	private void handleRenderables() {
		if (System.currentTimeMillis() - lastGameTick > 20) {
			lastGameTick = System.currentTimeMillis();
			GameManager.get().onTick(0);
		}
	}

	/**
	 * Use the selected renderer to render objects onto the map
	 */
	private void rerenderMapObjects(SpriteBatch batch, OrthographicCamera camera) {
		renderer.render(batch, camera);
	}

	@Override
	public void show() {

	}

	/**
	 * Resizes the viewport
	 *
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

		cameraDebug.viewportWidth = width;
		cameraDebug.viewportHeight = height;
		cameraDebug.update();
	}

	@Override
	public void pause() {
		//do nothing
	}

	@Override
	public void resume() {
		//do nothing
	}

	@Override
	public void hide() {
		//do nothing
	}

	/**
	 * Disposes of assets etc when the rendering system is stopped.
	 */
	@Override
	public void dispose() {
		// Don't need this at the moment
		System.exit(0);
	}

	@Override
	public void notifyKeyDown(int keycode) {
		PlayerPeon.increaseOrbs();

		if (keycode == Input.Keys.F12) {
			GameManager.get().debugMode = !GameManager.get().debugMode;
		}

		if (keycode == Input.Keys.F5) {
			world = new TestWorld();
			AbstractEntity.resetID();
			Tile.resetID();
			GameManager gameManager = GameManager.get();
			gameManager.setWorld(world);

			// Add first peon to the world
			world.addEntity(new Peon(0f, 0f, 0.05f));
		}

		if (keycode == Input.Keys.F11) { // F11
			GameManager.get().showCoords = !GameManager.get().showCoords;
			LOG.info("Show coords is now {}", GameManager.get().showCoords);
		}


		if (keycode == Input.Keys.C) { // F11
			GameManager.get().showCoords = !GameManager.get().showCoords;
			LOG.info("Show coords is now {}", GameManager.get().showCoords);
		}

		if (keycode == Input.Keys.F10) { // F10
			GameManager.get().showPath = !GameManager.get().showPath;
			LOG.info("Show Path is now {}", GameManager.get().showPath);
		}

		if (keycode == Input.Keys.F3) { // F3
			// Save the world to the DB
			DatabaseManager.saveWorld(null);
		}

		if (keycode == Input.Keys.F4) { // F4
			// Load the world to the DB
			DatabaseManager.loadWorld(null);
		}
	}

	public void moveCamera() {
		//timmeh to fix hack.  // fps is not updated cycle by cycle
		float normilisedGameSpeed = (60.0f / Gdx.graphics.getFramesPerSecond());

		int goFastSpeed = (int) (5 * normilisedGameSpeed * camera.zoom);

		if (!camera.isPotate()) {

			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
				goFastSpeed *= goFastSpeed * goFastSpeed;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				camera.translate(-goFastSpeed, 0, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				camera.translate(goFastSpeed, 0, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				camera.translate(0, -goFastSpeed, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				camera.translate(0, goFastSpeed, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
				camera.zoom *= 1 - 0.01 * normilisedGameSpeed;
				if (camera.zoom < 0.5) {
					camera.zoom = 0.5f;
				}
			}

			if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
				camera.zoom *= 1 + 0.01 * normilisedGameSpeed;
			}
		}

	}
}