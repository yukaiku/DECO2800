package deco2800.thomas.tasks;

import java.util.List;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PathFindingService;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;

public class MovementTask extends AbstractTask{
	
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
		
		if(path != null) {
			// We have a path.
			if(path.isEmpty()) {
				complete = true;
			} else {
				entity.moveTowards(path.get(0).getCoordinates());
				// This is a bit of a hack.
				if(entity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
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
