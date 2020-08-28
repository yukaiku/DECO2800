package deco2800.thomas.entities;


import deco2800.thomas.tasks.MovementTask;

/**
 * A class that defines an implementation of an enemy
 * called an Orc.
 */
public class Orc extends EnemyPeon implements AggressiveEnemy {

    private int tick;

    public Orc(int height, float speed, int health, Peon target) {
        super("Orc", "spacman_blue", height, speed, health, target);
        this.tick = 0;
        setTask(new MovementTask(this, super.getTarget().position));
    }

    @Override
    public void onTick(long i) {
        if (tick > 40) {
            setTask(new MovementTask(this, super.getTarget().position));
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
