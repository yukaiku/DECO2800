package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.tasks.AbstractTask;

/**
 * The RangedAttackTask is used to spawn projectiles into the game world.
 * Implementing this as a task, so that "Inductions" or, Cooldowns may be implemented in future
 * builds.
 */
public abstract class RangedAttackTask extends AbstractTask {
    // Task state
    private boolean taskComplete = false;
    private final float targetCol;
    private final float targetRow;
    private final float speed;
    private final int damage;
    private final int lifetime;

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
        this.targetCol = targetCol;
        this.targetRow = targetRow;
        this.damage = damage;
        this.speed = speed;
        this.lifetime = lifetime;
    }

    /**
     * Implemented by the extending class. This is the method that actually spawns the relevant
     * projectile into the game world.
     * @param col Initial x position
     * @param row Initial y position
     * @param targetCol X direction to move towards
     * @param targetRow Y direction to move towards
     * @param damage Damage to apply
     * @param speed Speed to move at
     * @param lifetime Lifetime of projectile
     * @param faction Faction of projectile
     */
    protected abstract void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed,
                                  long lifetime, EntityFaction faction);

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
        return true;
    }

    /**
     * Update task per tick.
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        spawn(entity.getCol(), entity.getRow(), targetCol, targetRow, damage, speed,
                lifetime, entity.getFaction());
        taskComplete = true;
    }
}
