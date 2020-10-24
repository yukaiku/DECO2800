package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.PlayerFireball;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

/**
 * Spawns a Fireball into the game world.
 */
public class FireballAttackTask extends RangedAttackTask {
    /**
     * Creates an instance of the FireballAttackTask.
     * @param entity Parent entity of the fireball
     * @param targetCol X position to move towards
     * @param targetRow Y position to move towards
     * @param damage Damage to apply
     * @param speed Speed to move at
     * @param lifetime Lifetime of projectile
     */
        public FireballAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage,
                            float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    /**
     * Spawns a fireball into the game world.
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
        PlayerFireball fireball = new PlayerFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }
}