package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.StatusEffectManager;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class that defines an implementation of a Dragon.
 * Dragons are bosses and they need to be manually initialised (using constructor or setBoss())
 * and spawned (using spawnBoss()) inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/bosses/dragon
 */
public class Dragon extends Boss implements PassiveEnemy {
    private int tickFollowing = 60;
    // Range at which the dragon will attempt to melee attack the player
    private int attackRange = 8;
    //the orb number for orb texture
    int orbNumber;
    //
    Random random = new Random();

    public Dragon(String name, int height, float speed, int health, int orb) {
        super(name, "elder_dragon", height, speed, health);
        orbNumber = orb;
    }

    public Dragon(String name, int height, float speed, int health, String texture, int orb) {
        this(name, height, speed, health, orb);
        super.setTextureDirections(new ArrayList<>(Arrays.asList(texture, texture + "_left", texture + "_right")));
        this.setTexture(texture + "_left");
    }

    /**
     * Summons a new Goblin minion of the same environmental type as the dragon
     * if there is less than 10 goblins spawned
     */
    public void summonGoblin() {
        if (GameManager.get().getManager(EnemyManager.class).getSpecialEnemiesAlive().size() < 10) {
            Goblin goblin = new Goblin(1, 0.1f, 20, "goblin" + getTextureDirection(TEXTURE_BASE).substring(6));
            GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(goblin, this.getCol() + 1, this.getRow() + 2);
        }
    }

    @Override
    public void reduceHealth(int damage) {
        hitByTarget();
        health.reduceHealth(damage);
        if (isDead()) {
            death();
        }
        isAttacked = true;
        isAttackedCoolDown = 10;
    }

    /**
     * Targets the player if the boss's health is reduced
     */
    public void hitByTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null) {
            super.setTarget((PlayerPeon) player);
            setMovementTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }

    /**
     * Sets the appropriate texture based on the direction the dragon
     * is moving
     */
    private void setDragonTexture() {
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
            setCombatTask(new MeleeAttackTask(this, origin, 8, 8, 20));
    }

    public void summonRangedAttack() {
        Fireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.2f, 60, EntityFaction.Evil);
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing + random.nextInt(9) > 80) {
            if (super.getTarget() != null) {
                // Throws a fireball at the player, and attempts to summon a
                // goblin, and attempts to initialise movement and combat
                // tasks
                summonGoblin();
                summonRangedAttack();
                setMovementTask(new MovementTask(this, super.getTarget().
                        getPosition()));
                attackPlayer();
                setDragonTexture();
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

        GameManager.get().getWorld().getPlayerEntity().credit(1500);
    }
}
