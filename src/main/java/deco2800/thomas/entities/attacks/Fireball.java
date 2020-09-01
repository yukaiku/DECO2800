package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public class Fireball extends CombatEntity implements Projectile, Tickable {
    protected float speed;
    private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
    private transient AbstractTask task;

    public Fireball() {
        super();
        this.setTexture("projectile");
        this.setObjectName("Peon");
        this.setHeight(1);
        this.speed = 0.05f;
    }

    public Fireball (float row, float col, int damage, float speed) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, speed);
        this.setObjectName("combatFireball");
        this.setTexture("projectile");
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void moveTowards(SquareVector destination) {
        this.getPosition().moveToward(destination, speed);
    }

    @Override
    public MovementTask.Direction getMovingDirection() {
        return movingDirection;
    }

    @Override
    public void setMovingDirection(MovementTask.Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    @Override
    public void onTick(long i) {
        if(task != null && task.isAlive()) {
            if(task.isComplete()) {
                this.task = GameManager.getManagerFromInstance(TaskPool.class).getCombatTask(this);
            }
            task.onTick(i);
        } else {
            task = GameManager.getManagerFromInstance(TaskPool.class).getCombatTask(this);
        }
    }
}
