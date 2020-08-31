package deco2800.thomas.entities.attacks;

import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public interface Projectile {
    public float getSpeed();
    public void setSpeed( float speed);
    public void moveTowards(SquareVector destination);
    public MovementTask.Direction getMovingDirection();
    public void setMovingDirection(MovementTask.Direction movingDirection);
}
