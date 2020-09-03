package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.DirectProjectileMovementTask;
import deco2800.thomas.tasks.RangedAttackTask;
import deco2800.thomas.util.SquareVector;

public class Fireball extends RangedEntity implements Tickable{
    protected float speed;
    private transient RangedAttackTask task;

    public Fireball() {
        super();
        this.setTexture("projectile");
        this.setObjectName("combatFireball");
        this.setHeight(1);
        this.speed = 0.05f;
    }

    public Fireball (float row, float col, int damage, float speed, int range) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, speed, range);
        this.setObjectName("combatFireball");
        this.setTexture("projectile");
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
        Fireball fireball = new Fireball(col, row, damage, speed, 0);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball));

        GameManager.get().getWorld().addEntity(fireball);
    }
}
