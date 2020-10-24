package deco2800.thomas.entities.attacks;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

public class VolcanoFireball extends Fireball {
    public VolcanoFireball(float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, damage, speed, faction);
        this.setDamageType(DamageType.FIRE);
    }

    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        Fireball fireball = new VolcanoFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }
}
