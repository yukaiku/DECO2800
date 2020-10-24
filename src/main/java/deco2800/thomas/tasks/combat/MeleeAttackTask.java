package deco2800.thomas.tasks.combat;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

/**
 * The MeleeAttackTask executes a single melee attack.
 */
public class MeleeAttackTask extends AbstractTask {
    /* Reference to the current game world */
    private final AbstractWorld world;
    /* Bounding box to attack */
    private final BoundingBox bounds;
    /* Damage to apply */
    private final int damage;

    // Task state
    private boolean taskComplete = false;

    /**
     * Creates an instance of the MeleeAttackTask.
     * @param entity Parent entity
     * @param origin Origin of bounds to test
     * @param width Width of bounds to test
     * @param height Height of bounds to test
     * @param damage Damage to apply
     */
    public MeleeAttackTask(AbstractEntity entity, SquareVector origin, float width, float height, int damage) {
        super(entity);
        float[] dimensions = WorldUtil.colRowToWorldCords(width, height);
        this.bounds = new BoundingBox(origin, dimensions[0], dimensions[1]);
        this.damage = damage;
        this.world = GameManager.get().getWorld();
    }

    /**
     * Returns whether the task is complete.
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
     * Called each tick to update the melee attack. The attack is executed on the first tick,
     * and the remaining life is the cooldown.
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(this.bounds);
        if (collidingEntities.size() > 1) { // Own bounding box should always be present
            for (AbstractEntity e : collidingEntities) {
                EntityFaction faction = e.getFaction();
                if (faction != EntityFaction.NONE && faction != entity.getFaction()) {
                    applyDamage(e);
                }
            }
        }
        taskComplete = true;
    }

    /**
     * Applies damage to a given entity as per the parent's entity damage value.
     * @param e Enemy entity
     */
    private void applyDamage (AbstractEntity e) {
        if (e instanceof Peon) {
            Peon peon = (Peon) e;

            peon.applyDamage(this.damage, DamageType.COMMON);
        }
    }
}
