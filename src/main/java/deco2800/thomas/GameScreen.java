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
import deco2800.thomas.renderers.*;
import deco2800.thomas.util.CameraUtil;
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
	public static boolean tutorial = false;
	Renderer3D renderer = new Renderer3D();
	OverlayRenderer rendererDebug = new OverlayRenderer();
	AbstractWorld world;
	static Skin skin;

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */
	OrthographicCamera camera, cameraDebug;

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
		if (startType == gameType.TUTORIAL) {
			GameManager.get().inTutorial = true;
			tutorial = true;
		}
		/* Create an example world for the engine */
		this.game = game;

		GameManager gameManager = GameManager.get();

		world = startType.method();

		gameManager.setWorld(world);

		// Initialize camera
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraDebug = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

		handleRenderables();
		
		CameraUtil.zoomableCamera(camera, Input.Keys.MINUS, Input.Keys.EQUALS, delta);
		CameraUtil.lockCameraOnTarget(camera, GameManager.get().getWorld().getPlayerEntity());

		cameraDebug.position.set(camera.position);
		cameraDebug.update();

		SpriteBatch batchDebug = new SpriteBatch();
		batchDebug.setProjectionMatrix(cameraDebug.combined);

		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// Clear the entire display as we are using lazy rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rerenderMapObjects(batch, camera);
		rendererDebug.render(batchDebug, cameraDebug);

		// Add guideline if we are in the TutorialWorld
		if (tutorial) {
			SpriteBatch batchGuideline = new SpriteBatch();
			batchGuideline.setProjectionMatrix(cameraDebug.combined);
			Guideline guideline = new Guideline();
			guideline.render(batchGuideline, cameraDebug);
		}
		//Questtracker UI
		SpriteBatch batchGuideline = new SpriteBatch();
		batchGuideline.setProjectionMatrix(cameraDebug.combined);
		QuestTrackerRenderer questTrackerRenderer = new QuestTrackerRenderer();
		questTrackerRenderer.render(batchGuideline, cameraDebug);

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
		if (keycode == Input.Keys.F12) {
			GameManager.get().debugMode = !GameManager.get().debugMode;
		}

		if (keycode == Input.Keys.F9 & GameManager.get().inTutorial) {
			tutorial = !tutorial;
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

}
