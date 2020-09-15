package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.StatusEffectManager;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;

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
    // Range at which the goblin will attempt to melee attack the player
    private int attackRange = 2;

    public Goblin(int height, float speed, int health) {
        super("Goblin", "goblin_swamp_right", height, speed, health);
    }

    public Goblin(int height, float speed, int health, String texture) {
        this(height, speed, health);
        super.setTextureDirections(new ArrayList<>(Arrays.asList(texture, texture + "_left", texture + "_right")));
        this.setTexture(texture + "_right");
        detectTarget();
    }

    /**
     * Locks onto the player and begins to pursue once it has been spawned
     * by another enemy
     */
    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null) {
            super.setTarget(GameManager.get().getWorld().getPlayerEntity());
            setMovementTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    /**
     * Sets the appropriate texture based on the direction the goblin
     * is moving
     */
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
    public void attackPlayer() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange)) {
            SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
            setCombatTask(new MeleeAttackTask(this, origin, 1, 1, 5));
        }
    }

    @Override
    public void onTick(long i){
        // update target following path every 0.5 second (30 ticks)
        if (++tickFollowing > 30) {
            if (super.getTarget() != null) {
                setMovementTask(new MovementTask(this, super.getTarget().getPosition()));
                setGoblinTexture();
                attackPlayer();
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
        if (getCombatTask() != null && getCombatTask().isAlive()) {
            getCombatTask().onTick(i);
            if (getCombatTask().isComplete()) {
                setCombatTask(null);
            }
        }

        // isAttacked animation
        if (isAttacked && --isAttackedCoolDown < 0) {
            isAttacked = false;
        }
    }

    @Override
    public Goblin deepCopy() {
        return new Goblin(super.getHeight(), super.getSpeed(), super.getMaxHealth(), super.getTexture());
    }
}
