package deco2800.thomas.entities.Agent;

import com.google.gson.annotations.Expose;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;

import java.util.HashMap;
import java.util.List;

public abstract class AgentEntity extends AbstractEntity {
	@Expose
	protected float speed;
	private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;

	// collection of textures for different directions
	private final HashMap<Integer, String> textureDirections = new HashMap<>();
	protected final int TEXTURE_BASE = 0;  // the texture String without _left or _right
	protected final int TEXTURE_LEFT = 1;
	protected final int TEXTURE_RIGHT = 2;
	
	public AgentEntity(float col, float row, int renderOrder, float speed) {
		super(col, row, renderOrder);
		this.speed = speed;
	}

	public AgentEntity() {
		super();
	}

	/** Get the texture string with the given direction */
	public String getTextureDirection(int direction) {
		return textureDirections.get(direction);
	}

	/**
	 * Store the texture strings with different directions
	 * @param textures List of textures [base, left, right]. For example: ["orc", "orc_left", "orc_right"]
	 */
	public void setTextureDirections(List<String> textures) {
		for (int i = 0; i < textures.size(); i++) {
			this.textureDirections.put(i, textures.get(i));
		}
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
