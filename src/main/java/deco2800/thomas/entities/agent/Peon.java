package deco2800.thomas.entities.agent;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.StatusEffect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A peon represents an active entity within the game. For example, the player,
 * an enemy, a boss or an NPC.
 */
public class Peon extends AgentEntity implements Tickable {
	/* Tasks for this peon */
	private AbstractTask movementTask;
	protected AbstractTask combatTask;
	/* Status effects on this peon */
	private CopyOnWriteArrayList<StatusEffect> effects;
	/* Combat stats for this entity */
	private static final float armourConstant = 1000f; // Changes effectiveness of armour values, higher = less effective
	private final HealthTracker health;
	private float armour; // Reduces incoming damage
	private float damage; // Base outgoing damage value
	private DamageType vulnerability; // Peon takes extra damage from this damage type

	/* Combat status of this entity */
	protected boolean isAttacked = false;
	protected int isAttackedCoolDown = 0;

	/**
	 * Creates a generic peon class.
	 */
	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
		this.save = true;
		this.effects = new CopyOnWriteArrayList<>();
		this.damage = 10;
		this.armour = armourConstant; // No damage reduction
		this.vulnerability = DamageType.NONE;
		this.health = new HealthTracker(100);
	}

	/**
	 * Peon constructor
     */
	public Peon(float row, float col, float speed, int health) {
		super(row, col, RenderConstants.PEON_RENDER, speed);
		this.setTexture("spacman_ded");
		this.effects = new CopyOnWriteArrayList<>();
		this.damage = 10;
		this.armour = armourConstant; // No damage reduction
		this.vulnerability = DamageType.NONE;
		this.health = new HealthTracker(health);
	}

	/**
	 * Performs a single tick for the peon.
	 * @param i Ticks since game start
	 */
	@Override
	public void onTick(long i) {	
		// Update movement task
		if (movementTask != null && movementTask.isAlive()) {
			movementTask.onTick(i);
			if (movementTask.isComplete()) {
				this.movementTask = null;
			}
		} else {
			movementTask = null;
		}

		// Update combat task
		if (combatTask != null) {
			combatTask.onTick(i);
			if (combatTask.isComplete()) {
				combatTask = null;
			}
		}

		// isAttacked animation
		if (isAttacked && --isAttackedCoolDown < 0) {
			isAttacked = false;
		}

		// Update effects
		for (StatusEffect effect : this.effects) {
			if (effect.getActive()) {
				effect.applyEffect();
			} else {
				removeEffect(effect);
			}
		}
	}

	/**
	 * Applies damage to the peon according to damage calculation algorithm.
	 * @param damage The amount of damage to be taken by this AgentEntity.
	 * @param damageType The type of damage to apply from DamageType enum.
	 * @returns Damage dealt.
	 */
	public int applyDamage(int damage, DamageType damageType) {
		int damageApplied = (int)(damage * (armourConstant / getArmour()));
		if (damageType == vulnerability && vulnerability != DamageType.NONE) {
			damageApplied *= 1.5f;
		}
		health.reduceHealth(damageApplied);
		isAttacked = true;
		isAttackedCoolDown = 5;

		return damageApplied;
	}

	/**
	 * Returns whether this peon was recently attacked.
	 */
	public boolean isAttacked() {
		return this.isAttacked;
	}

	/**
	 * Sets this peon's movement task.
	 * @param movementTask New movement task to use.
	 */
	protected void setMovementTask(AbstractTask movementTask) {
		this.movementTask = movementTask;
	}

	/**
	 * Returns this peon's movement task.
	 * @return Current movement task or null if none set.
	 */
	public AbstractTask getMovementTask() {
		return movementTask;
	}

	/**
	 * Returns this peon's combat task.
	 * @return Current combat task or null if none set.
	 */
	public AbstractTask getCombatTask() {
		return combatTask;
	}

	/**
	 * Sets this peon's combat task. (Will override any in progress).
	 * @param combatTask New combat task to use.
	 */
	public void setCombatTask(AbstractTask combatTask) {
		this.combatTask = combatTask;
	}

	/**
	 * Returns the list of active status effects on the Peon
	 *
	 * @return List of active effects
	 */
	public CopyOnWriteArrayList<StatusEffect> getEffects() {
		return effects;
	}

	/**
	 * Set the list of active status effects on the Peon
	 *
	 * @param effects List of StatusEffects
	 */
	public void setEffects(CopyOnWriteArrayList<StatusEffect> effects) {
		this.effects = effects;
	}

	/**
	 * Remove a status effect from the list of active effects on the Peon
	 *
	 * @param effect A status effect
	 * @return Returns true if removed, returns false if effect is not in the list
	 */
	public boolean removeEffect(StatusEffect effect) {
		return this.effects.remove(effect);
	}

	/**
	 * Add a status effect to the list of active effects on the Peon
	 *
	 * @param effect A status effect
	 */
	public void addEffect(StatusEffect effect) {
		this.effects.add(effect);
	}

	/**
	 * Sets the type of damage this peon is vulnerable to. (Takes +50% damage).
	 * @param damageType Type of damage from DamageType.
	 */
	public void setVulnerability(DamageType damageType) {
		vulnerability = damageType;
	}

	/**
	 * Returns the type of damage this peon is vulnerable to. (Takes +50% damage).
	 * @return DamageType this peon is vulnerable to.
	 */
	public DamageType getVulnerability() {
		return vulnerability;
	}

	/**
	 * Returns the current armour value for the Peon
	 *
	 * @return The armour of the Peon
	 */
	public float getArmour() {
		return armour;
	}

	/**
	 * Sets the current armour value for the Peon
	 *
	 * @param armour The new armour value for the Peon
	 */
	public void setArmour(float armour) {
		this.armour = armour;
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
		// Currently no implementation as the Peon is treated as an AbstractClass.
	}
}
