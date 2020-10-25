package deco2800.thomas.combat.skills.dragon;

import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireBombAttackTask;

/**
 * Launches a fireball that deals damage when it hits a target.
 */
public class DragonFireBombSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 500;
    private static final int ORIGINAL_MAXCOOLDOWN = 160;

    /* Damage multiplier to apply to the explosion.
    Multiplies the peon base damage value. */
    private static float damageMultiplier = 0.4f;
    /* Lifetime of explosion */
    private static final int LIFETIME = 30;
    /* Tick period of explosion */
    private static final int TICK_PERIOD = 20;
    /* Height of explosion */
    private static final int HEIGHT = 3;
    /* Width of explosion */
    private static final int WIDTH = 3;

    /* Reference to parent entity */
    private final Peon entity;

    /**
     * Creates a new FireBombSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public DragonFireBombSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
        if (!PlayerPeon.isCoolDownBuffActive()) {
            setMaxCoolDown(ORIGINAL_MAXCOOLDOWN);
        }
        setAttackSound("explosion");
    }

    /**
     * Returns (in ticks) how long the full cooldown of this skill is.
     *
     * @return Maximum cooldown of skill.
     */
    @Override
    public int getCooldownMax() {
        return maxCoolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        maxCoolDown = cooldownMax;
    }

    /***
     * Sets coooldown of skill
     * @param maxCooldown cooldown of skill
     */
    public static void setMaxCoolDown(int maxCooldown){
        DragonFireBombSkill.maxCoolDown = maxCooldown;
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
    public static void setDamageMultiplier(float damageMultiplier){
        DragonFireBombSkill.damageMultiplier = damageMultiplier;
    }

    public static void reduceCooldownMax(float percent){
        if (maxCoolDown > 80) {
            setMaxCoolDown(Math.round(maxCoolDown * (1.0f - percent)));
        }
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
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * damageMultiplier);
        return new FireBombAttackTask(entity, damage, LIFETIME, TICK_PERIOD, HEIGHT, WIDTH);
    }
}
