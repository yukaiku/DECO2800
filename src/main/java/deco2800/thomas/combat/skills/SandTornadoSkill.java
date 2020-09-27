package deco2800.thomas.combat.skills;

import deco2800.thomas.combat.AbstractSkill;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;

public class SandTornadoSkill extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static final int COOLDOWN = 30;
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;
    /* Speed of sand tornado */
    private static final float SPEED = 0.2f;
    /* Lifetime of sand tornado */
    private static final int LIFETIME = 60;

    /* Reference to parent entity */
    private final Peon entity;

    public SandTornadoSkill(Peon entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        this.entity = entity;
    }

    @Override
    public int getCooldownMax() {
        return COOLDOWN;
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    protected AbstractTask getTask(float targetX, float targetY) {
        int damage = (int) (entity.getDamage() * DAMAGE_MULTIPLIER);
        return new SandTornadoAttackTask(entity, targetX, targetY, damage, SPEED, LIFETIME);
    }
}
