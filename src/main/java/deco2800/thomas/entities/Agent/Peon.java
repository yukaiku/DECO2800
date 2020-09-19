package deco2800.thomas.entities.Agent;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;

public class Peon extends AgentEntity implements Tickable {
	private transient AbstractTask movementTask;
	private transient AbstractTask combatTask;

	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
		this.save = true;
	}

	/**
	 * Peon constructor
     */
	public Peon(float row, float col, float speed, int health) {
		super(row, col, RenderConstants.PEON_RENDER, speed, health);
		this.setTexture("spacman_ded");
	}

	@Override
	public void onTick(long i) {
		if (movementTask != null && movementTask.isAlive()) {
			if (movementTask.isComplete()) {
				this.movementTask = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
			}
			movementTask.onTick(i);
		} else {
			movementTask = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
		}

		// Update combat task
		if (combatTask != null) {

			if (combatTask.isComplete()) {
				combatTask = null;
			}
			combatTask.onTick(i);
		} else {
			// Do nothing
		}
	}

	protected void setMovementTask(AbstractTask movementTask) {
		this.movementTask = movementTask;
	}

	public AbstractTask getMovementTask() {
		return movementTask;
	}

	public AbstractTask getCombatTask() {
		return combatTask;
	}

	public void setCombatTask(AbstractTask combatTask) {
		this.combatTask = combatTask;
	}
}
