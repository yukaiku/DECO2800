package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceBreathTask;

public class IceBreathSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int cooldown = 20;
    /* Damage to apply from fireball */
    private static final int damage = 20;

    private static final float speedMultiplier = 0.2f;

    private static final int slowDuration = 4;

    /* Cooldown tracker */
    private int cooldownRemaining = 0;
    /* Reference to parent entity */
    private final AbstractEntity entity;

    public IceBreathSkill(AbstractEntity entity) {
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
            AbstractTask task = new IceBreathTask(entity, targetX, targetY,
                    damage, speedMultiplier, slowDuration);
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
