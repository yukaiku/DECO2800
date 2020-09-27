package deco2800.thomas.entities.enemies.bosses;

import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.enemies.PassiveEnemy;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;
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
    private MovementTask.Direction facingDirection;
    protected Animation<TextureRegion> dragonIdle;
    protected Variation variation;
    protected int duration = 0;
    private int tickFollowing = 60;
    // Range at which the dragon will attempt to melee attack the player
    protected int attackRange = 4;
    //the orb number for orb texture
    int orbNumber;
    private float stateTimer = 0;
    Random random = new Random();
    public Dragon(int health, float speed, int orbNumber) {
        super(health, speed);
        this.orbNumber = orbNumber;
        this.variation = Variation.SWAMP; // default
        this.identifier = "dragonSwamp";
        this.setTexture("dragonSwamp");
    }

    /**
     * Summons a new Goblin minion of the same environmental type as the dragon
     * if there is less than 10 goblins spawned
     */
    public void summonGoblin() {
        if (GameManager.get().getManager(EnemyManager.class).getSpecialEnemiesAlive().size() < 10) {
            GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(
                    variation.name().toLowerCase() + "Goblin", this.getCol() + 1, this.getRow() + 2);
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
        int damageDealt = super.applyDamage(damage, damageType);
        hitByTarget();
        if (isDead()) {
            death();
        }
        return damageDealt;
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
    }

    public void elementalAttack() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange)) {
            SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
            setCombatTask(new MeleeAttackTask(this, origin, 8, 8, 20));
        }
    }

    public void breathAttack() {
        Fireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.2f, 60, EntityFaction.EVIL);
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing + random.nextInt(9) > 80) {
            if (super.getTarget() != null) {
                // Sets dragon direction
                if (getTarget().getCol() < this.getCol()) {
                    facingDirection = MovementTask.Direction.LEFT;
                } else if (getTarget().getCol() > this.getCol()) {
                    facingDirection = MovementTask.Direction.RIGHT;
                }
                // Throws a fireball at the player, and attempts to summon a
                // goblin, and attempts to initialise movement and combat
                // tasks
                summonGoblin();
                breathAttack();
                elementalAttack();
                setMovementTask(new MovementTask(this, super.getTarget().
                        getPosition()));
            }
            tickFollowing = 0;
        }

        // Update tasks and effects
        super.onTick(i);
    }

    /**
     * Spawns an orb around the dragon's location at death that takes
     * the player to the next environment
     */
    @Override
    public void death() {
        AbstractWorld world = GameManager.get().getWorld();
        Tile tile = world.getTile((float) Math.ceil((this.getCol())),
                (float) Math.ceil((this.getRow())));
        GameManager.getManagerFromInstance(EnemyManager.class).removeBoss();
        //Generate the correct orb texture to initialise the dragon's dropped orb
        world.setOrbEntity(new Orb(tile, "orb_" + orbNumber));

        PlayerPeon.credit(1500);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region = dragonIdle.getKeyFrame(stateTimer);
        if ((getMovingDirection() == MovementTask.Direction.LEFT ||
                facingDirection == MovementTask.Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.LEFT;
        } else if ((getMovingDirection() == MovementTask.Direction.RIGHT ||
                facingDirection == MovementTask.Direction.RIGHT) && region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.RIGHT;
        }
        return region;
    }
}
