package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public class SpeedStatus extends StatusEffect {
    private final float newSpeed;
    private final float multiplier;

    /**
     * Default Constructor for the Speed Status effect (Multiplies entities speed by )
     */
    public SpeedStatus(AgentEntity entity) {
        super(entity);
        multiplier = 0.5f;
        this.newSpeed = getAffectedEntity().getSpeed() * multiplier;
    }

    /**
     * Customer Constructor for the Speed Status effect (Deals specified damage)
     */
    public SpeedStatus(AgentEntity entity, float SpeedMultiplier) {
        super(entity);
        this.multiplier = SpeedMultiplier;
        this.newSpeed = getAffectedEntity().getSpeed() * multiplier;
    }

    /**
     * Apply Speed status
     */
    @Override
    public void applyEffect() {
        setActiveState(true);
        getAffectedEntity().setSpeed(newSpeed);
    }

    /**
     * Remove Speed status
     */
    public void removeEffect() {
        getAffectedEntity().setSpeed(getAffectedEntity().getSpeed() / multiplier);
    }

}
