package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.Tickable;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

/**
 * A fireball is a projectile that moves in a straight line until it
 * a) hits an enemy and deals damage, or
 * b) its lifetime expires.
 */
public class Fireball extends Projectile implements Animatable, Tickable{

    public enum State {
        DEFAULT, EXPLODING
    }
    public Fireball.State currentState;
    private final Animation<TextureRegion> explosion;
    private final Animation<TextureRegion> defaultState;
    private float stateTimer = 0;
    private int duration = 0;
    /**
     * Default constructor, sets texture and object name.
     */
    public Fireball() {
        super();
        this.setTexture("fireball_right");
        this.setObjectName("combatFireball");
        explosion = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballExplosion"));
        defaultState = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballDefault"));
        currentState = State.DEFAULT;
    }

    /**
     * Parametric constructor, that sets the initial conditions of the projectile
     * as well as texture and name.
     * @param col Initial X position
     * @param row Initial Y position
     * @param damage Damage to apply on impact
     * @param speed Speed of projectile
     * @param faction EntityFaction of the projectile
     */
    public Fireball (float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setObjectName("combatFireball");
        this.setTexture("fireball_right");

        explosion = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballExplosion"));
        defaultState = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballDefault"));
        currentState = State.DEFAULT;
    }

    /**
     * Spawns a new fireball into the game world, that will move in the direction
     * specified. Note, the fireball will continue in the same direction if it passes
     * the target.
     * @param col X position to spawn at
     * @param row Y position to spawn at
     * @param targetCol X position to move towards
     * @param targetRow Y position to move towards
     * @param damage Damage to apply to enemies
     * @param speed Speed of projectile
     * @param lifetime Lifetime (in ticks) of projectile
     * @param faction EntityFaction of projectile
     */
    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        Fireball fireball = new Fireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }

    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if(movementTask.isComplete() && stateTimer > 9) {
                WorldUtil.removeEntity(this);
            }
            movementTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }
        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                currentState = State.EXPLODING;
                movementTask.stopTask();
            }
            combatTask.onTick(i);
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        // Get the animation frame based on the current state
        switch (currentState) {
            case EXPLODING:
                region = explosion.getKeyFrame(stateTimer);
                break;
            default:
                stateTimer = 0;
                region = defaultState.getKeyFrame(stateTimer);
                break;
        }
        stateTimer = stateTimer + delta;
        return region;
    }
}
