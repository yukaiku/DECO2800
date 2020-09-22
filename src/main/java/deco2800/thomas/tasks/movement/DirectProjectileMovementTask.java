package deco2800.thomas.tasks.movement;

import deco2800.thomas.entities.attacks.Projectile;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.util.SquareVector;

/**
 * This task provides straight movement across the map directly to the
 * destination. IE, this movement does not follow the grid. The projectile
 * will continue to move until the task is ended, or the lifetime is exceeded.
 */
public class DirectProjectileMovementTask extends AbstractTask {
    // Entity who owns this task
    private final Projectile entity;
    // Delta step size to move towards
    private final float[] direction;
    // Lifetime of task
    private final long lifetime;
    private long currentLifetime;

    // Current task state
    private boolean taskComplete;

    /**
     * Creates an instance of the movement task, that will move the projectile until
     * it reaches its destination. This task does not take collisions into account!
     * @param entity entity who owns this task
     * @param destination coordinates of target
     * @param lifetime lifetime of task in ticks
     */
    public DirectProjectileMovementTask(Projectile entity, SquareVector destination, long lifetime) {
        super(entity);

        // Copy arguments
        this.entity = entity;
        this.direction = new float[] {
                destination.getCol() - entity.getCol(),
                destination.getRow() - entity.getRow()
        };
        this.lifetime = lifetime;
        this.currentLifetime = 0;
        this.taskComplete = false;
    }

    /**
     * Returns whether the task has run to completion. In this case, that is when the lifetime
     * has expired.
     * @return True when the task is complete.
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
     * Executes a single tick of projectile movement.
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        if (!taskComplete) {
            // Update position
            SquareVector destination = new SquareVector(
                    entity.getCol() + direction[0],
                    entity.getRow() + direction[1]
            );
            entity.moveTowards(destination);

            // Check if lifetime has expired
            if (++currentLifetime >= lifetime) {
                taskComplete = true;
            }
        }
    }

    /**
     * End the current task
     */
    @Override
    public void stopTask() {
        taskComplete = true;
    }
}
