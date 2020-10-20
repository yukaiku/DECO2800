package deco2800.thomas.tasks.status;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.util.SquareVector;

/**
 * A status effect which damages an entity while they remain standing on
 * a set position.
 */
public class QuicksandBurnStatus extends BurnStatus {

    // the position which damages the entity
    private final SquareVector position;

    /**
     * Creates a new QuicksandBurnStatus for an entity, with a set damage, number of
     * ticks and position.
     *
     * @param entity The entity this effect applies to.
     * @param burnDamage The damage taken by this entity each tick.
     * @param ticks The number of damage ticks for this effect.
     * @param position The position inflicting this effect.
     */
    public QuicksandBurnStatus(Peon entity, int burnDamage, int ticks, SquareVector position) {
        super(entity, burnDamage, ticks);
        this.position = position;
    }

    /**
     * Creates a new QuicksandBurnStatus for an entity, with a set damage, number of
     * ticks and position.
     *
     * @param entity The entity this effect applies to.
     * @param burnDamage The damage taken by this entity each tick.
     * @param ticks The number of damage ticks for this effect.
     * @param position The position inflicting this effect.
     */
    public QuicksandBurnStatus(Peon entity, int burnDamage, int ticks, SquareVector position, DamageType damageType) {
        super(entity, burnDamage, ticks, damageType);
        this.position = position;
    }

    /**
     * Applied the damage effect in the same way as a BurnStatus, but removes the effect if
     * the player leaves the position.
     */
    @Override
    public void applyEffect() {
        super.applyEffect();
        if (!getAffectedEntity().getPosition().tileEquals(position)) {
            setActiveState(false);
        }
    }
}
