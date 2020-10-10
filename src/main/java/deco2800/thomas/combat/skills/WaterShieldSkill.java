package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.WaterShieldTask;

/**
 * WaterShieldSkill will create a water shield surrounds
 * the entity and prevent him from all damage sources. The
 * effects such as slow, ... are still applied
 */
public class WaterShieldSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int maxCoolDown = 200;
    private static int original_MAX_COOLDOWN = 200;
    /* Lifetime of the shield */
    private static final int LIFETIME = 100;
    /* Reference to parent entity */
    private final Peon entity;

    public WaterShieldSkill(Peon parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    @Override
    public int getCooldownMax() {
        return maxCoolDown;
    }

    //Sets the maximum skill cooldown
    public static void setMaxCoolDown(int maxCoolDown){
        WaterShieldSkill.maxCoolDown = maxCoolDown;
    }

    @Override
    public void reduceCooldownMax(float percent){
        if (maxCoolDown > 100) {
            maxCoolDown = Math.round(maxCoolDown * (1.0f - percent));
        }
    }

    @Override
    public void setCooldownMax(){ maxCoolDown = original_MAX_COOLDOWN; }

    @Override
    public String getTexture() {
        return "watershieldIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        return new WaterShieldTask(entity, LIFETIME);
    }
}
