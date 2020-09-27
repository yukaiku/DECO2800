package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireBombAttackTask;

/**
 * Launches a fireball that deals damage when it hits a target.
 */
public class FireBombSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int MAX_COOLDOWN = 160;

    /* Damage multiplier to apply to the explosion.
    Multiplies the peon base damage value. */
    private static final float damageMultiplier = 0.4f;
    /* Lifetime of explosion */
    private static final int LIFETIME = 60;
    /* Tick period of explosion */
    private static final int TICK_PERIOD = 20;
    /* Height of explosion */
    private static final int HEIGHT = 3;
    /* Width of explosion */
    private static final int WIDTH = 3;

    /* Cooldown tracker */
    private int cooldown = 0;
    /* Reference to parent entity */
    private final Peon entity;

    /**
     * Creates a new FireBombSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public FireBombSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = (Peon) parent;
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
    public int getCooldownRemaining() {
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
     * @return String name of skill icon
     */
    @Override
    public String getTexture() {
        return "explosionIcon";
    }

    /**
     * Creates and returns a new skill task for this skill that is executed by the
     * entity executing the skill.
     * @param targetX Not used for this skill
     * @param targetY Not used for this skill
     * @return New AbstractTask to execute.
     * @throws SkillOnCooldownException when cooldown > 0
     */
    @Override
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        int damage = (int) (entity.getDamage() * damageMultiplier);
        if (cooldown <= 0) {
            AbstractTask task = new FireBombAttackTask(entity, damage, LIFETIME, TICK_PERIOD, HEIGHT, WIDTH);
            cooldown = MAX_COOLDOWN;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }
}
