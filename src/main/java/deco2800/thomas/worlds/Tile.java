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
import deco2800.thomas.tasks.status.StatusEffect;
import deco2800.thomas.util.SquareVector;

public class Tile {

	/**
	 * Managing ID of tiles
	 */
	private static int nextID = 0;

	// The type of this Tile
	private String type = "Tile";

	// Whether this tile has an associated StatusEffect
	private boolean statusEffect = false;

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
	@Expose
	private SquareVector coords;

	private StaticEntity parent;

	@Expose
	private boolean obstructed = false;

	//Default Teleport attributes for Tile
	private float teleportRow = 0;
	private float teleportCol = 0;
	private boolean teleportTile = false;

	private boolean trapTile = false;
	private boolean trapActivated = false;

	private boolean rewardTile = false;
	private boolean rewardActivated = false;

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
		return type;
	}

	/**
	 * Sets the type of this Tile
	 *
	 * @param type The new type of this Tile
	 */
	public void setType(String type) {
		this.type = type;
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
		return String.format("{Tile[%.0f, %.1f: %d]}", coords.getCol(), coords.getRow(), index);
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

	/**
	 * Sets the Tile ID of this Tile.
	 *
	 * @param tileID The new Tile ID.
	 */
	public void setTileID(int tileID) {
		this.tileID = tileID;
	}

	public void setIndex(Integer indexValue) {
		this.index = indexValue;
	}

	public boolean isObstructed() {
		return obstructed;
	}

	/**
	 * Returns whether this Tile has an associated StatusEffect.
	 *
	 * @return Whether this Tile has an associated StatusEffect.
	 */
	public boolean hasStatusEffect() {
		return statusEffect;
	}

	/**
	 * Sets whether this Tile has an associated StatusEffect.
	 *
	 * @param statusEffect The associated StatusEffect.
	 */
	public void setStatusEffect(boolean statusEffect) {
		this.statusEffect = statusEffect;
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

	public void setTeleportRow(float row){
		this.teleportRow = row;
	}

	public void setTeleportCol(float col){
		this.teleportCol = col;
	}

	public void setTeleportTile(boolean teleport) {
		this.teleportTile = teleport;
	}

	public float getTeleportRow() {
		return this.teleportRow;
	}

	public float getTeleportCol() {
		return this.teleportCol;
	}

	public boolean isTeleportTile() {
		return this.teleportTile;
	}

	public void setTrapTile(boolean trap){
		this.trapTile = trap;
	}

	public boolean isTrapTile() {
		return this.trapTile;
	}

	public void setTrapActivated(boolean activated){
		this.trapActivated = activated;
	}

	public boolean getTrapActivated() {
		return this.trapActivated;
	}

	public void setRewardTile(boolean reward){
		this.rewardTile = reward;
	}

	public boolean isRewardTile() {
		return this.rewardTile;
	}

	public void setRewardActivated(boolean activated){
		this.rewardActivated = activated;
	}

	public boolean getRewardActivated() {
		return this.rewardActivated;
	}




}