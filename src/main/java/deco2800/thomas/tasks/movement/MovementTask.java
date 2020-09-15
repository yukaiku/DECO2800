package deco2800.thomas.tasks.movement;

import java.util.List;


import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
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


public class MovementTask extends AbstractTask {
	public enum Direction{
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
		if (gameManager == null) gameManager = GameManager.get();

		if (entity instanceof PlayerPeon) {
			this.normalMovement();
		} else {
			this.autoMovement();
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
			checkForTileStatus(destination);
		}

		if (entity.getPosition().isCloseEnoughToBeTheSame(destination)) {
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
	 * Checks if the tile at a position has an associated status effect.
	 * If it does, a new status effect is added to the StatusEffectManager for the entity.
	 *
	 * @param position - Square Vector of upcoming position of the entity.
	 */
	private void checkForTileStatus(SquareVector position) {
		// we don't want to duplicate effects from the same tile
		if (currentPos.tileEquals(entity.getPosition())) return;
		setCurrentPos();

		// check if the tile has an effect, apply the effect if so
		Tile tile = gameManager.getWorld().getTile(position);
		if (tile != null && tile.hasStatusEffect()) {
			switch(tile.getType()) {
				// burn tiles damage over time
				case "BurnTile":
					gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus(entity, 5, 5));
				break;

				// quicksand damages over time and slows
				case "Quicksand":
					gameManager.getManager(StatusEffectManager.class).addStatus(new SpeedStatus(entity, 0.25f, 3));
					gameManager.getManager(StatusEffectManager.class).addStatus(new QuicksandBurnStatus(entity, 5, 100, position));
				break;

				// neighbours of cactus plants damage once when the player first arrives
				case "CactusNeighbour":
					gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus(entity, 10, 1));
				break;

				// ice tiles speed up the player temporarily
				case "TundraIceTile":
				gameManager.getManager(StatusEffectManager.class).addStatus(new SpeedStatus(entity, 1.1f, 1));
				break;

				// neighbours of tundra fire entities inflict damage over time
				case "TundraFireTile":
				gameManager.getManager(StatusEffectManager.class).addStatus(new BurnStatus(entity, 5, 2));
				break;

			}
		}
	}
}
