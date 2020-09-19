package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.SandTornado;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

public class SandTornadoAttackTask extends RangedAttackTask {

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
    public SandTornadoAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage, float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        SandTornado tornado = new SandTornado(col, row, damage, speed, faction);
        tornado.setMovementTask(new DirectProjectileMovementTask(tornado,
                new SquareVector(targetCol, targetRow), lifetime));
        tornado.setCombatTask(new ApplyTornadoOnCollisionTask(tornado, lifetime));
        GameManager.get().getWorld().addEntity(tornado);
    }
}
