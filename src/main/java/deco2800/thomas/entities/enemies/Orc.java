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
 * A class that defines an implementation of an orc.
 * Orcs are wild enemies. They can be automatically spawned using EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/monsters/orc
 */
public class Orc extends Monster implements AggressiveEnemy {

    private int tickFollowing = 30;
    private int tickDetecting = 15;

    // Range at which the orc will begin to chase the player
    private final int detectRadius = 8;
    // Range at which the orc will stop chasing the player
    private final int discardRadius = 12;
    // Range at which the orc will attempt to melee attack the player
    private int attackRange = 2;

    public Orc(int height, float speed, int health) {
        super("Orc", "orc_swamp_right", height, speed, health, true);
    }

    /**
     * Initialise an orc with custom textures (for different variations)
     */
    public Orc(int height, float speed, int health, String texture) {
        this(height, speed, health);
        super.setTextureDirections(new ArrayList<>(Arrays.asList(texture, texture + "_left", texture + "_right")));
        this.setTexture(texture + "_right");
    }

    /**
     * Detects the target with the given aware radius.
     */
    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && EnemyUtil.playerInRadius(this, player,
                detectRadius)) {
            super.setTarget((PlayerPeon) player);
            setMovementTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }

    /**
     * Stops targeting the player once they leave the awareness
     */
    public void pursueTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && !EnemyUtil.playerInRadius(this, player,
                discardRadius)) {
            super.setTarget(null);
            setMovementTask(null);
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(StatusEffectManager.class).removeEffectsOnEntity(this);
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
    }

    /**
     * Sets the texture of the orc based on the way it is moving
     */
    private void setOrcTexture() {
        if (getTarget() != null) {
            if (getTarget().getCol() < this.getCol()) {
                setTexture(getTextureDirection(TEXTURE_LEFT));
            } else {
                setTexture(getTextureDirection(TEXTURE_RIGHT));
            }
        }
    }

    @Override
    public void attackPlayer() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange));
            SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
            setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 10));
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing > 60) {
            if (super.getTarget() != null) {
                setMovementTask(new MovementTask(this, super.getTarget().
                        getPosition()));
                setOrcTexture();
                attackPlayer();
            }
            tickFollowing = 0;
        }
        // checks player position every 0.25 second (15 ticks)
        if (++tickDetecting > 15) {
            if (super.getTarget() == null) {
                detectTarget();
            } else {
                pursueTarget();
            }
            tickDetecting = 0;
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
    }

    @Override
    public Orc deepCopy() {
        return new Orc(super.getHeight(), super.getSpeed(),
                super.getMaxHealth(), super.getTextureDirection(TEXTURE_BASE));
    }
}
