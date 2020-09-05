package deco2800.thomas.tasks;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.BFSPathfinder;
import deco2800.thomas.util.Pathfinder;

public class AttackPathFindingServiceThread implements Runnable {
	private Thread thread;
	RangedAttackTask movementTask;

	public AttackPathFindingServiceThread(RangedAttackTask movementTask) {
		this.movementTask = movementTask;
	}

	public void run() {
		Pathfinder pathfinder = new BFSPathfinder();
		movementTask.setPath(pathfinder.pathfind(GameManager.get().getWorld(),
												 movementTask.entity.getPosition(),
												 movementTask.destination));
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public boolean isAlive() {
		return thread.isAlive();
	}
}
