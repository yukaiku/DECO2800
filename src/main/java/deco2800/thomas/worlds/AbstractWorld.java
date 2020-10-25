package deco2800.thomas.worlds;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * AbstractWorld is the Game AbstractWorld
 * <p>
 * It provides storage for the WorldEntities and other universal world level items.
 */
public abstract class AbstractWorld implements Tickable {
	/**
	 * Default width of the world; horizontal coordinates of the world will be within `[-DEFAULT_WIDTH, DEFAULT_WIDTH]`
	 */
	protected static final int DEFAULT_WIDTH = 25;


	/**
	 * Default height of the world; vertical coordinates of the world will be within `[-DEFAULT_HEIGHT, DEFAULT_HEIGHT]`
	 */
	protected static final int DEFAULT_HEIGHT = 25;

    protected AgentEntity playerEntity;

	/**
	 * The static entity which is the Orb. All maps
	 * have only one Orb entity
	 */
	private Orb orbEntity;

	/**
	 * Width of the world; horizontal coordinates of the world will be within `[-width, width]`
	 */
	protected int width;

	/**
	 * Height of the world; vertical coordinates of the world will be within `[-height, height]`
	 */
	protected int height;

	/**
	 * Entities inside the world
	 */
	protected List<AbstractEntity> entities;

	/**
	 * List of tiles of the world
	 */
	protected CopyOnWriteArrayList<Tile> tiles;

	/**
	 * A map that is used to retrieve tiles from horizontal and vertical coordinates
	 */
	protected TileMap tileMap;

	/**
	 * A queue of entities to be removed in onTick
	 */
	protected List<AbstractEntity> entitiesToRemove;

	/**
	 * A queue of tiles to be removed in onTick
	 */
	protected List<Tile> tilesToRemove;

	/**
	 * Constructor that creates a world with default width and height
	 */
	protected AbstractWorld() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 *
	 */
	private WorldEvent worldEvent;

	private boolean zoomable = true;

	/**
	 * Constructor that creates a world with given width and height
	 *
	 * @param width  width of the world; horizontal coordinates of the world will be within `[-width, width]`
	 * @param height eight of the world; vertical coordinates of the world will be within `[-height, height]`
	 */
	protected AbstractWorld(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new CopyOnWriteArrayList<>();
		this.entities = new CopyOnWriteArrayList<>();
		this.entitiesToRemove = new CopyOnWriteArrayList<>();
		this.tilesToRemove = new CopyOnWriteArrayList<>();
		generateTiles();
		generateTileMap();
		assignTileNeighbours();
		generateTileIndices();
	}

	/**
	 * Returns the type of this world.
	 *
	 * @return The type of this world.
	 */
	public String getType() {
		return "World";
	}

	/**
	 * Generates the tiles for the world
	 */
	protected abstract void generateTiles();

	/**
	 * Set the orbEntity and add it to the entities list
	 * @param orb
	 */
	public void setOrbEntity(Orb orb) {
		this.orbEntity = orb;
		this.entities.add(orb);
	}

	/**
	 * Get the current Orb entity in this world
	 * @return orbEntity
	 */
	public Orb getOrbEntity() {
		return orbEntity;
	}

	/**
	 * Generates a tileMap from the list of tiles
	 */
	public void generateTileMap() {
		this.tileMap = new TileMap(width, height, tiles);
	}

	/**
	 * Assigns neighbours for the tiles. Each tile has at most 4 neighbours: NORTH, SOUTH, EAST, WEST
	 */
	public void assignTileNeighbours() {
		for (Tile tile : this.tiles) {
			int col = (int) tile.getCol();
			int row = (int) tile.getRow();

			if (this.tileMap.existsTile(col, row + 1)) {
				Tile northNeighbour = this.tileMap.getTile(col, row + 1);
				tile.addNeighbour(Tile.NORTH, northNeighbour);
			}

			if (this.tileMap.existsTile(col, row - 1)) {
				Tile southNeighbour = this.tileMap.getTile(col, row - 1);
				tile.addNeighbour(Tile.SOUTH, southNeighbour);
			}

			if (this.tileMap.existsTile(col + 1, row)) {
				Tile eastNeighbour = this.tileMap.getTile(col + 1, row);
				tile.addNeighbour(Tile.EAST, eastNeighbour);
			}

			if (this.tileMap.existsTile(col - 1, row)) {
				Tile westNeighbour = this.tileMap.getTile(col - 1, row);
				tile.addNeighbour(Tile.WEST, westNeighbour);
			}
		}
	}

