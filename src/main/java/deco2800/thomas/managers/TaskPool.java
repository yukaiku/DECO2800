package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskPool extends AbstractManager {

	private List<AbstractTask> taskPool;
 	private AbstractWorld world;
	private Random random;

	public TaskPool() {
		taskPool = new ArrayList<>();
		world = GameManager.get().getWorld();
		random = new Random();
	}

	public AbstractTask getTask(AgentEntity entity) {
		if (taskPool.isEmpty()) {
			List<Tile> tiles = world.getTiles();
			if (tiles.isEmpty()) {
				// There are no tiles
				return null;
			}
			Tile destination = tiles.get(random.nextInt(tiles.size()));
			return new MovementTask(entity, destination.getCoordinates());
		}
		return taskPool.remove(0);
	}
}
