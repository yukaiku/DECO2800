package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.StingProjectile;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

/**
 * Spawns a Sting projectile into the game world.
 */
public class ScorpionStingAttackTask extends RangedAttackTask {
    /**
     * Creates an instance of the ScorpionStingAttackTask.
     * @param entity Parent entity of the fireball
     * @param targetCol X position to move towards
     * @param targetRow Y position to move towards
     * @param damage Damage to apply
     * @param speed Speed to move at
     * @param lifetime Lifetime of projectile
     */
    public ScorpionStingAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage,
                              float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    /**
     * Spawns a sting projectile into the game world.
     * @param col Initial x position
     * @param row Initial y position
     * @param targetCol X direction to move towards
     * @param targetRow Y direction to move towards
     * @param damage Damage to apply
     * @param speed Speed to move at
     * @param lifetime Lifetime of projectile
     * @param faction Faction of projectile
     */
    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        StingProjectile projectile = new StingProjectile(col, row, damage, speed, faction);
        projectile.setMovementTask(new DirectProjectileMovementTask(projectile,
                new SquareVector(targetCol, targetRow), lifetime));
        projectile.setCombatTask(new ApplyDamageOnCollisionTask(projectile, lifetime));
        GameManager.get().getWorld().addEntity(projectile);
    }
}