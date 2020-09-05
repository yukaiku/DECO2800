package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;

/**
 * A projectile is an entity that moves over distance and applies
 * damage when it reaches its destination. This is a base class that should be extended
 * for use in game. See Fireball as an example.
 */
public class Projectile extends CombatEntity {
    // Speed projectile moves at
    private float speed;
    // Reference to current game world
    private AbstractWorld world;

    /**
     * Default constructor, just an empty projectile.
     */
    public Projectile() {
        super();
    }

    /**
     * Creates a projectile with position, renderOrder, damage and movement speed.
     * @param col Initial X position
     * @param row Initial Y position
     * @param renderOrder Render order
     * @param damage Damage to apply
     * @param speed Speed projectile moves at
     * @param faction EntityFaction of the projectile
     */
    public Projectile(float col, float row, int renderOrder, int damage, float speed, EntityFaction faction) {
        super(col, row, renderOrder, damage, faction);
        this.speed = speed;
        this.world = GameManager.get().getWorld();
    }

    /**
     * Get the speed the projectile moves at.
     * @return Speed of projectile.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed the projectile moves at.
     * @param speed New speed for the projectile.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Updates the position of this projectile, by moving towards a given SquareVector.
     * @param destination Vector to step towards.
     */
    public void moveTowards(SquareVector destination) {
        position.moveToward(destination, speed);
    }

    /**
     * Executes a single tick of the RangedEntity.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if( movementTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            movementTask.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }

        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }
    }
}
