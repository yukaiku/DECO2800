package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceballAttackTask;

public class IceballSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private final int MAX_COOLDOWN = 50;
    /* Damage to apply from sting */
    private final int DAMAGE = 8;
    /* Speed of projectile */
    private final float SPEED = 0.5f;
    /* Lifetime of projectile */
    private final int LIFETIME = 60;

    /* Cooldown tracker */
    private int cooldown = 0;
    /* Reference to parent entity */
    private AbstractEntity entity;

    public IceballSkill(AbstractEntity parent) {
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
    public int getCooldown() {
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
        if (cooldown <= 0) {
            AbstractTask task = new IceballAttackTask(entity, targetX, targetY, DAMAGE, SPEED, LIFETIME);
            cooldown = MAX_COOLDOWN;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }
}
