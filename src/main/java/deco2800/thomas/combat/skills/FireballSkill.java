package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireballAttackTask;

/**
 * Launches a fireball that deals damage when it hits a target. 
 */
public class FireballSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int MAX_COOLDOWN = 20;
    /* Damage to apply from fireball */
    private static final int DAMAGE = 10;
    /* Speed of fireball */
    private static final float SPEED = 0.5f;
    /* Lifetime of fireball */
    private static final int LIFETIME = 60;

    /* Cooldown tracker */
    private int cooldown = 0;
    /* Reference to parent entity */
    private final AbstractEntity entity;

    /**
     * Creates a new FireballSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public FireballSkill(AbstractEntity parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    /**
     * On tick is called periodically (time dependant on the world settings).
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    /**
     * Returns (in ticks) how long is remaining on the cooldown.
     *
     * @return Cooldown remaining.
     */
    @Override
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Returns (in ticks) how long the full cooldown of this skill is.
     *
     * @return Maximum cooldown of skill.
     */
    @Override
    public int getCooldownMax() {
        return MAX_COOLDOWN;
    }

    /**
     * Returns a string containing the name of the texture that is used to represent
     * this skill on the skill bar.
     *
     * @return
     */
    @Override
    public String getTexture() {
        return "fireballIcon";
    }

    /**
     * Creates and returns a new skill task for this skill that is executed by the
     * entity executing the skill.
     * @param targetX X position of target in ColRow coordinates
     * @param targetY Y position of target in ColRow coordinates
     * @return New AbstractTask to execute.
     * @throws SkillOnCooldownException when cooldown > 0
     */
    @Override
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        if (cooldown <= 0) {
            AbstractTask task = new FireballAttackTask(entity, targetX, targetY, DAMAGE, SPEED, LIFETIME);
            cooldown = MAX_COOLDOWN;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }
}
