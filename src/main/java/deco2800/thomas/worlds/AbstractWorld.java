package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.*;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level items.
 */
public abstract class AbstractWorld {

    protected List<AbstractEntity> entities = new CopyOnWriteArrayList<>();
    protected AgentEntity playerEntity;

    protected int width;
    protected int length;

    protected CopyOnWriteArrayList<Tile> tiles;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected AbstractWorld() {
    	tiles = new CopyOnWriteArrayList<Tile>();

    	generateWorld();
    	generateNeighbours();
    	generateTileIndices();
    }
    
    
    protected abstract void generateWorld();


    /** Generates neighbours for tiles on a world; assigns the sides needed. */
    public void generateNeighbours() {
    // Multiply coords by 2 to remove floats.
    	Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<Integer, Map<Integer, Tile>>();
		Map<Integer, Tile> columnMap;
		for(Tile tile : tiles) {
			columnMap = tileMap.getOrDefault((int)tile.getCol(), new HashMap<Integer, Tile>());
			columnMap.put((int) (tile.getRow()), tile);
			tileMap.put((int) (tile.getCol()), columnMap);
		}
		
		for(Tile tile : tiles) {
			int col = (int) (tile.getCol());
			int row = (int) (tile.getRow());
			System.out.println("tile coords were: " + col + " " + row);
			
			// West
			if(tileMap.containsKey(col - 1)) {
			    //System.out.println("added west");
                //System.out.println("West was " + tileMap.get(col - 1).get(row));
			    tile.addNeighbour(Tile.WEST, tileMap.get(col - 1).get(row));
			}
			
			// Central
			if(tileMap.containsKey(col)) {
				// North
				if (tileMap.get(col).containsKey(row + 1)) {
                    //System.out.println("North was " + tileMap.get(col).get(row + 1));
					tile.addNeighbour(Tile.NORTH, tileMap.get(col).get(row + 1));
				}
				
				// South
				if (tileMap.get(col).containsKey(row - 1)) {
                    //System.out.println("South was " + tileMap.get(col).get(row - 1));
					tile.addNeighbour(Tile.SOUTH,tileMap.get(col).get(row - 1));
				}
			}
			
			// East
			if(tileMap.containsKey(col + 1)) {
				// East
                //System.out.println("East was " + tileMap.get(col + 1).get(row));
                tile.addNeighbour(Tile.EAST,tileMap.get(col + 1).get(row));

			}
		}
    }
    
    private void generateTileIndices() {
    	for(Tile tile : tiles) {
    		tile.calculateIndex();
    	}
    }
    
    /**
     * Returns a list of entities in this world.
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        return new CopyOnWriteArrayList<>(this.entities);
    }
    
    /**
     *  Returns a list of entities in this world, ordered by their render level .
     *  @return all entities in the world 
     */
    public List<AbstractEntity> getSortedEntities(){
		List<AbstractEntity> e = new CopyOnWriteArrayList<>(this.entities);
    	Collections.sort(e);
		return e;
    }


    /**
     *  Returns a list of entities in this world, ordered by their render level.
     *  @return all entities in the world 
     */
    public List<AgentEntity> getSortedAgentEntities(){
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
     * @param entity the entity to add
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the world.
     * @param entity the entity to remove
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

	public void setEntities(List<AbstractEntity> entities) {
		this.entities = entities;
	}

    public List<Tile> getTileMap() {
        return tiles;
    }
    
    
    public Tile getTile(float col, float row) {
    	return getTile(new SquareVector(col,row));
    }
    
    public Tile getTile(SquareVector position) {
        for (Tile t : tiles) {
        	if (t.getCoordinates().equals(position)) {
        		return t;
			}
		}
		return null;
    }
    
    public Tile getTile(int index) {
        for (Tile t : tiles) {
        	if (t.getTileID() == index) {
        		return t;
			}
		}
		return null;
    }

    public void setTileMap(CopyOnWriteArrayList<Tile> tileMap) {
        this.tiles = tileMap;
    }
    
    //TODO ADDRESS THIS..?
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

    //TODO ADDRESS THIS..?
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

    public void onTick(long i) {
        for (AbstractEntity e : entitiesToDelete) {
            entities.remove(e);
        }

        for (Tile t : tilesToDelete) {
            tiles.remove(t);
        }
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

    public void queueEntitiesForDelete(List<AbstractEntity> entities) {
        entitiesToDelete.addAll(entities);
    }

    public void queueTilesForDelete(List<Tile> tiles) {
        tilesToDelete.addAll(tiles);
    }

    public AgentEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(AgentEntity playerEntity) {
        this.playerEntity = playerEntity;
    }
}
