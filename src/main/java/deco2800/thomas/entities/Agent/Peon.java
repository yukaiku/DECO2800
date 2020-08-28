package deco2800.thomas.entities.Agent;

import deco2800.thomas.Tickable;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.entities.RenderConstants;

public class Peon extends AgentEntity implements Tickable {
	private transient AbstractTask task;

	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
	}

	/**
	 * Peon constructor
     */
	public Peon(float row, float col, float speed) {
		super(row, col, RenderConstants.PEON_RENDER, speed);
		this.setTexture("spacman_ded");
	}

	@Override
	public void onTick(long i) {
		if(task != null && task.isAlive()) {
			if(task.isComplete()) {
				this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
			}
			task.onTick(i);
		} else {
			task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
		}
	}

	protected void setTask(AbstractTask task) {
		this.task = task;
	}
	
	public AbstractTask getTask() {
		return task;
	}
}
