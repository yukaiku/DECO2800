package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.renderers.components.BossHealthComponent;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.dungeons.DesertDungeon;
import deco2800.thomas.worlds.dungeons.SwampDungeon;
import deco2800.thomas.worlds.dungeons.TundraDungeon;
import deco2800.thomas.worlds.dungeons.VolcanoDungeon;
import deco2800.thomas.worlds.swamp.SwampWorld;
import deco2800.thomas.worlds.tundra.TundraWorld;
import deco2800.thomas.worlds.volcano.VolcanoWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class GameManager {
	//debug values stored here
	private int entitiesRendered;
	private int entitiesCount;
	private int tilesRendered;
	private int tilesCount;

	private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	private static GameManager instance = null;

	// The list of all instantiated managers classes.
	private List<AbstractManager> managers = new ArrayList<>();

	// The game world currently being played on.
	private AbstractWorld gameWorld;

	// The camera being used by the Game Screen to project the game world.
	private OrthographicCamera camera;

	// The stage the game world is being rendered on to.
	private Stage stage;

	// The UI skin being used by the game for libGDX elements.
	private Skin skin;

	private AbstractWorld worldOutsideDungeon;

	private EnemyManager enemyManagerOutSideWorld;

	private String currentDungeon;


	private static float fps = 0;

	private boolean debugMode;

	private static boolean tutorial = true;

	// Whether or not the player has moved to the next Zone
	private boolean movedToNextWorld;
	/**
	 * Whether or not we render info over the tiles.
	 */
	// Whether or not we render the movement path for Players.
	private static boolean showCoords = false;

	// The game screen for a game that's currently running.
	private static boolean showPath = false;

	/**
	 * Whether or not we render info over the entities
	 */
	private static boolean showCoordsEntity = false;

	private enum WorldType {
		SWAMP_WORLD,
		TUNDRA_WORLD,
		DESERT_WORLD,
		VOLCANO_WORLD
	}
	private ArrayList<WorldType> worldOrder;
	private State state = State.RUN;
	public enum State
	{
		PAUSED,
		RUN,
		GAMEOVER,
		VICTORY,
		TRANSITION
	}
	/**
	 * Returns an instance of the GM
	 *
	 * @return GameManager
	 */
	public static GameManager get() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	/**
	 * Private constructor to enforce use of get()
	 */
	private GameManager() {
		//Loads the order of the worlds
		worldOrder = new ArrayList<>(EnumSet.allOf(WorldType.class));
		debugMode = false;
	}

	/**
	 * Add a manager to the current instance, making a new instance if none
	 * exist
	 *
	 * @param manager
	 */
	public static void addManagerToInstance(AbstractManager manager) {
		get().addManager(manager);
	}

	/**
	 * Adds a manager component to the GM
	 *
	 * @param manager
	 */
	public void addManager(AbstractManager manager) {
		managers.add(manager);
	}

	/** Removes a manager */
	public void removeManager(AbstractManager manager) {
		managers.remove(manager);
	}

	/**
	 * Retrieves a manager from the list.
	 * If the manager does not exist one will be created, added to the list and returned
	 *
	 * @param type The class type (ie SoundManager.class)
	 * @return A Manager component of the requested type
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractManager> T getManager(Class<T> type) {
		/* Check if the manager exists */
		for (AbstractManager m : managers) {
			if (m.getClass() == type) {
				return (T) m;
			}
		}
		LOGGER.info("creating new manager instance");
		/* Otherwise create one */
		AbstractManager newInstance;
		try {
			Constructor<?> ctor = type.getConstructor();
			newInstance = (AbstractManager) ctor.newInstance();
			this.addManager(newInstance);
			return (T) newInstance;
		} catch (Exception e) {
			// Gotta catch 'em all
			LOGGER.error("Exception occurred when adding Manager.");
		}

		LOGGER.warn("GameManager.getManager returned null! It shouldn't have!");
		return null;
	}

	/**
	 * Retrieve a manager from the current GameManager instance, making a new
	 * instance when none are available.
	 *
	 * @param type The class type (ie SoundManager.class)
	 * @return A Manager component of the requested type
	 */
	public static <T extends AbstractManager> T getManagerFromInstance(Class<T> type) {
		return get().getManager(type);
	}


	/* ------------------------------------------------------------------------
	 * 				GETTERS AND SETTERS BELOW THIS COMMENT.
	 * ------------------------------------------------------------------------ */

	/**
	 * Get entities rendered count
	 *
	 * @return entities rendered count
	 */
	public int getEntitiesRendered() {
		return this.entitiesRendered;
	}

	/**
	 * Set entities rendered to new amount
	 *
	 * @param entitiesRendered the new amount
	 */
	public void setEntitiesRendered(int entitiesRendered) {
		this.entitiesRendered = entitiesRendered;
	}

	/**
	 * Get number of entities
	 *
	 * @return entities count
	 */
	public int getEntitiesCount() {
		return this.entitiesCount;
	}

	/**
	 * Set entities count to new amount
	 *
	 * @param entitiesCount the new amount
	 */
	public void setEntitiesCount(int entitiesCount) {
		this.entitiesCount = entitiesCount;
	}

	/**
	 * Get tiles rendered count
	 *
	 * @return tiles rendered count
	 */
	public int getTilesRendered() {
		return this.tilesRendered;
	}

	/**
	 * Set tiles rendered to new amount
	 *
	 * @param tilesRendered the new amount
	 */
	public void setTilesRendered(int tilesRendered) {
		this.tilesRendered = tilesRendered;
	}

	/**
	 * Get number of tiles
	 *
	 * @return tiles count
	 */
	public int getTilesCount() {
		return this.tilesCount;
	}

	/**
	 * Set tiles count to new amount
	 *
	 * @param tilesCount the new amount
	 */
	public void setTilesCount(int tilesCount) {
		this.tilesCount = tilesCount;
	}

	/***
	 * Get the FPS
	 * @return fps
	 */
	public float getFps() {
		return fps;
	}

	/***
	 * Sets the FPS
	 * @param fps float
	 */
	public static void setFps(float fps) {
		GameManager.fps = fps;
	}

	/***
	 * Gets the debugMode
	 * @return debugMode boolean
	 */
	public boolean getDebugMode() {
		return debugMode;
	}

	/***
	 * Sets the debugMode
	 * @param debugMode boolean
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public boolean getTutorial(){
		return tutorial;
	}

	public static void setTutorial(boolean tutorial){
		GameManager.tutorial = tutorial;
	}

	public boolean getMovedToNextWorld() {
		return movedToNextWorld;
	}
	public void setMovedToNextWorld(boolean movedToNextWorld) {
		this.movedToNextWorld = movedToNextWorld;
	}

	public boolean getShowCoords() {
		return showCoords;
	}
	public static void setShowCoords(boolean showCoords) {
		GameManager.showCoords = showCoords;
	}

	public boolean getShowPath() {
		return showPath;
	}
	public static void setShowPath(boolean showPath) {
		GameManager.showPath = showPath;
	}

	public boolean getShowCoordsEntity() {
		return showCoordsEntity;
	}
	public static void setShowCoordsEntity(boolean showCoordsEntity) {
		GameManager.showCoordsEntity = showCoordsEntity;
	}

	public State getState(){
		return state;
	}

	public void setState(State state){
		this.state = state;
	}

	/**
	 * Sets the current game world
	 *
	 * @param world
	 */
	public void setWorld(AbstractWorld world) {
		this.gameWorld = world;
	}

	/**
	 * Gets the current game world
	 *
	 * @return the current game world
	 */
	public AbstractWorld getWorld() {
		return gameWorld;
	}

	/**
	 * Teleport the player to the next world by setting
	 * it as current world
	 */
	public void setNextWorld() {
		// removes the previous enemy manager
		managers.removeIf(manager -> manager instanceof EnemyManager);
		this.getWorld().dispose(); // Dispose world
		WorldType worldType = worldOrder.get(QuestTracker.orbTracker().size());
		switch (worldType) {
			//SWAMP , TUNDRA, DESERT, VOLCANO
			case SWAMP_WORLD:
				this.setWorld(new SwampWorld());
				break;
			case TUNDRA_WORLD:
				this.setWorld(new TundraWorld());
				break;
			case DESERT_WORLD:
				this.setWorld(new DesertWorld());
				break;
			case VOLCANO_WORLD:
				this.setWorld(new VolcanoWorld());
				break;
		}
		movedToNextWorld = true;
		GameManager.get().state = State.TRANSITION;
	}

	/**
	 * Void that places the player in the zone's respective dungeon while
	 * storing the main world to be returned to
	 *
	 * @param dungeon
	 */
	public void enterDungeon(String dungeon){

		if (dungeon.equals("ExitPortal")) {
			this.exitDungeon();
			return;
		}

		this.currentDungeon = dungeon;
		this.worldOutsideDungeon = this.getWorld();
		this.enemyManagerOutSideWorld = this.getManager(EnemyManager.class);
		// removes the previous enemy manager
		managers.removeIf(manager -> manager instanceof EnemyManager);
		switch(dungeon) {
			case "VolcanoDungeonPortal":
				this.setWorld(new VolcanoDungeon());
				break;
			case "TundraDungeonPortal":
				this.setWorld(new TundraDungeon());
				break;
			case "SwampDungeonPortal":
				this.setWorld(new SwampDungeon());
				break;
			case "DesertDungeonPortal":
				this.setWorld(new DesertDungeon());
				break;
		}
		// pause boss battle
		boolean bossOnGoing = getManager(ScreenManager.class).getCurrentScreen().getOverlayRenderer().
				getComponentByInstance(BossHealthComponent.class).setRender(false);
		if (bossOnGoing) getManager(SoundManager.class).toggleBossMusic(false);
	}

	/**
	 * Method to handle returning to existing world upon exiting a dungeon that
	 * returns the player & removes the original dungeon portal from the world.
	 */
	public void exitDungeon() {

		//Remove the tiles parent
		for (Tile tile : worldOutsideDungeon.getTiles()) {
			if (tile.hasParent() && tile.getParent().getObjectName().equals(this.currentDungeon)) {
				tile.setParent(null);
			}
		}
		//Remove the portal from the entities list.
		for (AbstractEntity entity : worldOutsideDungeon.getEntities()) {
			if (entity.getObjectName().equals(this.currentDungeon)) {
				worldOutsideDungeon.removeEntity(entity);
			}
		}
		//Keep the same player
		AgentEntity player = this.getWorld().getPlayerEntity();
		// Dispose Dungeon & reset manager
		this.getWorld().dispose();
		managers.removeIf(manager -> manager instanceof EnemyManager);
		//Add existing world & enemy manager
		this.addManager(enemyManagerOutSideWorld);
		this.setWorld(worldOutsideDungeon);
		((PlayerPeon)this.worldOutsideDungeon.getPlayerEntity()).updatePlayerSkills();
		this.worldOutsideDungeon = null;

		// resume boss battle
		boolean bossOnGoing = getManager(ScreenManager.class).getCurrentScreen().getOverlayRenderer().
				getComponentByInstance(BossHealthComponent.class).setRender(true);
		if (bossOnGoing) getManager(SoundManager.class).toggleBossMusic(true);
	}


	public static void resume() {
		GameManager.get().state = GameManager.State.RUN;
	}

	public static void pause() {
		GameManager.get().state = State.PAUSED;
	}

	public static void victory() {
		GameManager.get().state = State.VICTORY;
		GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();
		GameManager.getManagerFromInstance(SoundManager.class).stopAmbience();
		GameManager.getManagerFromInstance(SoundManager.class).playSound("victory");
	}

	public static void gameOver() {
		GameManager.get().state = State.GAMEOVER;
		GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();
		GameManager.getManagerFromInstance(SoundManager.class).stopAmbience();
		GameManager.getManagerFromInstance(SoundManager.class).playSound("gameOver");
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	/**
	 * @return current game's stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage - the current game's stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return current game's skin
	 */
	public Skin getSkin() {
		return skin;
	}

	/**
	 * @param skin - the current game's skin
	 */
	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * On tick method for ticking managers with the TickableManager interface
	 *
	 * @param i
	 */
	public void onTick(long i) {
		for (AbstractManager m : managers) {
			if (m instanceof TickableManager) {
				((TickableManager) m).onTick(0);
			}
		}
		gameWorld.onTick(0);
	}
}
