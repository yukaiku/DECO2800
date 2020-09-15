package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.util.SquareVector;

public class SpeedStatus extends StatusEffect {
    private final float newSpeed;
    private final float multiplier;
    private boolean applied = false;
    private SquareVector position;

    /**
     * Customer Constructor for the Speed Status effect (Deals specified damage)
     */
    public SpeedStatus(AgentEntity entity, float SpeedMultiplier, SquareVector position) {
        super(entity);
        this.multiplier = SpeedMultiplier;
        this.newSpeed = getAffectedEntity().getSpeed() * multiplier;
        this.position = position;
    }

    /**
     * Apply Speed status
     */
    @Override
    public void applyEffect() {
        if (!applied) {
            getAffectedEntity().setSpeed(newSpeed);
            applied = true;

        }
        if (!getAffectedEntity().getPosition().tileEquals(position)) {
            removeEffect();
            setActiveState(false);
        }
    }

    /**
     * Remove Speed status
     */
    public void removeEffect() {
        getAffectedEntity().setSpeed(getAffectedEntity().getSpeed() / multiplier);
    }

}
