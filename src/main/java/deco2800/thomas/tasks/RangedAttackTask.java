package deco2800.thomas.tasks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.worlds.AbstractWorld;

/**
 * The RangedAttackTask is used to spawn projectiles into the game world.
 * Implementing this as a task, so that "Inductions" or, Cooldowns may be implemented in future
 * builds.
 */
public class RangedAttackTask extends AbstractTask {
    /* Reference to the current game world */
    private AbstractWorld world;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    /**
     * Spawns a projectile with specified parameters.
     * @param entity Parent entity of projectile.
     * @param targetCol X direction to move towards
     * @param targetRow Y direction to move towards
     * @param damage Damage to apply
     * @param speed Speed to move at
     * @param lifetime Lifetime of projectile
     */
    public RangedAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage,
                            float speed, int lifetime) {
        super(entity);
        Fireball.spawn(entity.getCol(), entity.getRow(), targetCol, targetRow, damage, speed,
                lifetime, entity.getFaction());
    }

    /**
     * Returns whether the task is completed.
     * @return True when task is complete.
     */
    @Override
    public boolean isComplete() {
        return taskComplete;
    }

    /**
     * Returns whether the task is alive.
     * @return True when the task is alive.
     */
    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    /**
     * Update task per tick.
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        taskComplete = true;
    }
}
