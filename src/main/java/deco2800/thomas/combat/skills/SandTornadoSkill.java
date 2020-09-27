package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;

public class SandTornadoSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int cooldown = 30;
    /* Damage to apply from fireball */
    private static final int damage = 50;
    /* Speed of fireball */
    private static final float speed = 0.2f;
    /* Lifetime of fireball */
    private static final int lifetime = 60;

    /* Cooldown tracker */
    private int cooldownRemaining = 0;
    /* Reference to parent entity */
    private final AbstractEntity entity;

    public SandTornadoSkill(AbstractEntity entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        this.entity = entity;
    }

    @Override
    public int getCooldownRemaining() {
        return cooldownRemaining;
    }

    @Override
    public int getCooldownMax() {
        return cooldown;
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        if (cooldownRemaining <= 0) {
            AbstractTask task = new SandTornadoAttackTask(entity, targetX, targetY, damage, speed, lifetime);
            cooldownRemaining = cooldown;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }

    @Override
    public void onTick(long tick) {
        if (cooldownRemaining > 0) {
            cooldownRemaining--;
        }
    }
}
