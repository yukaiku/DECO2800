package deco2800.thomas.tasks.movement;

import java.util.List;


import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PathFindingService;
import deco2800.thomas.tasks.AbstractTask;
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

	AgentEntity entity;
	SquareVector destination;

	private List<Tile> path;

	public MovementTask(AgentEntity entity, SquareVector destination) {
		super(entity);

		this.entity = entity;
		this.destination = destination;
		this.complete = false;    //path == null || path.isEmpty();
	}

	@Override
	public void onTick(long tick) {
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
					path.remove(0);
				}
			}
		} else if (computingPath) {
			// Change sprite to show waiting??

		} else {
			// Ask for a path.
			computingPath = true;
			GameManager.get().getManager(PathFindingService.class).addPath(this);
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
}
