package deco2800.thomas.combat.skills.dragon;

import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireballAttackTask;

/**
 * Launches a fireball that deals damage when it hits a target. 
 */
public class DragonFireballSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private int cooldown;

    /* Damage multiplier to apply to the fireball.
    Multiplies the peon base damage value. */
    private float damageMultiplier;
    /* Speed of fireball */
    private final float speed;
    /* Lifetime of fireball */
    private final int lifetime;

    /* Reference to parent entity */
    private final Peon entity;

    /**
     * Creates a new FireballSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public DragonFireballSkill(Peon parent, int cooldown, float damageMultiplier, float speed, int lifetime) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
        this.cooldown = cooldown;
        this.damageMultiplier = damageMultiplier;
        this.speed = speed;
        this.lifetime = lifetime;
    }

    /**
     * Returns (in ticks) how long the full cooldown of this skill is.
     *
     * @return Maximum cooldown of skill.
     */
    @Override
    public int getCooldownMax() {
        return cooldown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        cooldown = cooldownMax;
    }

    /**
     * Returns multiplier of skill
     *
     * @return Multiplier of skill.
     */
    public float getDamageMultiplier(){
        return damageMultiplier;
    }

    /***
     * Set multiplier of skill
     *
     * @param damageMultiplier multiplier of skill
     */
    public void setDamageMultiplier(float damageMultiplier){
        this.damageMultiplier = damageMultiplier;
    }

    public void reduceCooldownMax(float percent){
        if (cooldown > 10) {
            cooldown = Math.round(cooldown * (1.0f - percent));
        }
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
        int damage = (int) (entity.getDamage() * damageMultiplier);
        return new FireballAttackTask(entity, targetX, targetY, damage, speed, lifetime);
    }
}
