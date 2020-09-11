package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public class SpeedStatus extends StatusEffect{
    private float originalSpeed;
    private float newSpeed;
    private float defaultMultiplier = 0.5f;

    /**
     * Default Constructor for the Speed Status effect (Multiplies entities speed by )
     */
    public SpeedStatus(AgentEntity entity) {
        super(entity);
        originalSpeed = getAffectedEntity().getSpeed();
        this.newSpeed = originalSpeed * defaultMultiplier;
    }

    /**
     * Customer Constructor for the Speed Status effect (Deals specified damage)
     */
    public SpeedStatus(AgentEntity entity, float SpeedMultiplier) {
        super(entity);
        originalSpeed = getAffectedEntity().getSpeed();
        this.newSpeed = originalSpeed * SpeedMultiplier;
    }

    /**
     * Apply Speed status
     */
    @Override
    public void applyEffect() {
        getAffectedEntity().setSpeed(newSpeed);
    }
    /**
     * Remove Speed status
     */
    @Override
    public void removeEffect() {
        getAffectedEntity().setSpeed(originalSpeed);
    }

}
