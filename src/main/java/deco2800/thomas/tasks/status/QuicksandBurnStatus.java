package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.util.SquareVector;

public class QuicksandBurnStatus extends BurnStatus {

    private SquareVector position;

    public QuicksandBurnStatus(AgentEntity entity, int burnDamage, int ticks, SquareVector position) {
        super(entity, burnDamage, ticks);
        this.position = position;
    }

    /**
     * Apply Speed status
     */
    @Override
    public void applyEffect() {
        super.applyEffect();
        if (!getAffectedEntity().getPosition().tileEquals(position)) {
            setActiveState(false);
        }
    }
}
