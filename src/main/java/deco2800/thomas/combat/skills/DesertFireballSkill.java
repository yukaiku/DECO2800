package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.DesertFireballAttackTask;

public class DesertFireballSkill extends AbstractSkill {

    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 200;
    private static int cooldown = 200;
    private static final int ORIGINAL_COOLDOWN = 200;
    /* Damage multiplier to apply to the fireball.
    Multiplies the peon base damage value. */
    private static float damageMultiplier = 0.4f;
    /* Speed of fireball */
    private static final float SPEED = 0.4f;
    /* Lifetime of fireball */
    private static final int LIFETIME = 40;

    /* Reference to parent entity */
    private final Peon entity;
    public DesertFireballSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    @Override
    public int getCooldownMax() {
        return maxCoolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        cooldown = cooldownMax;
    }

    public void reduceCooldownMax(float percent) {
        if (cooldown > 10) {
            cooldown = Math.round(cooldown * (1.0f - percent));
        }
    }

    public void setCooldownMax() {
        cooldown = ORIGINAL_COOLDOWN;
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * damageMultiplier);
        return new DesertFireballAttackTask(entity, targetX, targetY, damage, SPEED, LIFETIME);
    }
}
