package deco2800.thomas.worlds;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TileMap {
	/**
	 * Width of the world; horizontal coordinates of the world will be within `[-width, width]`
	 */
	private int width;
	/**
	 * Height of the world; vertical coordinates of the world will be within `[-height, height]`
	 */
	private int height;

	/**
	 * Map of tiles to retrieve tiles from column and row
	 */
	Map<Integer, Map<Integer, Tile>> map;

	/**
	 * TileMap constructor
	 *
	 * @param width  horizontal coordinates of the world will be within `[-width, width]`
	 * @param height vertical coordinates of the world will be within `[-height, height]`
	 * @param tiles  all the tiles that the world has
	 */
	public TileMap(int width, int height, CopyOnWriteArrayList<Tile> tiles) {
		// Dimensions
		this.width = width;
		this.height = height;

		// Create map
		map = new ConcurrentHashMap<>();
		for (int col = -width; col <= width; col++) {
			map.put(col, new ConcurrentHashMap<>());
		}

		// Insert tiles into map
		for (Tile tile : tiles) {
			int col = (int) tile.getCol();
			int row = (int) tile.getRow();

			if (col < -width || col > width) {
				throw new RuntimeException("col of Tile instance must be within the range [-width, width - 1]");
			}
			if (row < -height || row > height) {
				throw new RuntimeException("row of Tile instance must be within the range [-height, height - 1]");
			}

			map.get(col).put(row, tile);
		}

	}

	/**
	 * Check if a tile (or if the coordinates are valid) in a world
	 *
	 * @param col
	 * @param row
	 * @return
	 */
	public boolean existsTile(int col, int row) {
		return this.map.containsKey(col)
				&& this.map.get(col).containsKey(row)
				&& this.map.get(col).get(row) != null;
	}

	/**
	 * Get a tile from column and row
	 *
	 * @param col
	 * @param row
	 * @return the tile
	 */
	public Tile getTile(int col, int row) {
		return this.map.get(col).get(row);
	}
}
