package deco2800.thomas.entities;
import deco2800.thomas.tasks.MovementTask;

/**
 * EnemyPeon is a prototype implementation of an enemy that chases after the
 * player; consider splitting enemyPeon into separate classes later on
 * todo: make the class abstract
 */
public class EnemyPeon extends Peon {

    // Current implementation passes the player object to enemies so they can
    // know the player's position, possible better implementation?
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

    @Override
    public void onTick(long i) {
        // Without tickRate reduction the position is updated too rapidly
        // and the enemy can't move, possible less hacky implementation
        if (tick > 40) {
            // For some reason it chases in straight lines, might be better
            // if it can move in both directions
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
