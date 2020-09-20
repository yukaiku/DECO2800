package deco2800.thomas.tasks.combat;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.status.TornadoStatus;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class ApplyTornadoOnCollisionTask extends AbstractTask {

    // Reference to current game world
    private AbstractWorld world;
    // Lifetime of task
    private long lifetime, currentLifetime;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    public ApplyTornadoOnCollisionTask(AbstractEntity entity, long lifetime) {
        super(entity);

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
        if (taskAlive) {
            List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
            if (collidingEntities.size() > 1) { // Own bounding box should always be present
                for (AbstractEntity e : collidingEntities) {
                    EntityFaction faction = e.getFaction();
                    if (faction != EntityFaction.None && faction != entity.getFaction()) {
                        applyDamage(e);
                        applyTornado(e);

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
            taskAlive = false;
            peon.applyDamage(((CombatEntity) entity).getDamage(), DamageType.COMMON);
            if (peon.isDead()) {
                peon.death();
            }
        }
    }

    private void applyTornado (AbstractEntity e) {
        if (e instanceof PlayerPeon) {
            PlayerPeon playerPeon = (PlayerPeon) e;
            playerPeon.addEffect(new TornadoStatus(playerPeon, 10));
        }
    }
}
