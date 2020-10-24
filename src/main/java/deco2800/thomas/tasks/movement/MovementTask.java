package deco2800.thomas.tasks.movement;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PathFindingService;
import deco2800.thomas.managers.StatusEffectManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.BurnStatus;
import deco2800.thomas.tasks.status.QuicksandBurnStatus;
import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

import java.util.List;


public class MovementTask extends AbstractTask {
    public enum Direction {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private boolean complete;

    private boolean computingPath = false;
    private boolean taskAlive = true;
    private GameManager gameManager = null;
    public AgentEntity entity;
    public SquareVector destination;
    private final SquareVector currentPos;

    private List<Tile> path;

    public MovementTask(AgentEntity entity, SquareVector destination) {
        super(entity);

        this.entity = entity;
        this.destination = destination;
        this.complete = false;    //path == null || path.isEmpty();
        this.currentPos = new SquareVector();
        setCurrentPos();
    }

    @Override
    public void onTick(long tick) {
        if (!complete) {
            if (gameManager == null) gameManager = GameManager.get();
            if (entity instanceof PlayerPeon) {
                this.normalMovement();
            } else {
                this.autoMovement();
            }
        }
    }

    /**
     * With the main entity, the MovementTask will use this method
     * to do the movement. Firstly, the method checks weather the destination
     * is able to walk or not then it make a moveTowards to that direction. The
     * method also check the movingDirection for continuous moving in case the player
     * hold the key after he press it. In addition, the method makes sure that
     * the main entity have to completely in the destination before they start the
     * next move.
     */
    private void normalMovement() {
        if (!WorldUtil.isWalkable(destination.getCol(), destination.getRow())) {
            this.complete = true;
            return;
        }
        entity.moveTowards(destination);

		// if we have moved to the new tile, check for statuses
		if (entity.getPosition().tileEquals(destination)) {
			checkForValidPortal(destination);
			checkForTileStatus(destination);
			checkForTeleportTile(destination);
			checkForTrapTile(destination);
			checkForRewardTile(destination);
			checkObtainedOrb(destination);
		}

        if (entity.getPosition().isCloseEnoughToBeTheSame(destination)) {
            if (!this.updateMovingDirection((PlayerPeon) this.entity)) {
                return;
            }
            switch (entity.getMovingDirection()) {
                case UP:
                    destination.setRow(destination.getRow() + 1f);
                    break;
                case DOWN:
                    destination.setRow(destination.getRow() - 1f);
                    break;
                case LEFT:
                    destination.setCol(destination.getCol() - 1f);
                    break;
                case RIGHT:
                    destination.setCol(destination.getCol() + 1f);
                    break;
                default:
                    this.complete = true;
                    break;
            }
        }
    }

    private boolean updateMovingDirection(PlayerPeon entity) {
        entity.setMovingDirection(Direction.NONE);
        if (!entity.getMovementStack().empty()) {
            Direction movingDirection = entity.getMovementStack().peek();
            if (entity.isDirectionKeyActive(movingDirection)) {
                entity.setMovingDirection(movingDirection);
            } else {
                entity.setCurrentState(PlayerPeon.State.IDLE);
                entity.getMovementStack().pop();
                return false;
            }
        }
        return true;
    }

    private void autoMovement() {
        if (path != null) {
            // We have a path.
            if (path.isEmpty()) {
                complete = true;
            } else {
                entity.moveTowards(path.get(0).getCoordinates());
                // This is a bit of a hack.
                if (entity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
                    checkForTileStatus(path.get(0).getCoordinates());
                    path.remove(0);
                }
            }
        } else if (computingPath) {
            // Change sprite to show waiting??

        } else {
            // Ask for a path.
            computingPath = true;
            gameManager.getManager(PathFindingService.class).addPath(this);
        }
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    public void setPath(List<Tile> path) {
        if (path == null) {
            taskAlive = false;
        }
        this.path = path;
        computingPath = false;
    }

    public List<Tile> getPath() {
        return path;
    }

    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    /**
     * Sets the current position of this movement task to the entity's current position.
     */
    private void setCurrentPos() {
        currentPos.setCol(entity.getPosition().getCol());
        currentPos.setRow(entity.getPosition().getRow());
    }

    /**
     * End the current task
     */
    @Override
    public void stopTask() {
        complete = true;
    }

    /**
     * Checks if the tile at a position has an associated status effect.
     * If it does, a new status effect is added to the StatusEffectManager for the entity.
     *
     * @param position - Square Vector of upcoming position of the entity.
     */
    private void checkForTileStatus(SquareVector position) {
        // we don't want to duplicate effects from the same tile
        if (currentPos.tileEquals(entity.getPosition())) {
            return;
        }
        // Bosses and enemies should not die to their own environmental effects
        if (!(entity instanceof PlayerPeon || entity.getObjectName().equals("ImmuneOrc"))) {
            return;
        }
        setCurrentPos();

        // check if the tile has an effect, apply the effect if so
        Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.hasStatusEffect() && entity instanceof Peon) {
            switch (tile.getType()) {
                // burn tiles damage over time
                case "BurnTile":
                    gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus((Peon) entity, 5, 5));
                    break;

                // quicksand damages over time and slows
                case "Quicksand":
                    if (entity.getObjectName().equals("ImmuneOrc")) {
                        gameManager.getManager(StatusEffectManager.class).addStatus(new QuicksandBurnStatus((Peon) entity, 5, 100, position, DamageType.NOT_IMMUNE));
                    } else {
                        gameManager.getManager(StatusEffectManager.class).addStatus(new QuicksandBurnStatus((Peon) entity, 5, 100, position));
                    }
                    gameManager.getManager(StatusEffectManager.class).addStatus(new SpeedStatus((Peon) entity, 0.25f, 3));
                    break;

                // neighbours of cactus plants damage once when the player first arrives
                case "CactusNeighbour":
                    if (entity.getObjectName().equals("ImmuneOrc")) {
                        gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus((Peon) entity, 10, 1, DamageType.NOT_IMMUNE));
                    } else {
                        gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus((Peon) entity, 10, 1));
                    }
                    break;

