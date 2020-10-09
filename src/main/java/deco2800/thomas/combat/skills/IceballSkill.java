package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceballAttackTask;

public class IceballSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int MAX_COOLDOWN = 50;
    /* Damage multiplier to apply to the iceball.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;
    /* Lifetime of explosion */
    /* Speed of projectile */
    private static final float SPEED = 0.5f;
    /* Lifetime of projectile */
    private static final int LIFETIME = 60;
    /* Speed multiplier to apply */
    private final float speedMultiplier;
    /* Duration of slow effect */
    private final int slowDuration;

    /* Reference to parent entity */
    private final Peon entity;

    public IceballSkill(Peon parent, float speedMultiplier, int slowDuration) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
        this.speedMultiplier = speedMultiplier;
        this.slowDuration = slowDuration;
    }

    @Override
    public int getCooldownMax() {
        return MAX_COOLDOWN;
    }

    @Override
    public void reduceCooldownMax(float percent){
        if (MAX_COOLDOWN > 25) {
            MAX_COOLDOWN = Math.round(MAX_COOLDOWN * (1.0f - percent));
        }
    }

    @Override
    public String getTexture() {
        return "iceballIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new IceballAttackTask(entity, targetX, targetY,
                    damage, SPEED, LIFETIME, speedMultiplier, slowDuration);
    }
}
