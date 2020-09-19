package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

public class PlayerFireball extends Fireball {
    public PlayerFireball(float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, damage, speed, faction);
        explosion = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerFireball"));
        defaultState = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerFireballDefault"));
    }

    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if(movementTask.isComplete() && stateTimer > 5) {
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
                movementTask.stopTask();
                combatTask.stopTask();
            }
            combatTask.onTick(i);
        }
    }
    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        Fireball fireball = new PlayerFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }
}