	/**
	 * TODO Find out what this method does, reconsider it, and write doc comment for it
	 */
	protected void generateTileIndices() {
		for (Tile tile : tiles) {
			tile.calculateIndex();
		}
	}

	/**
	 * Gets the list of tiles of the world
	 *
	 * @return list of tiles
	 */
	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * Gets a tile given coordinates `col` and `row`
	 *
	 * @param col horizontal coordinate
	 * @param row vertical coordinate
	 * @return the tile with the given coordinates
	 */
	public Tile getTile(float col, float row) {
		return getTile(new SquareVector(col, row));
	}

	/**
	 * Gets a tile given coordinates as a SquareVector
	 *
	 * @param position
	 * @return the tile with the given coordinates
	 */
	public Tile getTile(SquareVector position) {
		for (Tile tile : tiles) {
			if (tile.getCoordinates().equals(position)) {
				return tile;
			}
		}
		return null;
	}

	/**
	 * Gets a tile by tileID. tileID are assigned when a tile is created. All tiles have distinct tileIDs.
	 *
	 * @param tileID
	 * @return the tile with the given tileID
	 */
	public Tile getTile(int tileID) {
		for (Tile tile : tiles) {
			if (tile.getTileID() == tileID) {
				return tile;
			}
		}
		return null;
	}

	/**
	 * Assigns tiles to the world. If the world has already had a list of tile, replace it with the new list
	 *
	 * @param tiles new list of tiles
	 */
	public void setTiles(CopyOnWriteArrayList<Tile> tiles) {
		if (tiles != null) {
			queueTilesForRemove(this.tiles);
		}
		this.tiles = tiles;
		generateTileMap();
		assignTileNeighbours();
	}

	/**
	 * @deprecated tileMap and neighbours need to be updated
	 */
	@Deprecated
	public void updateTile(Tile tile) {
		for (Tile t : this.tiles) {
			if (t.getTileID() == tile.getTileID()) {
				if (!t.equals(tile)) {
					this.tiles.remove(t);
					this.tiles.add(tile);
				}
				return;
			}
		}
		this.tiles.add(tile);
	}

	/**
	 * Dispose a tile by tileID
	 *
	 * @param tileID
	 */
	public void disposeTile(int tileID) {
		Tile tile = GameManager.get().getWorld().getTile(tileID);
		if (tile != null) {
			tile.dispose();
		}
	}

	/**
	 * Queues tiles to be removed later. Remember to dispose any related resources first.
	 *
	 * @param tiles
	 */
	public void queueTilesForRemove(List<Tile> tiles) {
		tilesToRemove.addAll(tiles);
	}

	/**
	 * Returns a list of entities in this world.
	 *
	 * @return all entities in the world
	 */
	public List<AbstractEntity> getEntities() {
		return new CopyOnWriteArrayList<>(this.entities);
	}

	/**
	 * Returns a list of entities in this world, ordered by their render level.
	 *
	 * @return all entities in the world
	 */
	public List<AbstractEntity> getSortedEntities() {
		List<AbstractEntity> e = new CopyOnWriteArrayList<>(this.entities);
		Collections.sort(e);
		return e;
	}

	/**
	 * Returns a list of entities in this world, ordered by their render level.
	 *
	 * @return all entities in the world
	 */
	public List<AgentEntity> getSortedAgentEntities() {
		List<AgentEntity> e = this.entities
				.stream()
				.filter(p -> p instanceof AgentEntity)
				.map(p -> (AgentEntity) p)
				.collect(Collectors.toList());

		Collections.sort(e);
		return e;
	}

    /**
     * Gets an array list of all the entities contained within the given bounds.
     * @param bounds Bounding box to check within.
     * @return ArrayList of all entities within bounds.
     */
    public List<AbstractEntity> getEntitiesInBounds(BoundingBox bounds) {
        List<AbstractEntity> entitiesInBounds = new ArrayList<>();
        for (AbstractEntity entity : entities) {
            if (bounds.overlaps(entity.getBounds())) {
                entitiesInBounds.add(entity);
            }
        }

        return entitiesInBounds;
    }

