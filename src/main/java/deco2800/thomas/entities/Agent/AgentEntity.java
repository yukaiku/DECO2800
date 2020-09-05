package deco2800.thomas.entities.Agent;

import com.google.gson.annotations.Expose;


import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public abstract class AgentEntity extends AbstractEntity {
	@Expose
	protected float speed;
	private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
	protected HealthTracker health;


	public AgentEntity(float col, float row, int renderOrder, float speed, int health) {
		super(col, row, renderOrder);
		this.speed = speed;
		this.health = new HealthTracker(health);
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
