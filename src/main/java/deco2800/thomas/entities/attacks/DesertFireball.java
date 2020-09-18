package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.environment.desert.DesertQuicksand;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

public class DesertFireball extends Fireball {
    public DesertFireball (float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, damage, speed, faction);
    }

    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        Fireball fireball = new DesertFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }

    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if(movementTask.isComplete() && stateTimer > 9) {
                WorldUtil.removeEntity(this);
            }
            movementTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }
        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                currentState = State.EXPLODING;
                AbstractWorld world = GameManager.get().getWorld();
                Tile tile = world.getTile((float) Math.ceil((this.getCol())),
                        (float) Math.ceil((this.getRow())));
                world.addEntity(new DesertQuicksand(tile));
                tile.setType("Quicksand");
                tile.setStatusEffect(true);
                movementTask.stopTask();
                combatTask.stopTask();
            }
            combatTask.onTick(i);
        }
    }
}
