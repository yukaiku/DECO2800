package deco2800.thomas.entities;
import deco2800.thomas.tasks.MovementTask;

/**
 * EnemyPeon is a prototype implementation of an enemy that chases after the
 * player; consider splitting enemyPeon into seperate classes later on
 */
public class EnemyPeon extends Peon {

    // Current implementation passes the player object to enemies so they can
    // know the player's posiiton, possible better implementation?
    private PlayerPeon player;
    private int reduceTickRate;

    public EnemyPeon (PlayerPeon playerPeon) {
        super();
        this.setObjectName("EnemyPeon");
        this.setHeight(1);
        this.speed = 0.04f;
        this.setTexture("spacman_blue");

        player = playerPeon;
        setTask(new MovementTask(this, player.position));
    }

    @Override
    public void onTick(long i) {
        // Without tickRate reduction the position is updated too rapidly
        // and the enemy can't move, possible less hacky implementation
        if(reduceTickRate > 50) {
            //For some reason it chases in straight lines, might be better
            // if it can move in both directions
            setTask(new MovementTask(this, player.position));
            reduceTickRate = 0;
        } else {
            reduceTickRate = reduceTickRate + 1;
        }

        if(getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);

            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }
}
