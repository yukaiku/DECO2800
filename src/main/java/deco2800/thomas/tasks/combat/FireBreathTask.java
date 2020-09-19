package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;

public class FireBreathTask extends RangedAttackTask {

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
    public FireBreathTask(AbstractEntity entity, float targetCol, float targetRow, int damage, float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {

    }
}
