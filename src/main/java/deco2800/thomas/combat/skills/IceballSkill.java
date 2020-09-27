package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceballAttackTask;

public class IceballSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int MAX_COOLDOWN = 50;
    /* Damage multiplier to apply to the iceball.
    Multiplies the peon base damage value. */
    private static final float damageMultiplier = 0.4f;
    /* Lifetime of explosion */
    /* Speed of projectile */
    private static final float SPEED = 0.5f;
    /* Lifetime of projectile */
    private static final int LIFETIME = 60;

    private static final float speedMultiplier = 0.5f;

    private static final int slowDuration = 2;

    /* Cooldown tracker */
    private int cooldown = 0;
    /* Reference to parent entity */
    private final Peon entity;

    public IceballSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    @Override
    public void onTick(long tick) {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    @Override
    public int getCooldownRemaining() {
        return cooldown;
    }

    @Override
    public int getCooldownMax() {
        return MAX_COOLDOWN;
    }

    @Override
    public String getTexture() {
        return "iceballIcon";
    }

    @Override
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        int damage = (int) (entity.getDamage() * damageMultiplier);
        if (cooldown <= 0) {
            AbstractTask task = new IceballAttackTask(entity, targetX, targetY,
                    damage, SPEED, LIFETIME, speedMultiplier, slowDuration);
            cooldown = MAX_COOLDOWN;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }
}
