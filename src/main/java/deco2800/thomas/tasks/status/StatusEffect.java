package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.agent.Peon;

/**
 * An abstract class for a status effect, which can be generalised to apply
 * effects such as damage over time, healing or slowing.
 */
public abstract class StatusEffect {

    // whether the effect is active, always true upon instantiation
    private boolean active = true;

    // the entity that this effect is applied to
    private final Peon affectedEntity;

    /**
     * Creates a new StatusEffect on a designated entity.
     *
     * @param entity The designated entity.
     */
    public StatusEffect(Peon entity) {
        affectedEntity = entity;
        if (entity == null) {
            active = false;
        }
    }

    /** Returns the state of the status
     *
     * @return Whether status is active.
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * Sets the state of the status. Once set to inactive, this instance is removed
     * from the StatusEffectManager on the next tick.
     *
     * @param active The new desired active state of this effect.
     */
    public void setActiveState(boolean active){
        this.active = active;
    }

    /**
     * Returns the entity tied to this status effect instance.
     *
     * @return The entity being affected by this status.
     */
    public Peon getAffectedEntity() {
        return this.affectedEntity;
    }

    /**
     * Applies the effect of this StatusEffect.
     */
    public abstract void applyEffect();
}
