package deco2800.thomas.entities.Agent;

import com.google.gson.annotations.Expose;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public abstract class AgentEntity extends AbstractEntity {
	@Expose
	protected float speed;
	private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
	protected HealthTracker health;

	// collection of textures for different directions
	private final HashMap<Integer, String> textureDirections = new HashMap<>();
	protected final int TEXTURE_BASE = 0;  // the texture String without _left or _right
	protected final int TEXTURE_LEFT = 1;
	protected final int TEXTURE_RIGHT = 2;
	
	public AgentEntity(float col, float row, int renderOrder, float speed, int health) {
		super(col, row, renderOrder);
		this.speed = speed;
		this.health = new HealthTracker(health);
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

	/**
	 * Returns the maximum health of this AgentEntity.
	 */
	public int getMaxHealth() {
		return health.getMaxHealthValue();
	}

	/**
	 * Sets the maximum health of this AgentEntity.
	 * @param newMaxHealth the new maximum health of this enemy.
	 */
	public void setMaxHealth(int newMaxHealth) {
		this.health.setMaxHealthValue(newMaxHealth);
	}

	/**
	 * Returns the current health of this AgentEntity.
	 */
	public int getCurrentHealth() {
		return health.getCurrentHealthValue();
	}

	/**
	 * Sets the current health of this AgentEntity. to be a new value.
	 * @param newHealth The new current health of this AgentEntity.
	 */
	public void setCurrentHealthValue(int newHealth) {
		health.setCurrentHealthValue(newHealth);
	}

	/**
	 * Reduces the health of this AgentEntity. by the given amount.
	 * @param damage The amount of damage to be taken by this AgentEntity.
	 */
	public void reduceHealth(int damage) {
		health.reduceHealth(damage);
	}

	/**
	 * Increases the health of this AgentEntity. by the given amount.
	 * @param regen The amount of health this AgentEntity.is to be healed by.
	 */
	public void regenerateHealth(int regen) {
		health.regenerateHealth(regen);
	}

	/**
	 * Checks if the given AgentEntity has died (health reduced to 0 or below);
	 * @return True if AgentEntity is dead, False otherwise
	 */
	public boolean isDead() {
		return this.getCurrentHealth() <= 0;
	}

	public HealthTracker getHealthTracker() {
		return this.health;
	}

	/**
	 * Defines behaviour when an agent entity dies
	 */
	public void death() {
	}
}
