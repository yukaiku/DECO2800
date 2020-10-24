package deco2800.thomas.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.ScreenManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.components.FloatingDamageComponent;
import deco2800.thomas.worlds.Tile;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A utility class for the World instances.
 * Created by BradleyKing on 15/6/18.
 */
public class WorldUtil {

	private static final float SCALE = 0.3f; // Scales the assets to reasonable sizes

	private static final boolean ISO_MODE = false;

	public static final float SCALE_X = SCALE;
	public static final float SCALE_Y = SCALE * (ISO_MODE ? 5 / 8f : 1f);

	//base sizes for calculating grid placement.
	public static final float TILE_WIDTH = TextureManager.TILE_WIDTH * SCALE_X;
	public static final float TILE_HEIGHT = TextureManager.TILE_HEIGHT * SCALE_Y;

	private WorldUtil() {
	}

	/**
	 * Converts screen coordinates to world coordinates.
	 *
	 * @param x the x coordinate on the screen
	 * @param y the y coordinate on the screen
	 * @return a float array of the world coordinates
	 */
	public static float[] screenToWorldCoordinates(float x, float y) {
		Vector3 result = GameManager.get().getCamera().unproject(new Vector3(x, y, 0));

		return new float[]{result.x, result.y};
	}

	public static float[] worldCoordinatesToColRow(float x, float y) {
		float row;
		float col;
		float actualRow;
		x -= TILE_WIDTH / 2;
		y -= TILE_HEIGHT / 2;
		col = Math.round(x / (TILE_WIDTH * 0.75f));
		actualRow = y / TILE_HEIGHT;
		row = Math.round(actualRow);
		return new float[]{col, row};
	}

	/**
	 * Same function as above, but returns a primitive type. Much faster for rendering.
	 *
	 * @param col coordinate column
	 * @param row coordinate row
	 * @return a float array containing the world coordinates
	 */
	public static float[] colRowToWorldCords(float col, float row) {
		float squareX = col * TILE_WIDTH * 0.75f; //sprite ratio to fix sprite
		float squareY = row * TILE_HEIGHT;

		return new float[]{squareX, squareY};
	}

	public static boolean validColRow(SquareVector pos) {
		if (pos.getCol() % 1 != 0) {
			return false;
		}

		return (pos.getRow()) % 1 == 0;
	}

	/**
	 * Removes an Entity from the Game World.
	 * @param entity Entity to remove.
	 */
	public static void removeEntity(AbstractEntity entity) {
		GameManager.get().getWorld().removeEntity(entity);
		GameManager.get().getWorld().disposeEntity(entity.getEntityID());
	}

	/**
	 * Determines if the position is safe to walk on or place machines on.
	 *
	 * @param col the x coordinate of a position
	 * @param row the y coordinate of a position
	 * @return A boolean stating if the position is safe to walk on.
	 */
	public static boolean isWalkable(float col, float row) {
		if (GameManager.get().getWorld() == null || GameManager.get().getWorld().getTile(col, row) == null)
			return false;

		return !GameManager.get().getWorld().getTile(col, row).isObstructed();
	}

	public static boolean areCoordinatesOffScreen(float squareX, float squareY, OrthographicCamera camera) {
		float bufferWidth = 1.1f;
		return squareX < (camera.position.x - camera.viewportWidth * camera.zoom / 2 - 2 * TILE_WIDTH * camera.zoom * bufferWidth)
				|| squareX > (camera.position.x + camera.viewportWidth * camera.zoom / 2 + TILE_WIDTH * camera.zoom * bufferWidth + 50)
				|| squareY < (camera.position.y - camera.viewportHeight * camera.zoom / 2 - 4 * TILE_HEIGHT * camera.zoom * bufferWidth)
				|| squareY > (camera.position.y + camera.viewportHeight * camera.zoom / 2 + TILE_HEIGHT * camera.zoom * bufferWidth - 50);
	}

	/**
	 * Returns a reference to the floating damage component.
	 */
	public static FloatingDamageComponent getFloatingDamageComponent() {
		OverlayComponent component = GameManager.get().getManager(ScreenManager.class).getCurrentScreen()
				.getOverlayRenderer().getComponentByInstance(FloatingDamageComponent.class);
		return (FloatingDamageComponent)component;
	}

	/**
	 * Attempts to find the closest Walkable tile to a location.
	 * NO LOGGING BECAUSE OF HORRENDOUS SPAM
	 *
	 * @param startLocation the location of a tile to start the search
	 * @return a location of a walkable tile or null if none where found
	 */
	public static SquareVector closestWalkable(SquareVector startLocation) {
		LinkedList<SquareVector> queue = new LinkedList<>();
		// A set of all the tiles that have been added to the queue
		// Used to verify no duplicates are added for infinite loops
		Set<SquareVector> closedSet = new HashSet<>();
		queue.add(startLocation);
		while (!queue.isEmpty()) {
			// Make the first in queue the default start
			SquareVector nextLocation = queue.get(0);

			// Attempt to get the closest to the start location out of the queue
			for (SquareVector neighbour : queue) {
				// Improve this as it has some funky outcomes
				if (Math.abs(neighbour.distance(startLocation.getCol(), startLocation.getRow())) < Math.abs(nextLocation.distance(startLocation.getCol(), startLocation.getRow()))) {
					nextLocation = neighbour;
				}
			}
			// If the closest is walkable then return it
			if (isWalkable(nextLocation.getCol(), nextLocation.getRow())) {
				return nextLocation;
			}
			// Get neighbours of the closest and add to queue if they haven't already been checked
			getNeighbours(nextLocation, closedSet, queue);
			// Remove the invalid location
			queue.remove(nextLocation);
		}
		return null;
	}

	private static void getNeighbours(SquareVector nextLocation, Set<SquareVector> closedSet, LinkedList<SquareVector> queue) {
		Tile nextLocationTile = GameManager.get().getWorld().getTile(nextLocation.getCol(), nextLocation.getRow());
		if (nextLocationTile != null) {
			for (int i = 0; i < 6; i++) {
				Tile neighbour = nextLocationTile.getNeighbour(i);
				if (!closedSet.contains(neighbour.getCoordinates())) {
					closedSet.add(neighbour.getCoordinates());
					queue.add(neighbour.getCoordinates());
				}
			}
		}
	}
}
