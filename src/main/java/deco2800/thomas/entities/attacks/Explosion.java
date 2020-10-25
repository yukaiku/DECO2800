package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.WorldUtil;

/**
 * An explosion is a small explosion, and temporary burning on the ground
 * that some skills can spawn. It deals damage by:
 * a) Applying damage when it spawns, and
 * b) Applying damage over time until its lifetime is over
 */
public class Explosion extends CombatEntity implements Animatable {
    private final Animation<TextureRegion> animationFrames;
    private float stateTimer = 0f;

    /**
     * Parametric constructor, that sets the initial conditions of the explosion
     * as well as texture and name.
     * @param col X position
     * @param row Y position
     * @param damage Damage to apply on impact
     * @param faction EntityFaction of the explosion
     */
    public Explosion (float col, float row, int damage, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, faction, DamageType.COMMON);
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
        this.setDamageType(DamageType.FIRE);
        animationFrames = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballExplosion"));
    }

    /**
     * Executes a single tick of the Explosion.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
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

    /**
     * Get the texture region of current animation keyframe (this will be called in Renderer3D.java).
     *
     * @param delta the interval of the ticks
     * @return the current texture region in animation
     */
    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return animationFrames.getKeyFrame(stateTimer);
    }
}
