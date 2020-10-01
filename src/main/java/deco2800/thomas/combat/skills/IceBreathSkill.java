package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceBreathTask;

public class IceBreathSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static final int COOLDOWN = 20;
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;
    /* Speed multiplier to apply for slow effect. */
    private final float speedMultiplier;
    /* Duration of slow effect. */
    private final int slowDuration;

    /* Reference to parent entity */
    private final Peon entity;

    public IceBreathSkill(Peon entity, float speedMultiplier, int slowDuration) {
        this.entity = entity;
        this.speedMultiplier = speedMultiplier;
        this.slowDuration = slowDuration;
    }

    @Override
    public int getCooldownMax() {
        return COOLDOWN;
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new IceBreathTask(entity, targetX, targetY,
                    damage, speedMultiplier, slowDuration);
    }
}
