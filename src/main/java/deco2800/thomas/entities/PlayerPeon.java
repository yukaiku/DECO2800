package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerPeon extends Peon implements TouchDownObserver {
	public static String ENTITY_ID_STRING = "playerPeon";
	private AbstractWorld world;

	// a list storing all of the locations of damage tiles on the current world
	private CopyOnWriteArrayList<Tile> damageTiles;

	// a list storing all of the locations of slowing tiles on the current world
	private CopyOnWriteArrayList<Tile> slowTiles;

	// a bool for if the above lists have been generated
	private boolean notGenerated = true;

	// booleans for whether the player is slowed or taking damage from standing on a tile
	private boolean slowed = false;
	private boolean takingDamage = false;

	public PlayerPeon(float row, float col, float speed) {
		super(row, col, speed);
		this.setObjectName(ENTITY_ID_STRING);
		GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
	}

	/**
	 * Adds a Tile to a list of tiles, if it is not already present in the list.
	 *
	 * @param tile The Tile being added.
	 * @param list The list being added to.
	 */
	private void addTile(Tile tile, CopyOnWriteArrayList<Tile> list) {
		for (Tile t : list) {
			// Tile toStrings should be unique in the list
			if (t.toString().equals(tile.toString())) {
				return;
			}
		}
		list.add(tile);
	}

	/**
	 * Adds all tiles that should slow the player in a specific world to a slowTiles list.
	 */
	private void slowTiles() {
		slowTiles = new CopyOnWriteArrayList<>();

		// the Desert World has quicksand which slows the player
		if (world.getType().equals("Desert")) {

			for (Tile tile : world.getTiles()) {

				// Quicksand slows the player if they stand on top of it
				if (tile.getType().equals("Quicksand")) {
					addTile(tile, slowTiles);
				}
			}
		}
	}

	/**
	 * Adds all tiles that should damage the player in a specific world to a damageTiles list.
	 */
	private void damageTiles() {
		damageTiles = new CopyOnWriteArrayList<>();

		// the Desert World has quicksand and cactus plants which damage the player
		if (world.getType().equals("Desert")) {

			for (Tile tile : world.getTiles()) {

				// Quicksand damages the player if they stand on top of it
				if (tile.getType().equals("Quicksand")) {
					addTile(tile, damageTiles);

				// Cactus plants damage the player if they stand in a neighbouring tile
				} else if (tile.getType().equals("Cactus")) {
					for (Tile neighbour : tile.getNeighbours().values()) {
						addTile(neighbour, damageTiles);
					}
				}
			}
		}
	}


	@Override
	public void onTick(long i) {
		if (getTask() != null && getTask().isAlive()) {
			getTask().onTick(i);

			if (getTask().isComplete()) {
				setTask(null);
			}
		}

		//TODO Implement damage/slowing effects when standing on tiles in
		// the damageTiles/slowTiles lists

		// Get the lists of all important tiles for player interaction
		if (notGenerated) {
			world = GameManager.get().getWorld();
			slowTiles();
			damageTiles();
			notGenerated = false;
		}
	}

	@Override
	public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
		float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
		float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

		System.out.printf("mouse: %.2f %.2f%n", mouse[0], mouse[1]);
		System.out.printf("clickedPosition: %.2f %.2f%n", clickedPosition[0], clickedPosition[1]);
		setTask(new MovementTask(this, new SquareVector(clickedPosition[0], clickedPosition[1])));
	}
}
