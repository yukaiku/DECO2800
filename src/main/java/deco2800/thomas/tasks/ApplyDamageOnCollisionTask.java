package deco2800.thomas.tasks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

/**
 * This task checks for colliding entities, and when an enemy entity is detected
 * it applies damage.
 */
public class ApplyDamageOnCollisionTask extends AbstractTask{
    // Reference to parent entity
    private CombatEntity entity;
    // Reference to current game world
    private AbstractWorld world;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    /**
     * Creates an instance of the task.
     * @param entity Parent entity
     */
    public ApplyDamageOnCollisionTask(CombatEntity entity) {
        super(entity);

        this.entity = entity;
        this.taskComplete = false;
        world = GameManager.get().getWorld();
    }

    /**
     * Returns whether the task is complete.
     * @return True when the task has applied damage.
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
     * Executes a single game tick, where it detects collisions with the enemy.
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
        if (collidingEntities.size() > 1) { // Own bounding box should always be present
            for (AbstractEntity e : collidingEntities) {
                // TODO("Combat"): This needs to be a generic method of identify whether the entity is a friend or foe
                if (e.getObjectName() == "Orc") {
                    applyDamage((EnemyPeon)e);
                }
            }
        }
    }

    /**
     * Applies damage to a given entity as per the parent's entity damage value.
     * @param e Enemy entity
     */
    private void applyDamage (EnemyPeon e) {
        e.reduceHealth(entity.getDamage());
        this.taskComplete = true;
        if (e.isDead()) {
            e.death();
        }
    }
}
