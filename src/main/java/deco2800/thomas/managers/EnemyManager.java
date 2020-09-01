package deco2800.thomas.managers;

import deco2800.thomas.entities.enemies.Boss;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EnemyManager handles the spawning mechanism of the enemies.
 * Different worlds should initialise different EnemyManagers.
 */
public class EnemyManager extends TickableManager {
    // the target world
    private final AbstractWorld world;

    // true if the spawning mechanism is active, false otherwise (default true)
    private boolean wildSpawning;

    // the maximum number of enemies allowed being alive at one time (will change in the future depending on types)
    private int wildEnemyCap;

    // list of configured enemies allowed to spawn
    private final List<EnemyPeon> wildEnemyConfigs;

    // current wild enemies alive (excludes boss)
    private final List<EnemyPeon> wildEnemiesAlive;

    // current special enemies alive (excludes boss)
    private final List<EnemyPeon> specialEnemiesAlive;

    // the current boss (this can be null)
    private Boss boss;

    private final float spawnRangeMin;
    private final float spawnRangeMax;
    private int tick;
    private final Random random;

    /**
     * Initialise an EnemyManager for the world.
     * @param world The world where the manager will operate on.
     * @param wildEnemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param wildEnemyConfigs Blueprints of enemies (one for each type) the manager will automatically spawn.
     * The wildEnemyConfigs should not contain bosses, or they will be randomly placed and generated.
     */
    public EnemyManager(AbstractWorld world, int wildEnemyCap, List<EnemyPeon> wildEnemyConfigs) {
        this.world = world;

        this.wildSpawning = wildEnemyConfigs.size() > 0;
        this.wildEnemyCap = wildEnemyCap;
        this.wildEnemyConfigs = wildEnemyConfigs;
        this.wildEnemiesAlive = new ArrayList<>();

        this.specialEnemiesAlive = new ArrayList<>();
        this.boss = null;

        this.spawnRangeMin = 5;
        this.spawnRangeMax = 10;

        this.tick = 100;
        this.random = new Random();
    }

    /**
     * Initialise an EnemyManager with a boss.
     * Bosses need to be later manually placed using the spawnBoss() method.
     */
    public EnemyManager(AbstractWorld world, int wildEnemyCap, List<EnemyPeon> wildEnemyConfigs, Boss boss) {
        this(world, wildEnemyCap, wildEnemyConfigs);
        this.boss = boss;
    }

    /**
     * Check if the enemy is currently spawning.
     * @return true if the enemy is currently spawning, false otherwise
     */
    public boolean checkWildEnemySpawning() {
        return wildSpawning;
    }

    /** Start the enemy spawning after stopped. */
    public void enableWildEnemySpawning() {
        wildSpawning = true;
    }

    /** Stop the enemy spawning. */
    public void disableWildEnemySpawning() {
        wildSpawning = false;
    }

    /** Get the maximum number of wild enemies allowed existing at one time. */
    public int getWildEnemyCap() {
        return wildEnemyCap;
    }

    /** Set the maximum number of wild enemies allowed existing at one time. */
    public void setWildEnemyCap(int wildEnemyCap) {
        this.wildEnemyCap = wildEnemyCap;
    }

    /**
     * Get the current number of enemies on the map. (includes boss)
     * @return the number of enemies alive.
     */
    public int getEnemyCount() {
        return wildEnemiesAlive.size() + specialEnemiesAlive.size() + (boss == null ? 0 : 1);
    }

    /** Get the current wild enemies alive in the map. */
    public List<EnemyPeon> getWildEnemiesAlive() {
        return new ArrayList<>(wildEnemiesAlive);
    }

    /** Get the current special enemies alive in the map. */
    public List<EnemyPeon> getSpecialEnemiesAlive() {
        return new ArrayList<>(specialEnemiesAlive);
    }

    /** get the current boss config in the map. */
    public Boss getBoss() {
        return boss;
    }

    /** Set the boss for the map. (not summon yet, to summon use spawnBoss()) */
    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    /** Spawn the wild enemy at the given position. This should be automatically called by the configuration. */
    private void spawnWildEnemy(EnemyPeon enemy, float x, float y) {
        // todo: check if the position is outside of map
        if (wildSpawning && wildEnemiesAlive.size() < wildEnemyCap) {
            enemy.setPosition(x, y);
            world.addEntity(enemy);
            wildEnemiesAlive.add(enemy);
        }
    }

    /**
     * Spawn the special enemies (e.g. minions, dummies) at the given position.
     * Special Enemies will bypass the wild enemy limits and configs.
     * @param enemy The special EnemyPeon to spawn.
     * @param x The x position on the map (col?)
     * @param y The y position on the map (row?)
     */
    public void spawnSpecialEnemy(EnemyPeon enemy, float x, float y) {
        enemy.setPosition(x, y);
        world.addEntity(enemy);
        specialEnemiesAlive.add(enemy);
    }

    /** Spawn the boss at the given position. To specify the boss use setBoss(). */
    public void spawnBoss(float x, float y) {
        boss.setPosition(x, y);
        world.addEntity(boss);
    }

    /** Spawns a random enemy from the configuration list. Normally it will be called automatically by the manager. */
    private void spawnRandomWildEnemy() {
        // currently spawns an enemy every 100 ticks
        // generate a random value between -max and -min, or between +min and +max
        float xOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
        float yOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
        // choose a random enemy blueprint
        EnemyPeon enemy = wildEnemyConfigs.get(random.nextInt(wildEnemyConfigs.size())).deepCopy();
        spawnWildEnemy(enemy, world.getPlayerEntity().getCol() + xOffset, world.getPlayerEntity().getRow() + yOffset);
    }

    /** Removes a wild enemy (when it's dead or de-spawned) @require The enemy exists */
    public void removeWildEnemy(EnemyPeon wildEnemy) {
        wildEnemiesAlive.remove(wildEnemy);
        world.deleteEntity(wildEnemy.getEntityID());
        world.removeEntity(wildEnemy);
    }

    /** Removes a special enemy (when it's dead or de-spawned) @require The enemy exists */
    public void removeSpecialEnemy(EnemyPeon specialEnemy) {
        specialEnemiesAlive.remove(specialEnemy);
        world.deleteEntity(specialEnemy.getEntityID());
        world.removeEntity(specialEnemy);
    }

    /** Remove the boss after being defeated. */
    public void removeBoss() {
        if (boss != null) {
            world.removeEntity(boss);
            world.deleteEntity(boss.getEntityID());
            boss = null;
        }
    }

    /** Automatically detects the enemy type and remove from the world. (Not recommended since it takes some time) */
    public void removeEnemyAuto(EnemyPeon enemy) {
        if (wildEnemiesAlive.contains(enemy)) {
            removeWildEnemy(enemy);
            return;
        }
        if (specialEnemiesAlive.contains(enemy)) {
            removeSpecialEnemy(enemy);
            return;
        }
        if (boss == enemy) {
            removeBoss();
        }
    }

    @Override
    public void onTick(long i) {
        if (++tick > 120) {
            if (wildEnemiesAlive.size() < wildEnemyCap) {
                spawnRandomWildEnemy();
            }
            tick = 0;
        }
    }
}
