package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public abstract class StatusEffect {
    private boolean active = true;
    private AgentEntity affectedEntity;

    public StatusEffect(AgentEntity entity) {
        affectedEntity = entity;
    }

    /** Returns the state of the status
     *
     * @return boolean - whether status is active
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * Sets the state of the status, once set inactive, the instance is usually
     * set to null on the next tick
     * @param active
     */
    public void setActiveState(boolean active){
        this.active = active;
    }

    /**
     * Returns the current entity tied to this instance of status effect.
     * @return
     */
    public AgentEntity getAffectedEntity() {
        return this.affectedEntity;
    }

    /**
     * Abstract method to be formally implemented via subclass of StatusEffect.
     */
    public abstract void applyEffect();

    public void removeEffect() {}
}
