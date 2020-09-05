package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.worlds.AbstractWorld;

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

    public static void spawn(MovementTask.Direction direction, int damage, long lifetime, EntityFaction faction) {
        AbstractWorld world = GameManager.get().getWorld();

        switch (direction) {
            case UP:
                Sword swordUp = new Sword(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() + 1, damage, lifetime, faction);
                swordUp.setCombatTask(new ApplyDamageOnCollisionTask(swordUp, lifetime));
                swordUp.setTexture("fireball_right");
                GameManager.get().getWorld().addEntity(swordUp);
                break;
            case DOWN:
                Sword swordDown = new Sword(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() - 1, damage, lifetime, faction);
                swordDown.setCombatTask(new ApplyDamageOnCollisionTask(swordDown, lifetime));
                swordDown.setTexture("fireball_left");
                GameManager.get().getWorld().addEntity(swordDown);
                break;
            case LEFT:
                Sword swordLeft = new Sword(world.getPlayerEntity().getCol() - 1, world.getPlayerEntity().getRow(), damage, lifetime, faction);
                swordLeft.setCombatTask(new ApplyDamageOnCollisionTask(swordLeft, lifetime));
                swordLeft.setTexture("fireball_left");
                GameManager.get().getWorld().addEntity(swordLeft);
                break;
            case RIGHT:
                Sword swordRight = new Sword(world.getPlayerEntity().getCol() + 1, world.getPlayerEntity().getRow(), damage, lifetime, faction);
                swordRight.setCombatTask(new ApplyDamageOnCollisionTask(swordRight, lifetime));
                swordRight.setTexture("fireball_right");
                GameManager.get().getWorld().addEntity(swordRight);
                break;
        }




    }

}
