package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.SpawnGoblinTask;

public class SummonGoblinSkill extends AbstractSkill{
    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 500;
    private static final int ORIGINAL_MAXCOOLDOWN = 500;
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static float damageMultiplier = 0.4f;
    /* Speed of projectile */
    private static final float SPEED = 0.3f;
    /* Lifetime of projectile */
    private static final int LIFETIME = 60;

    /* Reference to parent entity */
    private final Dragon entity;

    public SummonGoblinSkill(Dragon parent) {
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
        maxCoolDown = cooldownMax;
    }

    public void reduceCooldownMax(float percent) {
        if (maxCoolDown > 25) {
            maxCoolDown = Math.round(maxCoolDown * (1.0f - percent));
        }
    }

    public void setCooldownMax() {
        maxCoolDown = ORIGINAL_MAXCOOLDOWN;
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        return new SpawnGoblinTask(entity, targetX, targetY, this.entity.getVariation());
    }
}
