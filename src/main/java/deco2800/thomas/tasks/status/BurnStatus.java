package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;

public class BurnStatus extends StatusEffect {
    private int burnDamage = 1;

    /**
     * Default Constructor for the Burn Status effect (Deals 5 damage)
     */
    public BurnStatus(AgentEntity entity) {
        super(entity);
    }

    /**
     * Customer Constructor for the Burn Status effect (Deals specified damage)
     */
    public BurnStatus(AgentEntity entity, int burnDamage) {
        super(entity);
        this.burnDamage = burnDamage;
    }

    /**
     * Apply Burn status
     */
    @Override
    public void applyEffect() {
        setActiveState(true);
        int health = getAffectedEntity().getHealthTracker().getCurrentHealthValue();
        getAffectedEntity().getHealthTracker().setCurrentHealthValue(health - burnDamage);
    }
}
