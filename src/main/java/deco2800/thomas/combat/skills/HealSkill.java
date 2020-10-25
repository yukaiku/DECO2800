package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.HealTask;

/**
 * Heal the entity for a specific ratio based on
 * the max health
 */
public class HealSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 200;
    /* The health ratio based on the max health of the entity */
    private static final float HEALTH_RATIO = 0.3f;
    /* Reference to parent entity */
    private final Peon entity;

    public HealSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    @Override
    public int getCooldownMax() {
        return maxCoolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        maxCoolDown = cooldownMax;
    }

    public static void setMaxCoolDown(int maxCoolDown){
        HealSkill.maxCoolDown = maxCoolDown;
    }

    public static void reduceCooldownMax(float percent){
        if (maxCoolDown > 100) {
            maxCoolDown = Math.round(maxCoolDown * (1.0f - percent));
        }
    }


    @Override
    public String getTexture() {
        return "healIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int restoreHealth = (int)(HEALTH_RATIO * entity.getMaxHealth());
        return new HealTask(entity, restoreHealth);
    }
}
