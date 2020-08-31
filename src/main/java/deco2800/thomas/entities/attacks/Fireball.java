package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public class Fireball extends CombatEntity implements Projectile {
    protected float speed;
    private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;

    public Fireball (float row, float col, int damage, float speed) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, speed);
        this.setObjectName("Fireball");
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
}
