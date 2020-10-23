package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.status.BurnStatus;

import java.util.List;

/**
 * A StingProjectile is a projectile that moves in a straight line until it
 * a) hits an enemy and deals damage, and
 * b) Applies a damage over time effect to the enemy, or
 * c) its lifetime expires.
 */
public class StingProjectile extends Projectile implements Animatable {
    private boolean poisonApplied = false;

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
        this.setDamageType(DamageType.SWAMPY_WATER);
        this.setDefaultState(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("stingProjectile")));
        this.setExplosion(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("stingEffect")));
    }

    /**
     * Runs the projectile update code, and applies a poison if required.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);

        if (combatTask != null && combatTask.isComplete() && !poisonApplied) {
            applyPoison();
            poisonApplied = true;
        }
    }

    /**
     * Called when the projectile hits an enemy. Applies a damage over time
     * effect to the enemy.
     */
    private void applyPoison() {
        List<AbstractEntity> collisionEntities = GameManager.get().getWorld().getEntitiesInBounds(this.getBounds());
        if (collisionEntities.size() > 1) {
            for (AbstractEntity e : collisionEntities) {
                if (e instanceof Peon) {
                    Peon peon = (Peon)e;
                    EntityFaction faction = peon.getFaction();
                    if (faction != EntityFaction.NONE && faction != this.getFaction()) {
                        peon.addEffect(new BurnStatus(peon, this.getDamage(), 10));
                    }
                }
            }
        }
    }
}
