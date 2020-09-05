package deco2800.thomas.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.tasks.combat.RangedAttackTask;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

public class TaskPool extends AbstractManager {

	private List<AbstractTask> taskPool;
	private List<RangedAttackTask> combatPool;
 	private AbstractWorld world;
	private Random random;

	public TaskPool() {
		taskPool = new ArrayList<AbstractTask>();
		combatPool = new ArrayList<RangedAttackTask>();
		world = GameManager.get().getWorld();
		random = new Random();
	}

	public AbstractTask getTask(AgentEntity entity) {
		if (taskPool.isEmpty()) {
			List<Tile> tiles = world.getTiles();
			if (tiles.size() == 0) {
				// There are no tiles
				return null;
			}
			Tile destination = tiles.get(random.nextInt(tiles.size()));
			return new MovementTask(entity, destination.getCoordinates());
		}
		return taskPool.remove(0);
	}
}
