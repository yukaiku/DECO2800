package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;

/**
 * A class that defines an implementation of a Dragon.
 * Dragons are bosses and they need to be manually initialised (using constructor or setBoss())
 * and spawned (using spawnBoss()) inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/bosses/dragon
 */
public class Dragon extends Boss implements PassiveEnemy {
    private int tickFollowing = 60;

    public Dragon(int height, float speed, int health) {
        super("Elder Dragon", "elder_dragon", height, speed, health);
    }

    public void summonGoblin() {
        Goblin goblin = new Goblin(1, 0.1f, 30);
        GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(goblin, this.getCol() + 1, this.getRow() + 2);
    }

    @Override
    public void reduceHealth(int damage) {
        this.getHealthTracker().reduceHealth(damage);
        if (isDead()) {
            death();
        }
        hitByTarget();
    }

    public void hitByTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null) {
            super.setTarget((PlayerPeon) player);
            setMovementTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing > 60) {
            if (super.getTarget() != null) {
                summonGoblin();
                Fireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                        getTarget().getRow(), 10, 0.2f, 60, EntityFaction.Evil);
                setMovementTask(new MovementTask(this, super.getTarget().
                        getPosition()));
            }
            tickFollowing = 0;
        }
        // execute tasks
        if (getMovementTask() != null && getMovementTask().isAlive()) {
            getMovementTask().onTick(i);
            if (getMovementTask().isComplete()) {
                setMovementTask(null);
            }
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeBoss();
    }
}
