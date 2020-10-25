package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceballAttackTask;

public class IceballSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 50;
    /* Damage multiplier to apply to the iceball.
    Multiplies the peon base damage value. */
    private static float damageMultiplier = 0.4f;
    /* Lifetime of explosion */
    /* Speed of projectile */
    private static final float SPEED = 0.5f;
    /* Lifetime of projectile */
    private static final int LIFETIME = 20;
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
        return maxCoolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        maxCoolDown = cooldownMax;
    }

    /***
     * Sets coooldown of skill
     * @param maxCoolDown cooldown of skill
     */
    public static void setMaxCooldown(int maxCoolDown){
        IceballSkill.maxCoolDown = maxCoolDown;
    }

    /**
     * Returns multiplier of skill
     *
     * @return Multiplier of skill.
     */
    public float getDamageMultiplier(){
        return damageMultiplier;
    }

    /***
     * Set multiplier of skill
     *
     * @param damageMultiplier multiplier of skill
     */
    public static void setDamageMultiplier(float damageMultiplier){
        IceballSkill.damageMultiplier = damageMultiplier;
    }

    public static void reduceCooldownMax(float percent){
        if (maxCoolDown > 25) {
            maxCoolDown = Math.round(maxCoolDown * (1.0f - percent));
        }
    }

    @Override
    public String getTexture() {
        return "iceballIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * damageMultiplier);
        return new IceballAttackTask(entity, targetX, targetY,
                    damage, SPEED, LIFETIME, speedMultiplier, slowDuration);
    }
}
