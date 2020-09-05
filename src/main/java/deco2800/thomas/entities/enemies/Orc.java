package deco2800.thomas.entities.enemies;


import com.badlogic.gdx.Game;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.EnemyUtil;

import java.util.Random;

/**
 * A class that defines an implementation of an orc.
 * Orcs are wild enemies. They can be automatically spawned using EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/monsters/orc
 */
public class Orc extends Monster implements AggressiveEnemy {

    private int tickFollowing = 30;
    private int tickDetecting = 15;
    private int tickSummon = 0;
    private int nextTickSummon = 300;
    private final Random random;

    private final int detectRadius = 8;
    private final int discardRadius = 12;
    private String leftTexture;
    private String rightTexture;

    public Orc(int height, float speed, int health) {
        super("Orc", "orc_swamp_left", height, speed, health, true);
        this.random = new Random();
    }

    /**
     * Initialise an orc with custom textures (for different variations)
     */
    public Orc(int height, float speed, int health, String texture) {
        this(height, speed, health);
        leftTexture = texture + "_left";
        rightTexture = texture + "_right";
        this.setTexture(rightTexture);
    }

    /**
     * Detects the target with the given aware radius.
     */
    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && EnemyUtil.playerInRadius(this, player,
                detectRadius)) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }

    /**
     * Stops targeting the player once they leave the awareness
     */
    public void discardTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && !EnemyUtil.playerInRadius(this, player,
                discardRadius)) {
            super.setTarget(null);
            setTask(null);
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
    }

    private void setOrcTexture() {
        if (getTarget() != null) {
            if (getTarget().getCol() < this.getCol()) {
                setTexture(leftTexture);
            } else {
                setTexture(rightTexture);
            }
        }
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing > 60) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().
                        getPosition()));
                setOrcTexture();
            }
            tickFollowing = 0;
        }
        // checks player position every 0.25 second (15 ticks)
        if (++tickDetecting > 15) {
            if (super.getTarget() == null) {
                detectTarget();
            } else {
                discardTarget();
            }
            tickDetecting = 0;
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
    public Orc deepCopy() {
        return new Orc(super.getHeight(), super.getSpeed(),
                super.getMaxHealth(), super.getTexture());
    }
}
