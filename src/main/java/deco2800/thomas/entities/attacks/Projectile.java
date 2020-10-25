package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.Tickable;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

/**
 * A projectile is an entity that moves over distance and applies
 * damage when it reaches its destination. This is a base class that should be extended
 * for use in game. See Fireball as an example.
 */
public class Projectile extends CombatEntity implements Animatable, Tickable {
    /* Enum containing the possible states of this class for animations. */
    public enum State {
        DEFAULT, EXPLODING
    }

    // Speed projectile moves at
    private float speed;
    // Direction (in degrees) projectile is moving
    private float direction;

    /* The current state of this entity*/
    protected Projectile.State currentState;
    /* Animation for when this entity is exploding */
    protected Animation<TextureRegion> explosion;

    /* Default animation */
    protected Animation<TextureRegion> defaultState;
    /* The current timer on this class */
    protected float stateTimer = 0;

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
        super(col, row, renderOrder, damage, faction, DamageType.COMMON);
        this.speed = speed;
        this.direction = 0;

        // Default animations to fireball
        this.setObjectName("combatProjectile");
        this.setTexture("fireball_right");

        explosion = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballExplosion"));
        defaultState = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballDefault"));
        currentState = Projectile.State.DEFAULT;
    }

    /**
     * Sets the explosion animation for this projectile.
     * @param explosion Animation to use for explosion.
     */
    public void setExplosion(Animation<TextureRegion> explosion) {
        this.explosion = explosion;
    }

    /**
     * Sets the defaultState animation for this projectile.
     * @param defaultState Animation to use for default state.
     */
    public void setDefaultState(Animation<TextureRegion> defaultState) {
        this.defaultState = defaultState;
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
        if (movementTask != null && !movementTask.isComplete()) {
            movementTask.onTick(i);
        }

        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete() && currentState == State.DEFAULT) {
                currentState = Projectile.State.EXPLODING;
                stateTimer = 0;
                if (movementTask != null) {
                    movementTask.stopTask();
                }
                combatTask.stopTask();
            } else if (currentState == State.EXPLODING && stateTimer > 9) {
                WorldUtil.removeEntity(this);
            } else {
                combatTask.onTick(i);
            }
        }
    }

    /**
     * Returns the current animation frame to draw representing this projectile.
     * @param delta the interval of the ticks
     * @return TextureRegion to draw.
     */
    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        // Get the animation frame based on the current state
        if (currentState == Projectile.State.EXPLODING) {
            region = explosion.getKeyFrame(stateTimer);
        } else {
            if (stateTimer >= defaultState.getAnimationDuration()) {
                stateTimer = 0;
            }
            region = defaultState.getKeyFrame(stateTimer);
        }
        stateTimer = stateTimer + delta;
        return region;
    }
}
