package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.RangedAttackTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class RangedEntity extends CombatEntity {

    private float speed;
    private float range;
    private SquareVector destination;
    private AbstractWorld world;

    public RangedEntity () {
        super();
    }

    public RangedEntity (float col, float row, int renderOrder, int damage, float speed, int range) {
        super(col, row, renderOrder, damage);
        this.speed = speed;
        this.range = range;
        this.destination = null;
        this.world = GameManager.get().getWorld();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void moveTowards(SquareVector destination) {
        position.moveToward(destination, speed);
    }

    public void setDestination(SquareVector destination) {this.destination = destination;}

    /**
     * Executes a single tick of the RangedEntity.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        // Update movement task
        if (movementTask != null) {
            if( movementTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            movementTask.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }

        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }
    }
}
