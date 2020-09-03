package deco2800.thomas.entities.attacks;

import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.RangedAttackTask;
import deco2800.thomas.util.SquareVector;

public class RangedEntity extends CombatEntity {

    private float speed;
    private float range;
    private AbstractTask task;
    private SquareVector destination;

    public RangedEntity () {
        super();
    }

    public RangedEntity (float col, float row, int renderOrder, int damage, float speed, int range) {
        super(col, row, renderOrder, damage);
        this.speed = speed;
        this.range = range;
        this.destination = null;
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

    @Override
    public void onTick(long i) {
        if(task != null) {
            if(task.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            task.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }
    }

    public void setTask(AbstractTask task) {
        this.task = task;
    }

    public AbstractTask getTask() {
        return task;
    }
}
