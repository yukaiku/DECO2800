package deco2800.thomas.combat.skills;

import deco2800.thomas.Tickable;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.util.SquareVector;

/**
 * Swings a sword that deals damage when it hits a target.
 */
public class MeleeSkill implements Skill, Tickable {
    /* Maximum time of cooldown in ticks */
    private static final int MAX_COOLDOWN = 10;
    /* Damage to apply from attack */
    private static final int DAMAGE = 10;

    /* Cooldown tracker */
    private int cooldown = 0;
    /* Reference to parent entity */
    private final AbstractEntity entity;

    /**
     * Creates a new MeleeSkill and binds it to the Entity.
     * @param parent Parent entity of skill.
     * @throws NullPointerException when parent is null
     */
    public MeleeSkill(AbstractEntity parent) {
        if (parent == null) {
            throw new NullPointerException();
        }
        this.entity = parent;
    }

    /**
     * On tick is called periodically (time dependant on the world settings).
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    /**
     * Returns (in ticks) how long is remaining on the cooldown.
     *
     * @return Cooldown remaining.
     */
    @Override
    public int getCooldownRemaining() {
        return cooldown;
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
     * @throws SkillOnCooldownException when cooldown > 0
     */
    @Override
    public AbstractTask getNewSkillTask(float targetX, float targetY) throws SkillOnCooldownException {
        if (cooldown <= 0) {
            AbstractTask task;
            SquareVector origin;
            double angle = Math.toDegrees(Math.atan2(targetX - entity.getCol(), targetY - entity.getRow()));
            if (angle > -45 && angle < 45) {
                // Spawn above entity
                origin = new SquareVector(entity.getCol(), entity.getRow() + 1);
                task = new MeleeAttackTask(entity, origin, 2, 2, DAMAGE);

            } else if (angle >= -135 && angle <= -45) {
                // Spawn to left of player
                origin = new SquareVector(entity.getCol() - 1, entity.getRow());
                task = new MeleeAttackTask(entity, origin, 2, 2, DAMAGE);

            } else if (angle < -135 || angle > 135) {
                // Spawn below player
                origin = new SquareVector(entity.getCol(), entity.getRow() - 1);
                task = new MeleeAttackTask(entity, origin, 2, 2, DAMAGE);

            } else {
                // Spawn right of player
                origin = new SquareVector(entity.getCol() + 1, entity.getRow());
                task = new MeleeAttackTask(entity, origin, 2, 2, DAMAGE);
            }
            cooldown = MAX_COOLDOWN;
            return task;
        } else {
            throw new SkillOnCooldownException();
        }
    }
}
