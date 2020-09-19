package deco2800.thomas.tasks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.AbstractEntity;

public abstract class AbstractTask implements Tickable {

	protected AbstractEntity entity;


	public AbstractTask(AbstractEntity entity) {
		this.entity = entity;
	}

	public abstract boolean isComplete();

	public abstract boolean isAlive();

	public void stopTask() {
	}
}
