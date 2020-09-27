package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Iceball;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

public class IceballAttackTask extends RangedAttackTask {

    private float speedMultiplier;
    private int slowDuration;
    /**
     * Spawns a projectile with specified parameters.
     *
     * @param entity    Parent entity of projectile.
     * @param targetCol X direction to move towards
     * @param targetRow Y direction to move towards
     * @param damage    Damage to apply
     * @param speed     Speed to move at
     * @param lifetime  Lifetime of projectile
     */
    public IceballAttackTask(AbstractEntity entity, float targetCol, float targetRow,
                             int damage, float speed, int lifetime, float speedMultiplier, int slowDuration) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
        this.speedMultiplier = speedMultiplier;
        this.slowDuration = slowDuration;
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        Iceball iceball = new Iceball(col, row, damage, speed, faction);
        iceball.setMovementTask(new DirectProjectileMovementTask(iceball,
                new SquareVector(targetCol, targetRow), lifetime));
        iceball.setCombatTask(new ApplySlowOnCollisionTask(iceball, lifetime, speedMultiplier, slowDuration));
        GameManager.get().getWorld().addEntity(iceball);
    }
}
