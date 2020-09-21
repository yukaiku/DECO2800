package deco2800.thomas.tasks.combat;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

/**
 * This task checks for colliding entities, and when an enemy entity is detected
 * it applies damage.
 */
public class ApplyDamageOnCollisionTask extends AbstractTask {
    // Reference to current game world
    private AbstractWorld world;
    // Lifetime of task
    private long lifetime, currentLifetime;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    /**
     * Creates an instance of the task.
     * @param entity Parent entity
     */
    public ApplyDamageOnCollisionTask(CombatEntity entity, long lifetime) {
        super(entity);

        //this.entity = entity;
        this.taskComplete = false;
        world = GameManager.get().getWorld();

        this.lifetime = lifetime;
        this.currentLifetime = 0;
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
        if (!taskComplete) {
            List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
            if (collidingEntities.size() > 1) { // Own bounding box should always be present
                for (AbstractEntity e : collidingEntities) {
                    EntityFaction faction = e.getFaction();
                    if (faction != EntityFaction.NONE && faction != entity.getFaction()) {
                        applyDamage(e);
                    }
                }
            }

            // Check if lifetime has expired
            if (++currentLifetime >= lifetime) {
                taskComplete = true;
            }
        }
    }

    /**
     * Applies damage to a given entity as per the parent's entity damage value.
     * @param e Enemy entity
     */
    private void applyDamage (AbstractEntity e) {
        if (e instanceof Peon) {
            Peon peon = (Peon) e;

            peon.applyDamage(((CombatEntity) entity).getDamage(), DamageType.COMMON);
            this.taskComplete = true;
            if (peon.isDead()) {
                peon.death();
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
