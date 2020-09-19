package deco2800.thomas.entities.enemies;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.StatusEffectManager;
import deco2800.thomas.managers.TextureManager;
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
    private Variation variation;
    private final Animation<TextureRegion> orcIdle;
    private float stateTimer;

    private int tickFollowing = 30;
    private int tickDetecting = 15;

    // Range at which the orc will begin to chase the player
    private final int detectRadius = 8;
    // Range at which the orc will stop chasing the player
    private final int discardRadius = 12;
    // Range at which the orc will attempt to melee attack the player
    private final int attackRange = 2;

    /**
     * Initialise an orc with different variations
     */
    public Orc(Variation variation, int health, float speed) {
        super(health, speed);
        this.variation = variation;

        switch (variation) {
            case DESERT:
                this.identifier = "orcDesert";
                this.setObjectName("Desert Orc");
                break;
            case TUNDRA:
                this.identifier = "orcTundra";
                this.setObjectName("Tundra Orc");
                break;
            case VOLCANO:
                this.identifier = "orcVolcano";
                this.setObjectName("Volcano Orc");
                break;
            case SWAMP:
            default:
                this.identifier = "orcSwamp";
                this.setObjectName("Swamp Orc");
                break;
        }

        this.orcIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.stateTimer = 0;
    }

    public Orc(int health, float speed) {
        this(Variation.SWAMP, health, speed);
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
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
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

        // isAttacked animation
        if (isAttacked && --isAttackedCoolDown < 0) {
            isAttacked = false;
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = orcIdle.getKeyFrame(stateTimer);
        stateTimer = stateTimer + delta;
        return region;
    }

    @Override
    public Orc deepCopy() {
        return new Orc(variation, super.getMaxHealth(), super.getSpeed());
    }
}
