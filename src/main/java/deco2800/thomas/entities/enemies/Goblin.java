package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
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
public class Goblin extends Minion implements AggressiveEnemy {
    private Variation variation;
    private final Animation<TextureRegion> goblinIdle;
    private float stateTimer;

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
        this.stateTimer = 0;
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
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = goblinIdle.getKeyFrame(stateTimer);
        stateTimer = stateTimer + delta;
        return region;
    }

    @Override
    public Goblin deepCopy() {
        return new Goblin(variation, super.getMaxHealth(), super.getSpeed());
    }
}
