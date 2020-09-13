package deco2800.thomas.tasks.status;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.util.SquareVector;

/**
 * A status effect which damages an entity while they remain standing on
 * a set position.
 */
public class QuicksandBurnStatus extends BurnStatus {

    // the position which damages the entity
    private SquareVector position;

    /**
     * Creates a new QuicksandBurnStatus for an entity, with a set damage, number of
     * ticks and position.
     *
     * @param entity
     * @param burnDamage
     * @param ticks
     * @param position
     */
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
