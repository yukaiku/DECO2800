package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Freeze;
import deco2800.thomas.managers.GameManager;

public class IceBreathTask extends RangedAttackTask {

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
    public IceBreathTask(AbstractEntity entity, float targetCol, float targetRow, int damage, float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        int width = 2;
        for (int i = 0; i < 20; i++) {
            width = width + 2;
            for (int j = 0 - width/2; j < width/2; j++) {
                Freeze freeze = new Freeze(col + i, row + j, damage, faction);
                freeze.setCombatTask(new ApplySlowOnCollisionTask(freeze, 10));
                GameManager.get().getWorld().addEntity(freeze);
            }
        }
    }
}
