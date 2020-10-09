package deco2800.thomas.combat.skills;

import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.util.SquareVector;

/**
 * Base Ultimate skill for the Mech. ~180 degree frontal sword swipe, that
 * applies small knockback to enemies.
 */
public class SwordSwipe extends AbstractSkill {
    /* Maximum time of cooldown in ticks */
    private static int MAX_COOLDOWN = 10 * 50; // 50 is a magic number ):
    /* Damage multiplier to apply to the ice tile.
    Multiplies the peon base damage value. */
    private static final float DAMAGE_MULTIPLIER = 0.4f;

    /* Reference to parent entity */
    private final Peon entity;

    /**
     * Creates a new SwordSwipe and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public SwordSwipe(Peon parent) {
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

    @Override
    public void reduceCooldownMax(float percent){
        if (MAX_COOLDOWN > 250) {
            MAX_COOLDOWN = Math.round(MAX_COOLDOWN * (1.0f - percent));
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
        return null;
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
        AbstractTask task;
        SquareVector origin;
        double angle = Math.toDegrees(Math.atan2(targetX - entity.getCol(), targetY - entity.getRow()));
        if (angle > -45 && angle < 45) {
            // Spawn above entity
            origin = new SquareVector(entity.getCol() - 1, entity.getRow() + 1);
            task = new MeleeAttackTask(entity, origin, 3, 2, damage);

        } else if (angle >= -135 && angle <= -45) {
            // Spawn to left of player
            origin = new SquareVector(entity.getCol() - 2, entity.getRow() + 1);
            task = new MeleeAttackTask(entity, origin, 2, 3, damage);

        } else if (angle < -135 || angle > 135) {
            // Spawn below player
            origin = new SquareVector(entity.getCol() - 1, entity.getRow());
            task = new MeleeAttackTask(entity, origin, 3, 2, damage);

        } else {
            // Spawn right of player
            origin = new SquareVector(entity.getCol() + 1, entity.getRow() + 1);
            task = new MeleeAttackTask(entity, origin, 2, 3, damage);
        }
        return task;
    }
}
