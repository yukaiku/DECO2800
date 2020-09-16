package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.status.BurnStatus;
import deco2800.thomas.util.WorldUtil;

import java.util.List;

/**
 * A StingProjectile is a projectile that moves in a straight line until it
 * a) hits an enemy and deals damage, and
 * b) Applies a damage over time effect to the enemy, or
 * c) its lifetime expires.
 */
public class StingProjectile extends Projectile implements Animatable {
    private final Animation<TextureRegion> animation;
    /**
     * Default constructor, sets texture and object name.
     */
    public StingProjectile() {
        super();
        this.setTexture("fireball_right");
        this.setObjectName("combatStingProjectile");
        animation = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballDefault"));

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
    public StingProjectile (float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setTexture("fireball_right");
        this.setObjectName("combatStingProjectile");
        animation = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballDefault"));
    }

    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if(movementTask.isComplete()) {
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
            } else {
                combatTask.onTick(i);
            }
        } else {
            WorldUtil.removeEntity(this);
        }
    }

    /** When this entity is destroyed, it should attempt to apply a
     * damage over time effect to any enemy entities within its bounds.
     */
    @Override
    public void dispose() {
        List<AbstractEntity> collisionEntities = GameManager.get().getWorld().getEntitiesInBounds(this.getBounds());
        if (collisionEntities.size() > 0) {
            for (AbstractEntity entity : collisionEntities) {
                EntityFaction faction = entity.getFaction();
                if (faction != EntityFaction.None && faction != this.getFaction()) {
                    if (entity instanceof Peon) {
                        Peon peon = (Peon)entity;
                        peon.addEffect(new BurnStatus(peon));
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
        return animation.getKeyFrame(0);
    }
}
