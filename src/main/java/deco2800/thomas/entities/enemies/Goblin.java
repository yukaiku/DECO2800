package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;

/**
 * A class that defines an implementation of a minion enemy type called a Goblin.
 *
 * Goblins are special enemies. They are summoned by monsters or bosses and
 * directly target towards players when spawning.
 */
public class Goblin extends Minion implements AggressiveEnemy {

    private int tickFollowing = 30;

    public Goblin(int height, float speed, int health) {
        super("Goblin", "goblin", height, speed, health);
    }

    public Goblin(int height, float speed, int health, String texture) {
        this(height, speed, health);
        this.setTexture(texture);
    }

    public void detectTarget() {
        int awareRadius = 12;
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && Math.sqrt(Math.pow(Math.round(super.getCol()) - Math.round(player.getCol()), 2) +
                Math.pow(Math.round(super.getRow()) - Math.round(player.getRow()), 2)) < awareRadius) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeSpecialEnemy(this);
    }

    @Override
    public void onTick(long i){
        // update target following path every 0.5 second (30 ticks)
        if (++tickFollowing > 30) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().getPosition()));
            }
            tickFollowing = 0;
        }
        // detect targets every tick
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
        return new Goblin(super.getHeight(), super.getSpeed(), super.getMaxHealth(), super.getTexture());
    }
}
