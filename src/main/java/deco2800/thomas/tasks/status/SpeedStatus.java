package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.agent.Peon;

/**
 * A generalisation of the StatusEffect class which applied a speed
 * change to an entity, based on a multiplier. This can be used to
 * speed up or slow down an entity for a given time period.
 */
public class SpeedStatus extends StatusEffect {

    // the speed multiplier for this effect
    private final float multiplier;

    // whether the effect has been applied yet
    private boolean applied = false;

    // the time length (in seconds) of this effect
    private final int timeLength;

    // the System time at the first application of this effect
    private long startTime;

    /**
     * Creates a new SpeedStatus instance with a desired entity, speed multiplier
     * and time length.
     *
     * @param entity The entity this effect applied to.
     * @param speedMultiplier The speed multiplier of this effect.
     * @param timeLength The time (in seconds) of this effect.
     */
    public SpeedStatus(Peon entity, float speedMultiplier, int timeLength) {
        super(entity);
        this.multiplier = speedMultiplier;
        this.timeLength = timeLength;
    }

    /**
     * Applies the speed effect if it has not already been applied. Calls to remove the
     * effect if it has been active for longer than its designated time length.
     */
    @Override
    public void applyEffect() {
        if (!applied) {
            getAffectedEntity().setSpeed(getAffectedEntity().getSpeed() * multiplier);
            applied = true;
            this.startTime = System.nanoTime();
        }

        long nsConversion = 1000000000;
        long currentTime = System.nanoTime();
        if ((currentTime - startTime) > timeLength * nsConversion) {
            removeEffect();
            setActiveState(false);
        }
    }

    /**
     * Removes the speed status from the entity.
     */
    public void removeEffect() {
        getAffectedEntity().setSpeed(getAffectedEntity().getSpeed() / multiplier);
    }
}