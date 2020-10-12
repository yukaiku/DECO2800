package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;

/**
 * The player's fireball attack.
 */
public class PlayerFireball extends Projectile {
    /**
     * Creates an instance of the PlayerFireball.
     * @param col Initial x position
     * @param row Initial y position
     * @param damage Damage to apply
     * @param speed Speed of projectile
     * @param faction Faction of projectile
     */
    public PlayerFireball(float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setDamageType(DamageType.FIRE);
        this.setExplosion(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerFireball")));
        this.setDefaultState(new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerFireballDefault")));
    }

    /**
     * Spawns a new PlayerFireball entity in the world.
     * @param col Initial x position
     * @param row Initial y position
     * @param targetCol Target x position
     * @param targetRow Target y position
     * @param damage Damage to apply
     * @param speed Speed of projectile
     * @param lifetime Lifetime of projectile
     * @param faction Faction of projectile
     */
    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        PlayerFireball fireball = new PlayerFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }
}
