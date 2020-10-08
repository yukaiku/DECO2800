package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;

/**
 * The WaterShield for a target entity. Protect them
 * from all damage sources by locking the health
 * for that entity until the end of water shield's
 * lifer time
 */
public class WaterShield extends CombatEntity {
    private Peon entity;
    private final long lifetime;
    private long currentLifetime = 0;

    public WaterShield(Peon entity, long lifeTime) {
        super(entity.getCol(), entity.getRow(), RenderConstants.PEON_EFFECT_RENDER, 0, entity.getFaction());
        this.entity = entity;
        this.lifetime = lifeTime;
        this.entity.lockHealth();
    }

    /**
     * Set the position for the shield to follow
     * the parent entity
     *
     * @param i on tick time
     */
    @Override
    public void onTick(long i) {
        this.setPosition(entity.getCol(), entity.getRow(), this.getRenderOrder());

        // Check if lifetime has expired
        if (++currentLifetime >= lifetime) {
            this.entity.unlockHealth();
            GameManager.get().getWorld().removeEntity(this);
        }
    }
}
