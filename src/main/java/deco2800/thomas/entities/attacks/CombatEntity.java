package deco2800.thomas.entities.attacks;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.tasks.AbstractTask;

public abstract class CombatEntity extends AbstractEntity {
    private int damage;
    private DamageType damageType;
    protected AbstractTask movementTask;
    protected AbstractTask combatTask;

    /**
     * Creates a new instance of a CombatEntity.
     * @param col X position
     * @param row Y position
     * @param renderOrder Render constant
     * @param damage Damage to deal
     * @param faction EntityFaction of entity
     * @param damageType DamageType to apply
     */
    public CombatEntity(float col, float row, int renderOrder, int damage, EntityFaction faction, DamageType damageType) {
        super(col, row, renderOrder);
        this.damage = damage;
        this.damageType = damageType;
        setFaction(faction);
    }

    /**
     * Returns the base damage this entity should deal.
     * @return Damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the base damage this entity should deal.
     * @param damage Damage value.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Sets the base damage type this entity should deal.
     * @param damageType Damage type from DamageType enum.
     */
    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    /**
     * Returns the base damage type this entity should deal.
     * @return Damage type from DamageType enum.
     */
    public DamageType getDamageType() {
        return this.damageType;
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
