package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

/**
 * A fireball is a projectile that moves in a straight line until it
 * a) hits an enemy and deals damage, or
 * b) its lifetime expires.
 */
public class Fireball extends Projectile implements Tickable{
    /**
     * Default constructor, sets texture and object name.
     */
    public Fireball() {
        super();
        this.setTexture("fireball_left");
        this.setObjectName("combatFireball");
    }

    /**
     * Parametric constructor, that sets the initial conditions of the projectile
     * as well as texture and name.
     * @param col Initial X position
     * @param row Initial Y position
     * @param damage Damage to apply on impact
     * @param speed Speed of projectile
     */
    public Fireball (float col, float row, int damage, float speed) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed);
        this.setObjectName("combatFireball");
        this.setTexture("fireball_left");
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
     */
    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime) {
        Fireball fireball = new Fireball(col, row, damage, speed);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball));

        GameManager.get().getWorld().addEntity(fireball);
    }
}
