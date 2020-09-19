package deco2800.thomas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Agent.QuestTracker;
import deco2800.thomas.handlers.KeyboardManager;
import deco2800.thomas.managers.*;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.renderers.*;
import deco2800.thomas.renderers.OverlayRenderer;
import deco2800.thomas.renderers.Renderer3D;
import deco2800.thomas.util.CameraUtil;
import deco2800.thomas.worlds.*;

import deco2800.thomas.worlds.tundra.TundraWorld;
import deco2800.thomas.worlds.volcano.VolcanoWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScreen implements Screen, KeyDownObserver {
	static float width = 1280;
	static float height = 1000;
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
	OverlayRenderer overlayRenderer;

	//Renderer Object to render Zone Events
	EventRenderer rendererEvent;

	//spriteBatch for renderers
	SpriteBatch spriteBatch = new SpriteBatch();
	//Quest Tracker UI
	PauseModal pauseModal = new PauseModal();
	Result result = new Result();
	TransitionScreen transitionScreen = new TransitionScreen();
	QuestTrackerRenderer questTrackerRenderer = new QuestTrackerRenderer();
	//Tutorial Guideline UI
	Guideline guideline = new Guideline();


	// Buttons in the pause menu
	Button resumeButton = new TextButton("RESUME", GameManager.get().getSkin(), "in_game");
	Button quitButton = new TextButton("RETURN TO MAIN MENU", GameManager.get().getSkin(), "in_game");
	Button enterButton = new TextButton("ENTER THE ZONE", GameManager.get().getSkin(), "in_game");
	AbstractWorld world;

	static Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */
	OrthographicCamera camera, cameraOverlay, cameraEvent;

	public Stage stage = new Stage(new ExtendViewport(1280, 720));

	long lastGameTick = 0;

	static public enum gameType {
		NEW_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TutorialWorld();
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		},
		TEST_WORLD {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TundraWorld();
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		},
		ENV_TEAM_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new VolcanoWorld();
				GameManager.get().getManager(NetworkManager.class).startHosting("host");
				return world;
			}
		};

		public abstract AbstractWorld method(); // could also be in an interface that MyEnum implements
	}


	public GameScreen(final ThomasGame game, final gameType startType) {
		if (startType == gameType.NEW_GAME) {
			GameManager.get().inTutorial = true;
			tutorial = true;
			GameManager.get().setWorld(startType.method());
		} else if (startType == gameType.ENV_TEAM_GAME) {
			GameManager.get().setWorld(startType.method());
		} else if (startType == gameType.TEST_WORLD) {
			GameManager.get().setWorld(startType.method());
		} else {
			GameManager.get().setNextWorld();
		}
		/* Create an example world for the engine */
		this.game = game;

		// Must initialize rendererEvent here so that gameWorld in GameManager is also initialized
		rendererEvent = new EventRenderer(true);

		// Initialize camera
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraOverlay = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraEvent = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

		GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
//		GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);

		// Add listener to the buttons in the pause menu
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.resume();
			}
		});
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Resume the game before quit to home screen
				GameManager.resume();
				// Reset quest tracker
				QuestTracker.resetOrbs();
				// Remove enemies
				GameManager.get().removeManager(GameManager.get().getManager(EnemyManager.class));
				// Dispose the screen
				dispose();
				// Set main menu screen
				game.setMainMenuScreen();
			}
		});
		enterButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.resume();
			}
		});

		stage.addActor(resumeButton);
		stage.addActor(quitButton);
		stage.addActor(enterButton);

		overlayRenderer = new OverlayRenderer();
	}

	/**
	 * Render the game normally
	 */
	public void renderGame(float delta ) {
		handleRenderables();

		CameraUtil.zoomableCamera(camera, Input.Keys.EQUALS, Input.Keys.MINUS, delta);
		CameraUtil.lockCameraOnTarget(camera, GameManager.get().getWorld().getPlayerEntity());

		cameraEvent.position.set(camera.position);
		cameraEvent.update();

		cameraOverlay.position.set(camera.position);
		cameraOverlay.update();

		SpriteBatch batchEvent = new SpriteBatch();
		batchEvent.setProjectionMatrix(cameraEvent.combined);

		SpriteBatch batchOverlay = new SpriteBatch();
		batchOverlay.setProjectionMatrix(cameraOverlay.combined);

		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// Clear the entire display as we are using lazy rendering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rerenderMapObjects(batch, camera);
		overlayRenderer.render(batchOverlay, cameraOverlay);
		rendererEvent.render(batchEvent, cameraEvent);


		spriteBatch.setProjectionMatrix(cameraOverlay.combined);
		//Add questTracker UI
		questTrackerRenderer.render(spriteBatch, cameraOverlay);
		//Add tutorial guideline if we are in the tutorial world
		if(tutorial){
			guideline.render(spriteBatch,cameraOverlay);
		}

		// Hide the buttons when the game is running
		resumeButton.setPosition(-100, -100);
		quitButton.setPosition(-100, -100);
		enterButton.setPosition(-100, -100);

		/* Refresh the experience UI for if information was updated */
		stage.act(delta);
		stage.draw();
		batch.dispose();
	}

	/**
	 * Render the game pause menu when the game is paused
	 */
	public void renderPauseMenu(float delta) {
		// Render the pause modal
		spriteBatch.setProjectionMatrix(cameraOverlay.combined);
		pauseModal.render(spriteBatch, cameraOverlay);

		// Display the buttons
		resumeButton.setPosition(width / 2 - resumeButton.getWidth() / 2,
				height / 2);
		quitButton.setPosition(width / 2 - quitButton.getWidth() / 2,
				height / 2 - 100);
		stage.act(delta);
		stage.draw();
	}

	/**
	 * Render the result of the game ('Victory' or 'Game Over')
	 */
	public void renderGameResult(float delta) {
		// Render the pause modal
		spriteBatch.setProjectionMatrix(cameraOverlay.combined);
		result.render(spriteBatch, cameraOverlay);

		// Display the buttons
		quitButton.setPosition(width / 2 - quitButton.getWidth() / 2,
				height / 2 - 350);
		stage.act(delta);
		stage.draw();
	}

	public void renderTransitionScreen(float delta) {
		// Render the transition screen
		spriteBatch.setProjectionMatrix(cameraOverlay.combined);
		transitionScreen.render(spriteBatch, cameraOverlay);

		// Display the button
		enterButton.setPosition(width / 2 - enterButton.getWidth() / 2, 150);
		stage.act(delta);
		stage.draw();
	}

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render(float delta) {
		switch (GameManager.get().state)
		{
			case TRANSITION:
				renderTransitionScreen(delta);
				break;
			case RUN:
				renderGame(delta);
				break;
			case PAUSED:
				renderPauseMenu(delta);
				break;
			case GAMEOVER:
			case VICTORY:
				renderGameResult(delta);
			default:
				break;
		}
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

		cameraOverlay.viewportWidth = width;
		cameraOverlay.viewportHeight = height;
		cameraOverlay.update();
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
		GameManager.getManagerFromInstance(InputManager.class).removeKeyDownListener(this);
		stage.dispose();
//		System.exit(0);
	}

	@Override
	public void notifyKeyDown(int keycode) {
		if (keycode == Input.Keys.F12 && GameManager.get().state == GameManager.State.RUN) {
			GameManager.get().debugMode = !GameManager.get().debugMode;
		}

		if (keycode == Input.Keys.ESCAPE) {
			if (GameManager.get().state == GameManager.State.RUN) {
				GameManager.pause();
			} else if (GameManager.get().state == GameManager.State.PAUSED) {
				GameManager.resume();
			}
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
			world.addEntity(new Peon(0f, 0f, 0.05f, 50));
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

		if (keycode == Input.Keys.F6) {
			DatabaseManager.saveWorldToJsonFile(GameManager.get().getWorld(), "resources/_save_.json");
		}
	}

}
