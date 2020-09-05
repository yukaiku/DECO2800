package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.EnemyUtil;

/**
 * A class that defines an implementation of a minion enemy type called a Goblin.
 * Goblins are special enemies. They are summoned by monsters or bosses and
 * directly target towards players when spawning.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/minions/goblin
 */
public class Goblin extends Minion implements AggressiveEnemy {
    private int tickFollowing = 30;
    private final int detectRadius = 12;

    public Goblin(int height, float speed, int health) {
        super("Goblin", "goblin", height, speed, health);
    }

    public Goblin(int height, float speed, int health, String texture) {
        this(height, speed, health);
        this.setTexture(texture);
    }

    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && EnemyUtil.playerInRadius(this, player, detectRadius)) {
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