                // ice tiles speed up the player temporarily
                case "TundraIceTile":
                    gameManager.getManager(StatusEffectManager.class).addStatus(new SpeedStatus((Peon) entity, 1.1f, 1));
                    break;

                // neighbours of tundra fire entities inflict damage over time
                case "TundraFireTile":
                    gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus((Peon) entity, 5, 2));
                    break;

            }
        }
    }

    /**
     * Checks if a portal is at the new position or if a portal is below the new
     * position (Portal Spans across two tiles) & teleports the play to the
     * zones respective dungeon.
     *
     * @param position - Square Vector of upcoming position of the entity.
     */
    private void checkForValidPortal(SquareVector position) {
        // get the next tile
        Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.getParent() instanceof Portal) {
            Portal portal = (Portal) tile.getParent();
            String type = portal.getObjectName();
            gameManager.enterDungeon(type);
        }
    }

    /**
     * Checks whether the tile at the new position is a teleport tile &
     * teleports the player to the tile's respective teleport coordinates.
     *
     * @param position - Square Vector of upcoming position of the entity.
     */
    private void checkForTeleportTile(SquareVector position) {
        // get the next tile
        Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.isTeleportTile()) {
            path = null;

            //Remove Teleport trigger Entity HERE
            float newCol = tile.getTeleportCol();
            float newRow = tile.getTeleportRow();

            destination.setCol(newCol);
            destination.setRow(newRow);
            gameManager.getWorld().getPlayerEntity().setPosition(newCol, newRow, 1);
        }
    }

	/**
	 * Checking if the player has obtained the Orb of the current world then moving the player
	 * to the next world
	 *
	 * @param position
	 */
	private void checkObtainedOrb(SquareVector position) {
		Orb orbEntity = gameManager.getWorld().getOrbEntity();
		if (orbEntity != null) {
			if (position.equals(orbEntity.getPosition())) {
				QuestTracker.increaseOrbs(orbEntity);
				if(QuestTracker.orbTracker().size() != 4){
					gameManager.getWorld().removeEntity(gameManager.getWorld().getPlayerEntity());
					GameManager.get().setNextWorld();
				}
			}
		}
	}

	/**
	 * Checks whether the tile at the new position is a trap tile &
	 * initiates the tile's respective trap should there be one.
	 *
	 * @param position - Square Vector of upcoming position of the entity.
	 */
	private void checkForTrapTile(SquareVector position) {
		// get the next tile
		Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.isTrapTile() && !tile.getTrapActivated()) {

            //Remove Trap Entity HERE
            gameManager.getWorld().removeEntity(tile.getParent());
            tile.setParent(null);

            tile.setTrapActivated(true);
            gameManager.getWorld().activateTrapTile(tile);
        }
    }

    /**
     * Checks whether the tile at the new position is a reward tile &
     * initiates the tile's respective reward should there be one.
     *
     * @param position - Square Vector of upcoming position of the entity.
     */
    private void checkForRewardTile(SquareVector position) {
        // get the next tile
        Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.isRewardTile() && !tile.getRewardActivated()) {

            //Remove Reward Entity HERE
            gameManager.getWorld().removeEntity(tile.getParent());
            tile.setParent(null);

			//Update trap to be triggered & activate outcome
			tile.setRewardActivated(true);
			gameManager.getWorld().activateRewardTile(tile);
		}
	}

}
