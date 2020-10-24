package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.Texture;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.entities.enemies.PassiveEnemy;
import deco2800.thomas.managers.*;
import deco2800.thomas.renderers.components.BossHealthComponent;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.Random;

/**
 * A class that defines an implementation of a Dragon.
 * Dragons are bosses and they need to be manually initialised (using constructor or setBoss())
 * and spawned (using spawnBoss()) inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/bosses/dragon
 */
public abstract class Dragon extends Boss implements PassiveEnemy {
    // Variation of the dragon
    protected EnemyIndex.Variation variation;

    // Orb number for orb texture
    protected int orbNumber;

    // Animation & Renderings
    private MovementTask.Direction facingDirection;
    public enum State {
        IDLE, WALK, ATTACKING
    }
    private State currentState;
    private State previousState;
    protected Animation<TextureRegion> dragonAttacking;
    protected Animation<TextureRegion> dragonWalking;
    protected Animation<TextureRegion> dragonIdle;
    private float stateTimer = 0;

    // Ticks
    protected int duration = 0;
    private int followTick = 60;
    private int goblinSpawnTick = 0;
    private int growlTick = 0;
    private int roarTick = 0;
    private static final int FOLLOW_CYCLE = 80;
    private static final int GOBLIN_SPAWN_CYCLE = 60;
    private static final int GROWL_CYCLE = 180;
    private static final int ROAR_CYCLE = 800;

    // Constants
    private static final int GOBLIN_CAP = 10;
    // Range at which the dragon will attempt to melee attack the player
    protected static final int ATTACK_RANGE = 4;
    Random random = new Random();

    public Dragon(int health, float speed, int orbNumber) {
        super(health, speed);
        this.orbNumber = orbNumber;
        this.variation = EnemyIndex.Variation.SWAMP; // default
        this.identifier = "dragonSwamp";
        this.setTexture("dragonSwamp");
        this.dragonAttacking = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("dragonVolcanoAttack"));
        this.dragonWalking = new Animation<>(0.2f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("dragonVolcanoWalk"));
        this.currentState = State.IDLE;
        this.previousState = State.IDLE;
    }

    /**
     * Summons a new Goblin minion of the same environmental type as the dragon
     * if there is less than 10 goblins spawned
     */
    public void summonGoblin() {
        if (GameManager.get().getManager(EnemyManager.class).getSpecialEnemiesAlive().size() < GOBLIN_CAP) {
            GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(
                    variation.name().toLowerCase() + "Goblin", this.getCol(), this.getRow());
        }
    }

    /**
     * Applies damage to the dragon.
     * @param damage The amount of damage to be taken by this Peon.
     * @param damageType Type of damage to apply.
     * @return Damage dealt.
     */
    @Override
    public int applyDamage(int damage, DamageType damageType) {
        int damageDealt = 0;
        // first hit is immune
        if (super.getTarget() == null) {
            hitByTarget();
        } else {
            damageDealt = super.applyDamage(damage, damageType);
        }
        return damageDealt;
    }

    public void elementalAttack() {
    }

    public void breathAttack() {
    }

    @Override
    public Texture getIcon() {
         return dragonIdle.getKeyFrame(0).getTexture();
    }

    /**
     * Targets the player if the boss's health is reduced
     */
    public void hitByTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null) {
            super.setTarget(player);
            setMovementTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
        GameManager.getManagerFromInstance(ScreenManager.class).getCurrentScreen()
                .getOverlayRenderer().getComponentByInstance(BossHealthComponent.class).onBossStart(this);
        float pan = EnemyUtil.playerLRDistance(this, super.getTarget());
        GameManager.getManagerFromInstance(SoundManager.class).playSound(String.format("%sDragon",
                this.getVariation()), pan);
        GameManager.getManagerFromInstance(SoundManager.class).playBossMusic("bossMusic");
    }

    /**
     * Spawns an orb around the dragon's location at death that takes
     * the player to the next environment
     */
    @Override
    public void death() {
        super.death();
        AbstractWorld world = GameManager.get().getWorld();
        Tile tile = world.getTile((float) Math.ceil((this.getCol())),
                (float) Math.ceil((this.getRow())));
        WorldUtil.removeEntity(this);
        // Generate the correct orb texture to initialise the dragon's dropped orb
        world.setOrbEntity(new Orb(tile, "orb_" + orbNumber));

        PlayerPeon.credit(1500);

        GameManager.getManagerFromInstance(ScreenManager.class).getCurrentScreen()
                .getOverlayRenderer().getComponentByInstance(BossHealthComponent.class).onBossDefeat();
        GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        switch (currentState) {
            case ATTACKING:
                region = dragonAttacking.getKeyFrame(stateTimer);
                break;
            case WALK:
                if (stateTimer >= dragonWalking.getAnimationDuration()) {
                    stateTimer = 0;
                }
                region = dragonWalking.getKeyFrame(stateTimer);
                break;
            case IDLE:
            default:
                stateTimer = 0;
                region = dragonIdle.getKeyFrame(stateTimer);
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
    public void onTick(long i) {
        if (super.getTarget() != null) {
            // when dragon is attacking player

            // player follow
            if (++followTick + random.nextInt(9) > FOLLOW_CYCLE) {
                // Sets dragon direction
                if (getTarget().getCol() < this.getCol()) {
                    facingDirection = MovementTask.Direction.LEFT;
                } else if (getTarget().getCol() > this.getCol()) {
                    facingDirection = MovementTask.Direction.RIGHT;
                }
                // Throws a fireball at the player, and attempts to summon a
                // goblin, and attempts to initialise movement and combat tasks
                currentState = State.ATTACKING;
                duration = 24;
                breathAttack();
                elementalAttack();
                setMovementTask(new MovementTask(this, super.getTarget().
                        getPosition()));
                followTick = 0;
            }

            // goblin spawn
            if (++goblinSpawnTick > GOBLIN_SPAWN_CYCLE) {
                if (random.nextBoolean() && super.getTarget() != null) {
                    summonGoblin();
                    if (GameManager.get().getManager(EnemyManager.class).
                            getSpecialEnemiesAlive().size() >= GOBLIN_CAP) {
                        goblinSpawnTick -= 300;
                    }
                    goblinSpawnTick = 0;
                }
            }

            // roar sounds
            if (++roarTick > ROAR_CYCLE) {
                float pan = EnemyUtil.playerLRDistance(this, super.getTarget());
                GameManager.getManagerFromInstance(SoundManager.class).playSound(String.format("%sDragon",
                        this.getVariation()), pan);
                roarTick = 0;
            }
        } else {
            // when dragon is not attacking player

            // growl sounds
            if (++growlTick > GROWL_CYCLE) {
                PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
                if (EnemyUtil.playerInRadius(this, player, 14)) {
                    float pan = EnemyUtil.playerLRDistance(this, player);
                    GameManager.getManagerFromInstance(SoundManager.class).playSound("dragonGrowl", pan);
                }
                growlTick = 0;
            }
        }

        // update dragon states
        if (getMovementTask() != null) {
            if (--duration < 0) {
                duration = 0;
                currentState = State.WALK;
            }
        } else if (--duration < 0) {
            duration = 0;
            currentState = State.IDLE;
        }

        // Update tasks and effects
        super.onTick(i);
    }
}
