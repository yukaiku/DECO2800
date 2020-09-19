package deco2800.thomas.entities.agent;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.StatusEffect;

import java.util.ArrayList;

public class Peon extends AgentEntity implements Tickable {
	private AbstractTask movementTask;
	private AbstractTask combatTask;
	private ArrayList<StatusEffect> effects;
	private float armour;
	private float damage;

	protected boolean isAttacked = false;
	protected int isAttackedCoolDown = 0;

	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
		this.save = true;
		this.effects = new ArrayList<StatusEffect>();
	}

	/**
	 * Peon constructor
     */
	public Peon(float row, float col, float speed, int health) {
		super(row, col, RenderConstants.PEON_RENDER, speed, health);
		this.setTexture("spacman_ded");
		this.effects = new ArrayList<StatusEffect>();
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
		}

		// isAttacked animation
		if (isAttacked && --isAttackedCoolDown < 0) {
			isAttacked = false;
		}
	}

	@Override
	public void reduceHealth(int damage) {
		health.reduceHealth(damage);
		isAttacked = true;
		isAttackedCoolDown = 5;
	}

	public boolean isAttacked() {
		return this.isAttacked;
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

	/**
	 * Returns the list of active status effects on the Peon
	 *
	 * @return List of active effects
	 */
	public ArrayList<StatusEffect> getEffects() {
		return effects;
	}

	/**
	 * Set the list of active status effects on the Peon
	 *
	 * @param effects List of StatusEffects
	 */
	public void setEffects(ArrayList<StatusEffect> effects) {
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

}
