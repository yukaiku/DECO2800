package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

public class DesertFireballAttackTask extends RangedAttackTask {

    public DesertFireballAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage, float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        DesertFireball fireball = new DesertFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }
}