	/**
	 * Adds an entity to the world.
	 *
	 * @param entity the entity to add
	 */
	public void addEntity(AbstractEntity entity) {
		entities.add(entity);
	}

	/**
	 * Removes an entity from the entity list of the world
	 *
	 * @param entity the entity to remove
	 */
	public void removeEntity(AbstractEntity entity) {
		entities.remove(entity);
	}

	/**
	 * TODO Find out what this method does, reconsider it, and write doc comment for it
	 */
	public void setEntities(List<AbstractEntity> entities) {
		this.entities = entities;
	}

	/**
	 * TODO Reconsider this method and write doc comment for it
	 */
	public void updateEntity(AbstractEntity entity) {
		for (AbstractEntity e : this.entities) {
			if (e.getEntityID() == entity.getEntityID()) {
				this.entities.remove(e);
				this.entities.add(entity);
				return;
			}
		}
		this.entities.add(entity);

		// Since MultiEntities need to be attached to the tiles they live on, setup that connection.
		if (entity instanceof StaticEntity) {
			((StaticEntity) entity).setup();
		}
	}

	/**
	 * Disposes an entity by entityID
	 *
	 * @param entityID entityID of the entity to be disposed
	 */
	public void disposeEntity(int entityID) {
		for (AbstractEntity entity : this.getEntities()) {
			if (entity.getEntityID() == entityID) {
				entity.dispose();
			}
		}
	}

	/**
	 * Queues entities to be removed later. Remember to dispose any related resources first.
	 *
	 * @param entities entities that are queued to be removed
	 */
	public void queueEntitiesForRemove(List<AbstractEntity> entities) {
		entitiesToRemove.addAll(entities);
	}

	public void onTick(long i) {
		for (AbstractEntity entity : entitiesToRemove) {
			entities.remove(entity);
		}

		for (Tile tile : tilesToRemove) {
			tiles.remove(tile);
		}
	}

    public void setTileMap(CopyOnWriteArrayList<Tile> tileMap) {
        this.tiles = tileMap;
    }

    public void deleteTile(int tileid) {
        Tile tile = GameManager.get().getWorld().getTile(tileid);
        if (tile != null) {
            tile.dispose();
        }
    }

    public void deleteEntity(int entityID) {
        for (AbstractEntity e : this.getEntities()) {
            if (e.getEntityID() == entityID) {
                e.dispose();
            }
        }
    }

    public AgentEntity getPlayerEntity() {
        return playerEntity;
    }

	public void setPlayerEntity(AgentEntity playerEntity) {
		this.playerEntity = playerEntity;
	}

	/**
	 * Get (half of) the width of the world.
	 * @return the width of the world
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Get (half of) the height of the world.
	 * @return the height of the world
	 */
	public int getHeight() {
		return this.height;
	}

	public WorldEvent getWorldEvent() {
		return this.worldEvent;
	}

	public void setWorldEvent(WorldEvent event) {
		this.worldEvent = event;
	}

	public boolean getWorldZoomable() {
		return true;
	}

	/**
	 * Skeleton method for activating a trap tile.
	 *
	 * @param tile the tile of the trap tile.
	 */
	public void activateTrapTile(Tile tile){

	}


	/**
	 * Skeleton method for activating a reward tile.
	 *
	 * @param tile the tile of the trap tile.
	 */
	public void activateRewardTile(Tile tile){

	}

	/**
	 * Disposes the World, clearing all entities and tiles from memory.
	 */
	public void dispose() {
		// Clear all tiles and entities
		for (AbstractEntity e : this.entities) {
			e.dispose();
		}
		this.entities.clear();
		this.entitiesToRemove.clear();
		this.playerEntity.dispose();
		this.playerEntity = null;

		for (Tile t : this.tiles) {
			t.dispose();
		}
		this.tiles.clear();
		this.tilesToRemove.clear();
	}

	public abstract List<AbstractDialogBox> returnAllDialogues();
}
