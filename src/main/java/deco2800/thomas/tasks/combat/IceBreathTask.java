package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.attacks.Freeze;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;

public class IceBreathTask extends AbstractTask {
    /* How many ticks between spawning the next row of ice */
    private static final int TICKS_BETWEEN_SPAWN = 7;
    /* Maximum length of ice breath. */
    private static final int MAX_LENGTH = 11;
    /* Initial width of ice */
    private int width = 1;
    private int tickCounter = 0;
    private int length = 0;
    private int deltaX = 0;
    private int deltaY = 0;
    private float originX;
    private float originY;

    private final int damage;
    private final float speedMultiplier;
    private final int slowDuration;

    // Task tracker
    private boolean complete = false;

    /**
     * Spawns a projectile with specified parameters.
     *
     * @param entity    Parent entity of projectile.
     * @param targetCol X direction to move towards
     * @param targetRow Y direction to move towards
     * @param damage    Damage to apply
     */
    public IceBreathTask(AbstractEntity entity, float targetCol, float targetRow,
                         int damage, float speedMultiplier, int slowDuration) {
        super(entity);
        this.damage = damage;
        this.speedMultiplier = speedMultiplier;
        this.slowDuration = slowDuration;
        this.originX = entity.getCol() + 2;
        this.originY = entity.getRow() + 2;

        // Determine direction to attack
        double angle = Math.toDegrees(Math.atan2(targetCol - entity.getCol(), targetRow - entity.getRow()));
        if (angle > -45 && angle < 45) {
            // Spawn above entity
            this.deltaY = 1;
            this.originY += 2;
        } else if (angle >= -135 && angle <= -45) {
            // Spawn to left of entity
            this.deltaX = -1;
            this.originX -= 2;
        } else if (angle < -135 || angle > 135) {
            // Spawn below entity
            deltaY = -1;
            this.originY -= 2;
        } else {
            // Spawn right of entity
            deltaX = 1;
            this.originX += 2;
        }
    }

    /**
     * Returns whether the task is complete.
     * @return True if task is complete.
     */
    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * Returns whether the task is alive.
     * @return True if task is alive.
     */
    @Override
    public boolean isAlive() {
        return true;
    }

    /**
     * Spawns a Freeze entity.
     * @param col X position of freeze entity
     * @param row Y position of freeze entity
     */
    private void spawn(float col, float row) {
        float direction = (float)Math.toDegrees(Math.atan2(deltaY, deltaX));
        Freeze freeze = new Freeze(col, row, this.damage, this.entity.getFaction(), direction);
        freeze.setCombatTask(new ApplySlowOnCollisionTask(freeze, 1, speedMultiplier, slowDuration));
        GameManager.get().getWorld().addEntity(freeze);
    }

    /**
     * Periodically spawns Freeze entities in direction of Ice Breath.
     * @param ticks
     */
    @Override
    public void onTick(long ticks) {
        if (--tickCounter <= 0) {
            tickCounter = TICKS_BETWEEN_SPAWN;

            // Increase width every 3 cells
            if (++length % 3 == 0) {
                width += 2;
            }

            // Spawn freeze entities
            for (int i = -width / 2; i <= width / 2; i++) {
                float col = originX;
                float row = originY;
                if (deltaX == 0) {
                    col += i;
                    row += deltaY * length;
                } else {
                    row += i;
                    col += deltaX * length;
                }
                this.spawn(col, row);
            }

            // Complete task when max length is reached.
            if (length >= MAX_LENGTH) {
                complete = true;
            }
        }
    }
}
