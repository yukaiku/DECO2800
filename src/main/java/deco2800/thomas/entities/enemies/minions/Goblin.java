package deco2800.thomas.entities.enemies.minions;

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
 * A class that defines an implementation of a minion enemy type called a Goblin.
 * Goblins are special enemies. They are summoned by monsters or bosses and
 * directly target towards players when spawning.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/minions/goblin
 */
public class Goblin extends Minion implements AggressiveEnemy, Animatable {
    public enum State {
        IDLE, WALK, ATTACK_MELEE
    }
    private State currentState;
    private State previousState;
    private final Variation variation;
    private final Animation<TextureRegion> goblinIdle;
    private float stateTimer;
    private final Animation<TextureRegion> goblinAttacking;
    private MovementTask.Direction facingDirection;

    private int duration = 0;
    private int tickFollowing = 30;
    // Range at which the goblin will attempt to melee attack the player
    private int attackRange = 2;


    public Goblin(Variation variation, int health, float speed) {
        super(health, speed);
        this.variation = variation;
        switch (variation) {
            case DESERT:
                this.identifier = "goblinDesert";
                this.setTexture("goblinDesert");
                this.setObjectName("Desert Goblin");
                break;
            case TUNDRA:
                this.identifier = "goblinTundra";
                this.setTexture("goblinTundra");
                this.setObjectName("Tundra Goblin");
                break;
            case VOLCANO:
                this.identifier = "goblinVolcano";
                this.setTexture("goblinVolcano");
                this.setObjectName("Volcano Goblin");
                break;
            case SWAMP:
            default:
                this.identifier = "goblinSwamp";
                this.setTexture("goblinSwamp");
                this.setObjectName("Swamp Goblin");
                break;
        }
        this.goblinIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.goblinAttacking = new Animation<> (0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
        this.stateTimer = 0;
        currentState = State.IDLE;
        previousState = State.IDLE;
        facingDirection = MovementTask.Direction.RIGHT;
        detectTarget();
    }

    public Goblin(int health, float speed) {
        this(Variation.SWAMP, health, speed);
    }

    /**
     * Locks onto the player and begins to pursue once it has been spawned
     * by another enemy
     */
    public void detectTarget() {
        if (GameManager.get().getWorld() != null) {
            AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
            if (player != null) {
                super.setTarget(GameManager.get().getWorld().getPlayerEntity());
                setMovementTask(new MovementTask(this, super.getTarget().getPosition()));
            }
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeSpecialEnemy(this);
        PlayerPeon.credit(8.50f);
    }

    @Override
    public void attackPlayer() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange)) {
            SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
            currentState = State.ATTACK_MELEE;
            duration = 12;
            setCombatTask(new MeleeAttackTask(this, origin, 1, 1, 5));
        }
    }

    @Override
    public void onTick(long i){
        // update target following path every 0.5 second (30 ticks)
        if (--duration < 0) {
            duration = 0;
            currentState = State.IDLE;
        }

        if (++tickFollowing > 30) {
            if (super.getTarget() != null) {
                if (getTarget() != null) {
                    if (getTarget().getCol() < this.getCol()) {
                        facingDirection = MovementTask.Direction.LEFT;
                    } else if (getTarget().getCol() > this.getCol()) {
                        facingDirection = MovementTask.Direction.RIGHT;
                    }
                }
                setMovementTask(new MovementTask(this, super.getTarget().getPosition()));
                attackPlayer();
            }
            tickFollowing = 0;
        }

        // Update tasks and effects
        super.onTick(i);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        switch (currentState) {
            case ATTACK_MELEE:
                region = goblinAttacking.getKeyFrame(stateTimer);
                break;
            case IDLE:
            default:
                stateTimer = 0;
                region = goblinIdle.getKeyFrame(stateTimer);
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
    public Goblin deepCopy() {
        return new Goblin(variation, super.getMaxHealth(), super.getSpeed());
    }
}
