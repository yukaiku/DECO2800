package deco2800.thomas.entities.agent;

import com.google.gson.annotations.Expose;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;

public abstract class AgentEntity extends AbstractEntity {
	@Expose
	protected float speed;
	private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
	
	public AgentEntity(float col, float row, int renderOrder, float speed) {
		super(col, row, renderOrder);
		this.speed = speed;
	}

	public AgentEntity() {
		super();
	}

	public void moveTowards(SquareVector destination) {
		position.moveToward(destination, speed);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public MovementTask.Direction getMovingDirection() {
		return movingDirection;
	}

	public void setMovingDirection(MovementTask.Direction movingDirection) {
		this.movingDirection = movingDirection;
	}
}
