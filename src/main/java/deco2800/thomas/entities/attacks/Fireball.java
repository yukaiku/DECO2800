package deco2800.thomas.entities.attacks;

import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public class Fireball implements Projectile{
    protected float speed;
    private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
    

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

    }

    @Override
    public MovementTask.Direction getMovingDirection() {
        return null;
    }

    @Override
    public void setMovingDirection(MovementTask.Direction movingDirection) {

    }
}
