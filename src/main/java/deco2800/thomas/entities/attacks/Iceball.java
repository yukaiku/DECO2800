package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.List;

public class Iceball extends Projectile implements Animatable {

    private final Animation<TextureRegion> animation;
    /* Enum containing the possible states of this class*/
    public enum State {
        DEFAULT, EXPLODING
    }

    /* The current state of this entity*/
    public Fireball.State currentState;
    /* Animation for when this entity is exploding */
    private final Animation<TextureRegion> explosion;
    /* Default animation */
    private final Animation<TextureRegion> defaultState;
    /* The current timer on this class */
    private float stateTimer = 0;

    public Iceball() {
        super();
        this.setTexture("fireball_right");
        this.setObjectName("combatIceballProjectile");
        animation = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballDefault"));
        explosion = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballExplosion"));
        defaultState = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballDefault"));
        currentState = Fireball.State.DEFAULT;
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
    public Iceball (float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setTexture("fireball_right");
        this.setObjectName("combatIceballProjectile");
        animation = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballDefault"));
        explosion = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballExplosion"));
        defaultState = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballDefault"));
        currentState = Fireball.State.DEFAULT;
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
                if (!movementTask.isComplete()) {
                    applySlow();
                }
                currentState = Fireball.State.EXPLODING;
                movementTask.stopTask();
            }
            combatTask.onTick(i);
        }
    }

    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        Iceball iceball = new Iceball(col, row, damage, speed, faction);
        iceball.setMovementTask(new DirectProjectileMovementTask(iceball,
                new SquareVector(targetCol, targetRow), lifetime));
        iceball.setCombatTask(new ApplyDamageOnCollisionTask(iceball, lifetime));
        GameManager.get().getWorld().addEntity(iceball);
    }

    /**
     * Called when the projectile hits an enemy. Applies a slow to the enemy,
     * and does an amount of damage.
     */
    private void applySlow() {
        List<AbstractEntity> collisionEntities = GameManager.get().getWorld().getEntitiesInBounds(this.getBounds());
        if (collisionEntities.size() > 1) {
            for (AbstractEntity e : collisionEntities) {
                if (e instanceof Peon) {
                    Peon peon = (Peon)e;
                    EntityFaction faction = peon.getFaction();
                    if (faction != EntityFaction.None && faction != this.getFaction()) {
                        peon.addEffect(new SpeedStatus(peon, 0.3f, 5));
                        peon.applyDamage(this.getDamage(), DamageType.ICE);
                        this.setDirection(0f); // Ice spikes should always face up.
                    }
                }
            }
        }
    }

    /**
     * Get the texture region of current animation keyframe (this will be called in Renderer3D.java).
     *
     * @param delta the interval of the ticks
     * @return the current texture region in animation
     */
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
