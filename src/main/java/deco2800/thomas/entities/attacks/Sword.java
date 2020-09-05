package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.ApplyDamageOnCollisionTask;

public class Sword extends MeleeEntity implements Tickable {

    public Sword() {
        super();
        this.setTexture("projectile");
        this.setObjectName("combatSword");
        this.setHeight(1);
    }

    public Sword (float row, float col, int damage, long lifetime, EntityFaction faction) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, lifetime, faction);
        this.setObjectName("combatSword");
        this.setTexture("fireball_right");
    }

    public static void spawn(float col, float row, int damage, long lifetime, EntityFaction faction) {
        Sword sword = new Sword(col, row, damage, lifetime, faction);
        sword.setCombatTask(new ApplyDamageOnCollisionTask(sword, lifetime));

        GameManager.get().getWorld().addEntity(sword);
    }

}
