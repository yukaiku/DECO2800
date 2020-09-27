package deco2800.thomas.combat;

import deco2800.thomas.Tickable;
import deco2800.thomas.tasks.AbstractTask;

/**
 * A skill generates a CombatTask for an entity, and provides encapsulation
 * of the cooldown, and other skill specific traits.
 */
public interface Skill extends Tickable {
    /**
     * Returns (in ticks) how long is remaining on the cooldown.
     * @return Cooldown remaining.
     */
    int getCooldownRemaining();

    /**
     * Returns (in ticks) how long the full cooldown of this skill is.
     * @return Maximum cooldown of skill.
     */
    int getCooldownMax();

    /**
     * Returns a string containing the name of the texture that is used to represent
     * this skill on the skill bar.
     * @return
     */
    String getTexture();

    /**
     * Creates and returns a new skill task for this skill that is executed by the
     * entity executing the skill.
     * @param targetX X position of mouse in ColRow coordinates
     * @param targetY Y position of mouse in ColRow coordinates
     * @return New AbstractTask to execute.
     * @throws SkillOnCooldownException when cooldown > 0
     */
    AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException;
}
