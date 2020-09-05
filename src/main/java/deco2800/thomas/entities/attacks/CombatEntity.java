package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.tasks.AbstractTask;

public abstract class CombatEntity extends AbstractEntity {
    private int damage;
    protected AbstractTask movementTask, combatTask;

    public CombatEntity() {
        super();
    }

    /**
     *
     * @param col
     * @param row
     * @param renderOrder
     * @param damage
     * @param faction
     */
    public CombatEntity(float col, float row, int renderOrder, int damage, EntityFaction faction) {
        super(col, row, renderOrder);
        this.damage = damage;
        setFaction(faction);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the movement task for this entity.
     * @param task AbstractTask for movement to use.
     */
    public void setMovementTask(AbstractTask task) {
        this.movementTask = task;
    }

    /**
     * Gets the reference to the current movement task for this entity.
     * @return Reference to currently in use AbstractTask for movement.
     */
    public AbstractTask getMovementTask() {
        return movementTask;
    }

    /**
     * Sets the combat task for this entity.
     * @param task AbstractTask for combat to use.
     */
    public void setCombatTask(AbstractTask task) {
        this.combatTask = task;
    }

    /**
     * Gets the reference to the current combat task for this entity.
     * @return Reference to currently in use AbstractTask for combat.
     */
    public AbstractTask getCombatTask() {
        return combatTask;
    }
}
