package deco2800.thomas.tasks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.AgentEntity;

public abstract class AbstractTask implements Tickable {
	
	protected AgentEntity entity;
	
	
	public AbstractTask(AgentEntity entity) {
		this.entity = entity;
	}
	
	public abstract boolean isComplete();

	public abstract boolean isAlive();

}
