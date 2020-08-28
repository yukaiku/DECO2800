package deco2800.thomas.entities;

import deco2800.thomas.tasks.MovementTask;

/**
 An abstract class inheriting from Peon that will define the
 behaviour of all enemies. All enemies must have some form of health.
 **/
public abstract class EnemyPeon extends Peon implements HasHealth {

    private PlayerPeon target;
    private int tick;

    public EnemyPeon(PlayerPeon target, float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("EnemyPeon");
        this.setTexture("spacman_blue");
        this.setHeight(1);
        this.target = target;
        this.tick = 0;

        setTask(new MovementTask(this, this.target.position));
    }

    public int getHealth() {
        return 0;
    }

    public void setHealth(int newHealth) {
    }

    @Override
    public void onTick(long i) {
        if (tick > 40) {
            setTask(new MovementTask(this, target.position));
            tick = 0;
        } else {
            tick++;
        }

        if (getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);

            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }
}
