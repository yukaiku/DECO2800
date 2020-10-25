package deco2800.thomas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.handlers.KeyboardManager;
import deco2800.thomas.managers.*;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.renderers.*;
import deco2800.thomas.util.CameraUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.TutorialWorld;
import deco2800.thomas.worlds.desert.DesertWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScreen implements Screen, KeyDownObserver {
	static float width = 1920;
	static float height = 1000;
	private static final Logger LOG = LoggerFactory.getLogger(GameScreen.class);
	@SuppressWarnings("unused")
	private final ThomasGame game;

	/**
	 * Set the renderer.
	 * 3D is for Isometric worlds
	 * Check the documentation for each renderer to see how it handles WorldEntity coordinates
	 */
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

	// Buttons in the pause menu
	String style = "in_game";
	Button resumeButton = new TextButton("RESUME", GameManager.get().getSkin(), style);
	Button quitButton = new TextButton("RETURN TO MAIN MENU", GameManager.get().getSkin(), style);
	Button enterButton = new TextButton("ENTER THE ZONE", GameManager.get().getSkin(), style);
	Button playAgainButton = new TextButton("PLAY AGAIN", GameManager.get().getSkin(), style);
	AbstractWorld world;

	static Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */
	OrthographicCamera camera;
	OrthographicCamera cameraOverlay;
	OrthographicCamera cameraEvent;

	private Stage stage = new Stage(new ExtendViewport(1920, 1000));

	long lastGameTick = 0;

	public enum gameType {
		NEW_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TutorialWorld();
				return world;
			}
		},
		TEST_WORLD {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new TutorialWorld();
				return world;
			}
		},
		ENV_TEAM_GAME {
			@Override
			public AbstractWorld method() {
				AbstractWorld world = new DesertWorld();
				return world;
			}
		};

		public abstract AbstractWorld method(); // could also be in an interface that MyEnum implements
	}

	/**
	 * Gets the current OverlayRenderer.
	 *
	 * @return OverlayRenderer
	 */
	public OverlayRenderer getOverlayRenderer() {
		return overlayRenderer;
	}


	public GameScreen(final ThomasGame game, final gameType startType) {
		switch (startType) {
			// start new game without debug mode
			case NEW_GAME:
				GameManager.get().setDebugMode(false);
				GameManager.get().setState(GameManager.State.TRANSITION);
				GameManager.get().setTutorial(true);
				GameManager.get().setWorld(startType.method());
				break;
			// enter environment maps with debugging
			case ENV_TEAM_GAME:
				GameManager.get().setDebugMode(true);
				GameManager.get().setWorld(startType.method());
				break;
			// start new game with debugging
			case TEST_WORLD:
				GameManager.get().setDebugMode(true);
				GameManager.get().setState(GameManager.State.TRANSITION);
				GameManager.get().setTutorial(true);
				GameManager.get().setWorld(startType.method());
				break;
			default:
				GameManager.get().setNextWorld();
				break;
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
				GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();
			}
		});
		enterButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.resume();
			}
		});
		playAgainButton.addListener(new ClickListener() {
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
				game.setScreen(new CharactersScreen(game));
			}
		});

		stage.addActor(resumeButton);
		stage.addActor(quitButton);
		stage.addActor(enterButton);
		stage.addActor(playAgainButton);

		overlayRenderer = new OverlayRenderer();
		overlayRenderer.setUpComponents();

		GameManager.getManagerFromInstance(ScreenManager.class).setCurrentScreen(this);
	}

	/**
	 * Render the game normally
	 */
	public void renderGame(float delta) {
		handleRenderables();

		CameraUtil.lockCameraOnTarget(camera, GameManager.get().getWorld().getPlayerEntity());
		CameraUtil.cameraBoundaryInWorld(camera, GameManager.get().getWorld());

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

		// Hide the buttons when the game is running
		resumeButton.setPosition(-1000, -1000);
		quitButton.setPosition(-1000, -1000);
		enterButton.setPosition(-1000, -1000);
		playAgainButton.setPosition(-1000, -1000);

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
		resumeButton.setPosition(stage.getWidth() / 2 - resumeButton.getWidth() / 2,
				height / 2);
		quitButton.setPosition(stage.getWidth() / 2 - quitButton.getWidth() / 2,
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
		playAgainButton.setPosition(width / 2 - playAgainButton.getWidth() / 2,
				height / 2 - 300);
		stage.act(delta);
		stage.draw();
	}

	public void renderTransitionScreen(float delta) {
		// Render the transition screen
		spriteBatch.setProjectionMatrix(cameraOverlay.combined);
		transitionScreen.render(spriteBatch, cameraOverlay);

		//Hide the other buttons
		resumeButton.setPosition(-1000, -1000);
		quitButton.setPosition(-1000, -1000);
		playAgainButton.setPosition(-1000, -1000);
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
		switch (GameManager.get().getState()) {
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
				break;
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
		// do nothing
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
	}

	@Override
	public void notifyKeyDown(int keycode) {
		if (keycode == Input.Keys.ENTER && GameManager.get().getState() == GameManager.State.TRANSITION) {
			GameManager.resume();
		}
		if (keycode == Input.Keys.F12 && GameManager.get().getState() == GameManager.State.RUN) {
			GameManager.get().setDebugMode(!GameManager.get().getDebugMode());
		}
		if (keycode == Input.Keys.N && GameManager.get().getDebugMode() && !GameManager.get().getWorld().getType().equals("World") && (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ||
				Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))) {
			Boss boss = GameManager.getManagerFromInstance(EnemyManager.class).getBoss();
			boss.applyDamage(boss.getCurrentHealth(), DamageType.COMMON);
			boss.applyDamage(boss.getCurrentHealth(), DamageType.COMMON);
			PlayerPeon playerPeon = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
			playerPeon.setPosition(boss.getPosition().getCol(),boss.getPosition().getRow(),boss.getHeight());
		}
		if (keycode == Input.Keys.ESCAPE && GameManager.get().getState() == GameManager.State.RUN ) {
			GameManager.pause();
		} else if (keycode == Input.Keys.ESCAPE && GameManager.get().getState() == GameManager.State.PAUSED) {
			GameManager.resume();
		}


		if (keycode == Input.Keys.F9) {
			GameManager.get().setTutorial(!GameManager.get().getTutorial());
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
			GameManager.get().setShowCoords(!GameManager.get().getShowCoords());
			LOG.info("Show coords is now {}", GameManager.get().getShowCoords());
		}


		if (keycode == Input.Keys.C) { // F11
			GameManager.get().setShowCoords(!GameManager.get().getShowCoords());
			LOG.info("Show coords is now {}", GameManager.get().getShowCoords());
		}

		if (keycode == Input.Keys.F10) { // F10
			GameManager.get().setShowPath(!GameManager.get().getShowPath());
			LOG.info("Show Path is now {}", GameManager.get().getShowPath());
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