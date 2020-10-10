package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireballAttackTask;

/**
 * Launches a fireball that deals damage when it hits a target. 
 */
public class FireballSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int MAX_COOLDOWN = 20;
    /* Damage multiplier to apply to the fireball.
    Multiplies the peon base damage value. */
    private static float DAMAGE_MULTIPLIER = 0.4f;
    /* Speed of fireball */
    private static final float speed = 0.5f;
    /* Lifetime of fireball */
    private static final int lifetime = 60;

    /* Reference to parent entity */
    private final Peon entity;

    /**
     * Creates a new FireballSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public FireballSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
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

    /***
     * Sets coooldown of skill
     * @param maxCooldown cooldown of skill
     */
    public void setMaxCooldown(int maxCooldown){
        MAX_COOLDOWN = maxCooldown;
    }

    /**
     * Returns multiplier of skill
     *
     * @return Multiplier of skill.
     */
    public float getDamageMultiplier(){
        return DAMAGE_MULTIPLIER;
    }

    /***
     * Set multiplier of skill
     *
     * @param damageMultiplier multiplier of skill
     */
    public void setDamageMultiplier(float damageMultiplier){
        this.DAMAGE_MULTIPLIER = damageMultiplier;
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
     */
    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new FireballAttackTask(entity, targetX, targetY, damage, speed, lifetime);
    }
}
