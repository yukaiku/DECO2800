package deco2800.thomas.entities.attacks;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.environment.desert.DesertQuicksand;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.combat.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.movement.DirectProjectileMovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

/**
 * A fireball that additionally drops quicksand on to the world
 * when they reach their target.
 */
public class DesertFireball extends Projectile {
    private boolean droppedQuicksand = false;

    /**
     * Creates an instance of the DesertFireball.
     * @param col Initial x position
     * @param row Initial y position
     * @param damage Base damage to deal
     * @param speed Speed this projectile moves at
     * @param faction Faction this entity belongs to
     */
    public DesertFireball (float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setDamageType(DamageType.SAND_I_GUESS);
    }

    /**
     * Spawns a DesertFireball into the game world.
     * @param col Initial x position
     * @param row Initial y position
     * @param targetCol X position to move towards
     * @param targetRow Y position to move towards
     * @param damage Base damage to deal
     * @param speed Speed this projectile moves at
     * @param lifetime Lifetime of projectile (in ticks)
     * @param faction Faction this entity belongs to
     */
    public static void spawn(float col, float row, float targetCol, float targetRow,
                             int damage, float speed, long lifetime, EntityFaction faction) {
        DesertFireball fireball = new DesertFireball(col, row, damage, speed, faction);
        fireball.setMovementTask(new DirectProjectileMovementTask(fireball,
                new SquareVector(targetCol, targetRow), lifetime));
        fireball.setCombatTask(new ApplyDamageOnCollisionTask(fireball, lifetime));
        GameManager.get().getWorld().addEntity(fireball);
    }

    /**
     * Implements super method, but additionally drops a quicksand tile
     * on position when exploding.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);

        // Drop quick sand
        if (currentState == State.EXPLODING && !droppedQuicksand) {
            AbstractWorld world = GameManager.get().getWorld();
            Tile tile = world.getTile((float) Math.ceil((this.getCol())),
                    (float) Math.ceil((this.getRow())));
            world.addEntity(new DesertQuicksand(tile));
            tile.setType("Quicksand");
            tile.setStatusEffect(true);

            droppedQuicksand = true;
        }
    }
}
