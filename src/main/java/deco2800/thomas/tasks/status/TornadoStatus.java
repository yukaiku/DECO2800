package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.Peon;

public class TornadoStatus extends StatusEffect {

    // the system time of the last damage tick
    private long timeLastTick;

    // the number of damage ticks remaining
    private int ticks;

    // whether the first tick of this effect has been applied
    private boolean applied = false;

    /**
     * Creates a new StatusEffect on a designated entity.
     *
     * @param entity The designated entity.
     */
    public TornadoStatus(Peon entity) {
        super(entity);
        timeLastTick = System.nanoTime();
        ticks = 1;
    }

    public TornadoStatus(Peon entity, int ticks) {
        super(entity);
        timeLastTick = System.nanoTime();
        this.ticks = ticks;
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

    @Override
    public void applyEffect() {
        // we skip application if the next tick is not ready
        if (!ticksReady()) return;

        getAffectedEntity().setCol((float) (getAffectedEntity().getCol() + 0.1));

        System.out.println("applied");
//        int health = healthTracker.getCurrentHealthValue();
//        healthTracker.setCurrentHealthValue(health - burnDamage);

        // if all ticks are done, we set to inactive
        if (ticks == 0) {
            setActiveState(false);
        }
    }
}
