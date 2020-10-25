package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.tasks.AbstractTask;

/**
 * A skill generates a CombatTask for an entity, and provides encapsulation
 * of the cooldown, and other skill specific traits.
 */
public abstract class AbstractSkill implements Tickable {
    /* Tracks the cooldown of this skill */
    private int cooldownRemaining;
    /* Audio to play when using skill */
    private String attackSound = "fireball";
    private boolean attackSoundEnabled = true;

    /**
     * Returns (in ticks) how long is remaining on the cooldown.
     * @return Cooldown remaining.
     */
    public int getCooldownRemaining() {
        return cooldownRemaining;
    }

    /**
     * Starts the cooldown timer.
     */
    public void startCooldown() {
        cooldownRemaining = getCooldownMax();
    }

    /**
     * Returns (in ticks) how long the full cooldown of this skill is.
     * @return Maximum cooldown of skill.
     */
    public abstract int getCooldownMax();

    public abstract void setCooldownMax(int cooldownMax);

    /**
     * Sets the attack sound for this skill.
     * @param sound Name of sound.
     */
    public void setAttackSound(String sound) {
        attackSound = sound;
    }

    /**
     * Sets whether this skill should play a sound when used.
     * @param enabled True = play sound, false = no sound
     */
    public void setAttackSoundEnabled(boolean enabled) {
        attackSoundEnabled = enabled;
    }

    /**
     * Returns a string containing the name of the texture that is used to represent
     * this skill on the skill bar.
     * @return Texture id
     */
    public abstract String getTexture();

    /**
     * Returns a new skill task for this skill.
     * @param targetX X position of mouse in ColRow coordinates.
     * @param targetY Y position of mouse in ColRow coordinates.
     * @return New AbstractTask to execute.
     */
    protected abstract AbstractTask getTask(float targetX, float targetY);

    /**
     * Creates and returns a new skill task for this skill that is executed by the
     * entity executing the skill.
     * @param targetX X position of mouse in ColRow coordinates
     * @param targetY Y position of mouse in ColRow coordinates
     * @return New AbstractTask to execute.
     * @throws SkillOnCooldownException when cooldown > 0
     */
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        if (getCooldownRemaining() <= 0) {
            if (attackSoundEnabled) {
                GameManager.getManagerFromInstance(SoundManager.class).playSound(attackSound);
            }
            startCooldown();
            return getTask(targetX, targetY);
        }
        throw new SkillOnCooldownException();
    }

    /**
     * On tick is called periodically (time dependant on the world settings).
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        if (cooldownRemaining > 0) {
            cooldownRemaining--;
        }
    }
}
