package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.EnemyUtil;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Dragon(int height, float speed, int health, String texture) {
        this(height, speed, health);
        super.setTextureDirections(new ArrayList<>(Arrays.asList(texture, texture + "_left", texture + "_right")));
        this.setTexture(texture + "_left");
    }

    public void summonGoblin() {
        if (GameManager.get().getManager(EnemyManager.class).getSpecialEnemiesAlive().size() < 10) {
            Goblin goblin = new Goblin(1, 0.1f, 20, "goblin" + getTextureDirection(TEXTURE_BASE).substring(6));
            GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(goblin, this.getCol() + 1, this.getRow() + 2);
        }
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
            setTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }
    private void setDragonTexture() {
        if (getTarget() != null) {
            if (getTarget().getCol() < this.getCol()) {
                setTexture(getTextureDirection(TEXTURE_LEFT));
            } else {
                setTexture(getTextureDirection(TEXTURE_RIGHT));
            }
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
                setTask(new MovementTask(this, super.getTarget().
                        getPosition()));
                setDragonTexture();
            }
            tickFollowing = 0;
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
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeBoss();
    }
}
