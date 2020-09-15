package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public class SpeedStatus extends StatusEffect {

    private final float multiplier;
    private boolean applied = false;
    private final int ticks;
    private final long startTime;

    public SpeedStatus(AgentEntity entity, float speedMultiplier, int ticks) {
        super(entity);
        this.multiplier = speedMultiplier;
        this.ticks = ticks;
        this.startTime = System.nanoTime();
    }

    /**
     * Abstract method to be formally implemented via subclass of StatusEffect.
     */
    @Override
    public void applyEffect() {
        long secondsConversion = 1000000000;
        long currentTime = System.nanoTime();
        if (!applied) {
            getAffectedEntity().setSpeed(getAffectedEntity().getSpeed() * multiplier);
            applied = true;
        }

        if ((currentTime - startTime) > ticks * secondsConversion) {
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