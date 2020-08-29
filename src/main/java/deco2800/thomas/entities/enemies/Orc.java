package deco2800.thomas.entities.enemies;


import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.Peon;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

/**
 * A class that defines an implementation of an enemy
 * called an Orc.
 */
public class Orc extends Monster implements AggressiveEnemy {

    private int tick;

    public Orc(int height, float speed, int health) {
        super("Orc", "spacman_blue", height, speed, health);
        this.tick = 60;
    }

    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && (Math.abs(Math.round(super.getCol()) - Math.round(player.getCol()))
                + Math.abs(Math.round(super.getRow()) - Math.round(player.getRow())) < 12)) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    @Override
    public void onTick(long i) {
        if (tick > 60) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().getPosition()));
            }
            tick = 0;
        } else {
            tick++;
        }

        if (super.getTarget() == null) {
            detectTarget();
        }

        if (getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);

            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }
}
