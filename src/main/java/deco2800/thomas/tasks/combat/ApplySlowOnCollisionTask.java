package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class ApplySlowOnCollisionTask extends AbstractTask {
    // Lifetime of task
    private long lifetime;
    private long currentLifetime;
    private float speedMultiplier;
    private int slowTime;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    public ApplySlowOnCollisionTask(AbstractEntity entity) {
        super(entity);
    }

    /**
     * Creates an instance of the task.
     * @param entity Parent entity
     * @param lifetime Time for the slow to last
     * @param speedMultiplier Multiple value to change speed by. Less than 0
     *                        will result in slowing
     * @param slowTime How long to apply the slow for
     */
    public ApplySlowOnCollisionTask(CombatEntity entity, long lifetime,
                                    float speedMultiplier, int slowTime) {
        super(entity);

        this.taskComplete = false;

        this.lifetime = lifetime;
        this.currentLifetime = 0;

        this.speedMultiplier = speedMultiplier;
        this.slowTime = slowTime;
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
        AbstractWorld world = GameManager.get().getWorld();
        if (!taskComplete) {
            List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
            if (collidingEntities.size() > 1) { // Own bounding box should always be present
                for (AbstractEntity e : collidingEntities) {
                    EntityFaction faction = e.getFaction();
                    if (faction != EntityFaction.NONE && faction != entity.getFaction()) {
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

            peon.addEffect(new SpeedStatus(peon, speedMultiplier, slowTime));
            this.taskComplete = true;
        }
    }
}
