package deco2800.thomas.tasks.status;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.HealthTracker;

/**
 * A status effect which inflicts damage over time to an entity.
 */
public class BurnStatus extends StatusEffect {

    // the damage inflicted after each tick
    private final int burnDamage;

    // the system time of the last damage tick
    private long timeLastTick;

    // the number of damage ticks remaining
    private int ticks;

    // whether the first tick of this effect has been applied
    private boolean applied = false;

    // the health tracker of the entity this effect applies to
//    private HealthTracker healthTracker;

    /**
     * Creates a new BurnStatus with default damage and ticks (1 dmg, 1 tick).
     *
     * @param entity The entity this status is being applied to.
     */
    public BurnStatus(Peon entity) {
        super(entity);
        timeLastTick = System.nanoTime();
        burnDamage = 1;
        ticks = 1;
//        healthTracker = getAffectedEntity().getHealthTracker();
    }

    /**
     * Creates a new BurnStatus with a set damage and number of ticks.
     *
     * @param entity The entity this status is being applied to.
     * @param burnDamage The damage inflicted by each tick.
     * @param ticks The number of damage ticks in this effect.
     */
    public BurnStatus(Peon entity, int burnDamage, int ticks) {
        super(entity);
        this.burnDamage = burnDamage;
        timeLastTick = System.nanoTime();
        this.ticks = ticks;

//        if (entity != null) {
//            healthTracker = getAffectedEntity().getHealthTracker();
//        }
    }

    /**
     * Returns whether the next damage tick should be applied.
     *
     * The first tick should be applied regardless of time.
     * All subsequent ticks should be applied 1 second after the previous.
     *
     * @return Whether the next damage tick should be applied.
     */
    public boolean ticksReady() {

        // the first tick is applied instantly
        if (!applied) {
            applied = true;
            ticks--;
            return true;
        }

        // 1 second (1 bn nanoseconds) between each tick
        long timeBetweenTicks = 1000000000;
        long newTime = System.nanoTime();

        // if it has been 1 second: decrement ticks, set a new time and return true
        if (newTime - timeLastTick >= timeBetweenTicks) {
            ticks--;
            timeLastTick = newTime;
            return true;
        }

        return false;
    }

    /**
     * Apply the status.
     */
    @Override
    public void applyEffect() {
        // we skip application if the next tick is not ready
        if (!ticksReady()) return;

        getAffectedEntity().applyDamage(burnDamage, DamageType.FIRE);
//        int health = healthTracker.getCurrentHealthValue();
//        healthTracker.setCurrentHealthValue(health - burnDamage);

        // if all ticks are done, we set to inactive
        if (ticks == 0) {
            setActiveState(false);
        }
    }
}
