package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;


/**
 * A class that defines an implementation of a minion enemy type
 * called a Goblin.
 */
public class Goblin extends Minion implements AggressiveEnemy {

    private int tick;

    private final int awareRadius;


    public Goblin(int height, float speed, int health) {
        super("Goblin", "goblin", height, speed, health);
        this.tick = 60;
        this.awareRadius = 8;
    }


    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && Math.sqrt(Math.pow(Math.round(super.getCol()) - Math.round(player.getCol()), 2) +
                Math.pow(Math.round(super.getRow()) - Math.round(player.getRow()), 2)) < awareRadius) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    @Override
    public void onTick(long i){
        if (++tick > 60) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().getPosition()));
            }
            tick = 0;
        }
        // detect targets for every tick
        if (super.getTarget() == null) {
            detectTarget();
        }
        // execute tasks
        if (getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);
            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }



    @Override
    public Goblin deepCopy() {
        return new Goblin(super.getHeight(), super.getSpeed(), super.getMaxHealth());
    }
}
