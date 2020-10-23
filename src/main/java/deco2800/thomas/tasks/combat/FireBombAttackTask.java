package deco2800.thomas.tasks.combat;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.attacks.Explosion;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.List;

/**
 * Spawns an explosion around the entity into the game world.
 */
public class FireBombAttackTask extends AbstractTask {
    /* Lifetime of the effect */
    private final long lifetime;
    /* Tick period of effect */
    private final long tickPeriod;
    /* Damage to apply */
    private final int damage;
    /* Task status */
    private boolean complete = false;
    /* Explosion height */
    private final int height;
    /* Explosion width */
    private final int width;

    /**
     * Creates an instance of the ExplosionTask which executes the FireBomb attack and spawns
     * explosion entities.
     * @param entity Parent entity of attack
     * @param damage Damage to apply
     * @param lifetime Duration in ticks of damage over time effect
     * @param tickPeriod Period in ticks in between damage ticks
     */
    public FireBombAttackTask(AbstractEntity entity, int damage, int lifetime, int tickPeriod, int height, int width) {
        super(entity);
        this.damage = damage;
        this.lifetime = lifetime;
        this.tickPeriod = tickPeriod;
        this.height = height;
        this.width = width;
    }

    /**
     * Spawns explosion entities into the game world.
     */
    private void spawn() {
        for (int x = -1; x < width - 1; x++) {
            for (int y = -1; y < height - 1; y++) {
                Explosion explosion = new Explosion(entity.getCol() + x, entity.getRow() + y,
                        damage / 2, entity.getFaction()); // Tick damage less than initial hit
                explosion.setCombatTask(new ApplyDamageOverTimeTask(explosion, lifetime, tickPeriod));
                GameManager.get().getWorld().addEntity(explosion);
            }
        }
    }

    /**
     * Applies initial big hit damage to all entities within bounds of explosion.
     */
    private void applyInitialDamage() {
        SquareVector origin = new SquareVector(entity.getCol() - 1, entity.getRow() - 1);
        float[] dimensions = WorldUtil.colRowToWorldCords(3, 3);
        BoundingBox bounds = new BoundingBox(origin, dimensions[0], dimensions[1]);
        List<AbstractEntity> collidingEntities = GameManager.get().getWorld().getEntitiesInBounds(bounds);
        if (collidingEntities.size() > 1) { // Own bounding box should always be present
            for (AbstractEntity e : collidingEntities) {
                EntityFaction faction = e.getFaction();
                if (faction != EntityFaction.NONE && faction != entity.getFaction()) {
                    applyDamage(e, damage);
                }
            }
        }
    }

    /**
     * Applies damage to a given entity as per the parent's entity damage value.
     * @param e Enemy entity
     * @param damage Damage to apply
     */
    private void applyDamage (AbstractEntity e, int damage) {
        if (e instanceof Peon) {
            Peon peon = (Peon) e;

            peon.applyDamage(damage, DamageType.FIRE);
        }
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    /**
     * On tick is called periodically (time dependant on the world settings).
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        // Apply initial damage
        applyInitialDamage();

        // Spawn explosions
        spawn();

        // Task complete
        complete = true;
    }
}