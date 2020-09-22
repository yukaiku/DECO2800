package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

/**
 * A projectile is an entity that moves over distance and applies
 * damage when it reaches its destination. This is a base class that should be extended
 * for use in game. See Fireball as an example.
 */
public class Projectile extends CombatEntity {
    // Speed projectile moves at
    private float speed;
    // Direction (in degrees) projectile is moving
    private float direction;

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
        this.direction = 0;
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
     * Returns the direction the projectile is facing in degrees.
     * Note: 0 degrees = towards the right.
     * @return Direction of projectile.
     */
    public float getDirection() {
        return direction;
    }

    /**
     * Sets the direction the projectile is facing. Will be overwritten by movement.
     * @param direction Direction to face
     */
    public void setDirection(float direction) {
        this.direction = direction;
    }

    /**
     * Updates the position of this projectile, by moving towards a given SquareVector.
     * @param destination Vector to step towards.
     */
    public void moveTowards(SquareVector destination) {
        // Update direction
        direction = (float)Math.toDegrees(
                Math.atan2(destination.getRow() - position.getRow(), destination.getCol() - position.getCol()));
        // Move vector
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
                WorldUtil.removeEntity(this);
            }
            movementTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }

        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                WorldUtil.removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }
    }
}
