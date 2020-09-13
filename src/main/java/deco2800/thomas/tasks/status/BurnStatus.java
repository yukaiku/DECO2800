package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.HealthTracker;

public class BurnStatus extends StatusEffect {
    private final int burnDamage;
    private long timeLastTick;
    private int ticks;
    private boolean applied = false;
    private final HealthTracker healthTracker;

    /**
     * Default Constructor for the Burn Status effect (Deals 5 damage)
     */
    public BurnStatus(AgentEntity entity) {
        super(entity);
        timeLastTick = System.nanoTime();
        burnDamage = 1;
        ticks = 1;
        healthTracker = getAffectedEntity().getHealthTracker();
    }

    /**
     * Customer Constructor for the Burn Status effect (Deals specified damage)
     */
    public BurnStatus(AgentEntity entity, int burnDamage, int ticks) {
        super(entity);
        this.burnDamage = burnDamage;
        timeLastTick = System.nanoTime();
        this.ticks = ticks;
        healthTracker = getAffectedEntity().getHealthTracker();
    }

    public boolean ticksReady() {
        if (!applied) {
            applied = true;
            return true;
        }
        long newTime = System.nanoTime();
        boolean ret;
        if (newTime - timeLastTick >= 1000000000) {
            ticks--;
            ret = true;
            timeLastTick = newTime;
        } else {
            ret = false;
        }

        return ret;
    }

    /**
     * Apply Burn status
     */
    @Override
    public void applyEffect() {
        if (!ticksReady()) return;

        int health = healthTracker.getCurrentHealthValue();
        healthTracker.setCurrentHealthValue(health - burnDamage);

        if (ticks == 0) {
            setActiveState(false);
        }
    }
}
