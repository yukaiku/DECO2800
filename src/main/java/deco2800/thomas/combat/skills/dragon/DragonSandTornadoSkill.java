package deco2800.thomas.combat.skills.dragon;

import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;

public class DragonSandTornadoSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int coolDown = 500;
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;
    /* Speed of sand tornado */
    private static final float SPEED = 0.2f;
    /* Lifetime of sand tornado */
    private static final int LIFETIME = 40;

    /* Reference to parent entity */
    private final Peon entity;

    public DragonSandTornadoSkill(Peon entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        this.entity = entity;
    }

    @Override
    public int getCooldownMax() {
        return coolDown;
    }

    @Override
    public void setCooldownMax(int cooldownMax) {
        coolDown = cooldownMax;
    }

    public static void setMaxCoolDown(int maxCoolDown){
        DragonSandTornadoSkill.coolDown = maxCoolDown;
    }

    public static void reduceCooldownMax(float percent){
        if (coolDown > 15) {
            coolDown = Math.round(coolDown * (1.0f - percent));
        }
    }

    @Override
    public String getTexture() {
        return "sandTornadoIcon";
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new SandTornadoAttackTask(entity, targetX, targetY, damage, SPEED, LIFETIME);
    }
}
