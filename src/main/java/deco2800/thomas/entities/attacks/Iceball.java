package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.Tickable;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.tasks.status.SpeedStatus;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.List;

/**
 * An iceball projectile that applies a slow on collision with an enemy.
 */
public class Iceball extends Projectile implements Animatable, Tickable {
    private boolean slowApplied = false;
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
        this.setDamageType(DamageType.ICE);
        this.setDefaultState(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballDefault")));
        this.setExplosion(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("iceballExplosion")));
    }

    /**
     * Updates the projectile, and removes it from world if the explosion is over.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);

        if (combatTask.isComplete()) {
            if (!slowApplied) {
                applySlow();
                slowApplied = true;
            }
            if (stateTimer > explosion.getAnimationDuration()) {
                WorldUtil.removeEntity(this);
            }
        }
    }

    /**
     * Spawns a new iceball.
     * @param col Initial x position
     * @param row Initial y position
     * @param targetCol Target x position
     * @param targetRow Target y position
     * @param damage Damage to apply
     * @param speed Speed of projectile
     * @param lifetime Lifetime of projectile
     * @param faction Faction of projectile
     */
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
                    if (faction != EntityFaction.NONE && faction != this.getFaction()) {
                        peon.addEffect(new SpeedStatus(peon, 0.3f, 5));
                        peon.applyDamage(this.getDamage(), DamageType.ICE);
                        this.setDirection(0f); // Ice spikes should always face up.
                    }
                }
            }
        }
    }
}
