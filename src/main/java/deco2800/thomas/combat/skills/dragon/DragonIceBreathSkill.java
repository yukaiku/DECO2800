package deco2800.thomas.combat.skills.dragon;

import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceBreathTask;

public class DragonIceBreathSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int coolDown = 500;
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;
    /* Speed multiplier to apply for slow effect. */
    private final float speedMultiplier;
    /* Duration of slow effect. */
    private final int slowDuration;

    /* Reference to parent entity */
    private final Peon entity;

    public DragonIceBreathSkill(Peon entity, float speedMultiplier, int slowDuration) {
        this.entity = entity;
        this.speedMultiplier = speedMultiplier;
        this.slowDuration = slowDuration;
    }

    @Override
    public int getCooldownMax() {
        return coolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        coolDown = cooldownMax;
    }

    public static void setMaxCooldown(int maxCooldown){
        DragonIceBreathSkill.coolDown = maxCooldown;
    }

    public static void reduceCooldownMax(float percent) {
        if (coolDown > 10){
            coolDown = Math.round(coolDown * (1.0f - percent));
        }
    }

    @Override
    public String getTexture() {
        return "iceBreathIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new IceBreathTask(entity, targetX, targetY,
                    damage, speedMultiplier, slowDuration);
    }
}
