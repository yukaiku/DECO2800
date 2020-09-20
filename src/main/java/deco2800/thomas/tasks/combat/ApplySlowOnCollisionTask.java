package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class ApplySlowOnCollisionTask extends AbstractTask {
    // Reference to current game world
    private AbstractWorld world;
    // Lifetime of task
    private long lifetime, currentLifetime;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    public ApplySlowOnCollisionTask(AbstractEntity entity) {
        super(entity);
    }

    /**
     * Creates an instance of the task.
     * @param entity Parent entity
     */
    public ApplySlowOnCollisionTask(CombatEntity entity, long lifetime) {
        super(entity);

        //this.entity = entity;
        this.taskComplete = false;
        world = GameManager.get().getWorld();

        this.lifetime = lifetime;
        this.currentLifetime = 0;
    }

    @Override
    public boolean isComplete() {
        return taskComplete;
    }

    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    @Override
    public void onTick(long tick) {
        if (!taskComplete) {
            List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
            if (collidingEntities.size() > 1) { // Own bounding box should always be present
                for (AbstractEntity e : collidingEntities) {
                    EntityFaction faction = e.getFaction();
                    if (faction != EntityFaction.None && faction != entity.getFaction()) {
                        applySlow(e);
                    }
                }
            }

            // Check if lifetime has expired
            if (++currentLifetime >= lifetime) {
                taskComplete = true;
            }
        }
    }

    private void applySlow (AbstractEntity e) {
        if (e instanceof Peon) {
            Peon peon = (Peon) e;

            peon.addEffect(new SpeedStatus(peon, 0.1f, 5));
            this.taskComplete = true;
            if (peon.isDead()) {
                peon.death();
            }
        }
    }
}
