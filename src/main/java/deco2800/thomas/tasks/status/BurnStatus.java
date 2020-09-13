package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public class BurnStatus extends StatusEffect {
    private final int burnDamage;
    private long timeLastTick;
    private int ticks;

    /**
     * Default Constructor for the Burn Status effect (Deals 5 damage)
     */
    public BurnStatus(AgentEntity entity) {
        super(entity);
        timeLastTick = System.nanoTime();
        burnDamage = 1;
        ticks = 1;
    }

    /**
     * Customer Constructor for the Burn Status effect (Deals specified damage)
     */
    public BurnStatus(AgentEntity entity, int burnDamage, int ticks) {
        super(entity);
        this.burnDamage = burnDamage;
        timeLastTick = System.nanoTime();
        this.ticks = ticks;
    }

    public boolean ticksReady() {
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
        int health = getAffectedEntity().getHealthTracker().getCurrentHealthValue();
        getAffectedEntity().getHealthTracker().setCurrentHealthValue(health - burnDamage);

        if (ticks == 0) {
            setActiveState(false);
        }
    }
}
