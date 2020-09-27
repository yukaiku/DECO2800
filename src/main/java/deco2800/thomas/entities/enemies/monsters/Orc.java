package deco2800.thomas.entities.enemies.monsters;

import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.AggressiveEnemy;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.thomas.entities.Animatable;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;

/**
 * A class that defines an implementation of an orc.
 * Orcs are wild enemies. They can be automatically spawned using EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/monsters/orc
 */
public class Orc extends Monster implements AggressiveEnemy, Animatable {
    public enum State {
        IDLE, WALK, ATTACK_MELEE
    }
    public State currentState;
    public State previousState;
    private final Variation variation;
    private final Animation<TextureRegion> orcIdle;
    private final Animation<TextureRegion> orcAttacking;
    private float stateTimer;
    private int duration = 0;
    private MovementTask.Direction facingDirection;

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
                this.setTexture("orcDesert");
                this.setObjectName("Desert Orc");
                break;
            case TUNDRA:
                this.identifier = "orcTundra";
                this.setTexture("orcTundra");
                this.setObjectName("Tundra Orc");
                break;
            case VOLCANO:
                this.identifier = "orcVolcano";
                this.setTexture("orcVolcano");
                this.setObjectName("Volcano Orc");
                break;
            case SWAMP:
            default:
                this.identifier = "orcSwamp";
                this.setTexture("orcSwamp");
                this.setObjectName("Swamp Orc");
                break;
        }
        this.stateTimer = 0;
        currentState = State.IDLE;
        previousState = State.IDLE;
        facingDirection = MovementTask.Direction.RIGHT;
        this.orcIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.orcAttacking = new Animation<> (0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
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
            super.setTarget(player);
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
        PlayerPeon.credit(40);
    }

    @Override
    public void attackPlayer() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange)) {
            SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
            currentState = State.ATTACK_MELEE;
            duration = 12;
            setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 10));
        }
    }

    @Override
    public void onTick(long i) {
        if (--duration < 0) {
            duration = 0;
            currentState = State.IDLE;
        }

        // update target following path every 1 second (60 ticks)
        if (++tickFollowing > 60) {
            if (super.getTarget() != null) {
                if (getTarget().getCol() < this.getCol()) {
                    facingDirection = MovementTask.Direction.LEFT;
                } else if (getTarget().getCol() > this.getCol()) {
                    facingDirection = MovementTask.Direction.RIGHT;
                }
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

        // Update tasks and effects
        super.onTick(i);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        switch (currentState) {
            case ATTACK_MELEE:
                region = orcAttacking.getKeyFrame(stateTimer);
                break;
            case IDLE:
            default:
                stateTimer = 0;
                region = orcIdle.getKeyFrame(stateTimer);
        }
        if ((getMovingDirection() == MovementTask.Direction.LEFT ||
                facingDirection == MovementTask.Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.LEFT;
        } else if ((getMovingDirection() == MovementTask.Direction.RIGHT ||
                facingDirection == MovementTask.Direction.RIGHT) && region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.RIGHT;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    @Override
    public Orc deepCopy() {
        return new Orc(variation, super.getMaxHealth(), super.getSpeed());
    }
}
