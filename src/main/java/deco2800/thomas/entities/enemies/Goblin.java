package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;

import java.util.ArrayList;
import java.util.Arrays;

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
        super("Goblin", "goblin_swamp_right", height, speed, health);
    }

    public Goblin(int height, float speed, int health, String texture) {
        this(height, speed, health);
        super.setTextureDirections(new ArrayList<>(Arrays.asList(texture, texture + "_left", texture + "_right")));
        this.setTexture(texture + "_right");
        detectTarget();
    }

    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && EnemyUtil.playerInRadius(this, player, detectRadius)) {
            super.setTarget(GameManager.get().getWorld().getPlayerEntity());
            setMovementTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    private void setGoblinTexture() {
        if (getTarget() != null) {
            if (getTarget().getCol() < this.getCol()) {
                setTexture(getTextureDirection(TEXTURE_LEFT));
            } else {
                setTexture(getTextureDirection(TEXTURE_RIGHT));
            }
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
                setMovementTask(new MovementTask(this, super.getTarget().getPosition()))
                setGoblinTexture();
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
    public Goblin deepCopy() {
        return new Goblin(super.getHeight(), super.getSpeed(), super.getMaxHealth(), super.getTexture());
    }
}
