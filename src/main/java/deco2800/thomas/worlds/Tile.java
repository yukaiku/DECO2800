package deco2800.thomas.worlds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;

import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.NetworkManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.SquareVector;

public class Tile {

	/**
	 * Managing ID of tiles
	 */
	private static int nextID = 0;

	private static int getNextID() {
		return nextID++;
	}

	public static void resetID() {
		nextID = 0;
	}

	/**
	 * Tile ID
	 */
	@Expose
	private int tileID = 0;

	/**
	 * Texture name of tile
	 */
	@Expose
	private String texture;

	/**
	 * Coordinates, which is a pair of numbers (col, row)
	 */
	private SquareVector coords;

	private StaticEntity parent;

	@Expose
	private boolean obstructed = false;

	/**
	 * Neighbours
	 */
	public static final int NORTH = 0;
	public static final int SOUTH = 2;
	public static final int EAST = 1;
	public static final int WEST = 3;
	private transient Map<Integer, Tile> neighbours;

	@Expose
	private int index = -1;

	public Tile(String texture) {
		this(texture, 0, 0);
	}

	public Tile(String texture, float col, float row) {
		this.texture = texture;
		coords = new SquareVector(col, row);
		this.neighbours = new HashMap<Integer, Tile>();
		this.tileID = Tile.getNextID();
	}

	public Tile() {
		this.neighbours = new HashMap<Integer, Tile>();
	}

	/**
	 * Returns the type of this Tile.
	 *
	 * @return The type of this Tile.
	 */
	public String getType() {
		return "Tile";
	}

	public float getCol() {
		return coords.getCol();
	}

	public float getRow() {
		return coords.getRow();
	}

	public SquareVector getCoordinates() {
		return new SquareVector(coords);
	}

	public String getTextureName() {
		return this.texture;
	}

	public Texture getTexture() {
		return GameManager.get().getManager(TextureManager.class).getTexture(this.texture);
	}


	public void addNeighbour(int direction, Tile neighbour) {
		neighbours.put(direction, neighbour);
	}

	public static int opposite(int dir) {
		return (dir + 2) % 4;
	}

	public void removeReferenceFromNeighbours() {
		for (Entry<Integer, Tile> neighbourHash : neighbours.entrySet()) {
			neighbourHash.getValue().getNeighbours().remove(Tile.opposite(neighbourHash.getKey()));
		}
	}

	public Tile getNeighbour(int direction) {
		return neighbours.get(direction);
	}

	public void removeNeighbour(int direction) {
		neighbours.remove(direction);
	}

	public Map<Integer, Tile> getNeighbours() {
		return neighbours;
	}

	public String toString() {
		return String.format("[%.0f, %.1f: %d]", coords.getCol(), coords.getRow(), index);
	}

	public StaticEntity getParent() {
		return parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public void setParent(StaticEntity parent) {
		this.parent = parent;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public void dispose() {
		if (this.hasParent() && this.parent != null) {
			for (SquareVector childposn : parent.getChildrenPositions()) {
				Tile child = GameManager.get().getWorld().getTile(childposn);
				if (child != null) {
					child.setParent(null);
					child.dispose();
				} else {
				}
			}
		}

		GameManager.get().getManager(NetworkManager.class).deleteTile(this);

		this.removeReferenceFromNeighbours();
		GameManager.get().getWorld().getTiles().remove(this);
	}

	public int calculateIndex() {
		if (index != -1) {
			return index;
		}

		int max = index;

		if (neighbours.containsKey(NORTH)) {
			max = Math.max(max, neighbours.get(NORTH).calculateIndex());
		}

		this.index = max + 1;
		return index;
	}

	public int getTileID() {
		return tileID;
	}

	public void setTileID(int tileID) {
		this.tileID = tileID;
	}

	public void setIndex(Integer indexValue) {
		this.index = indexValue;
	}

	public boolean isObstructed() {
		return obstructed;
	}

	public void setObstructed(boolean b) {
		obstructed = b;
	}

	public void setCol(float col) {
		this.coords.setCol(col);
	}

	public void setRow(float row) {
		this.coords.setRow(row);
	}
}