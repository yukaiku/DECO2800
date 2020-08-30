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
    // true if the spawning mechanism is active, false otherwise (default true)
    private boolean running;

    // the target world
    private final AbstractWorld world;

    // the maximum number of enemies allowed being alive at one time (will change in the future depending on types)
    private int enemyCap;

    // list of configured enemies allowed to spawn
    private final List<EnemyPeon> enemyConfigs;

    // the current number of enemies alive in the world (excludes boss)
    private int enemyCount;

    // current enemies alive (excludes boss)
    private final List<EnemyPeon> enemies;

    // the current boss (this can be null)
    private Boss boss;

    private final float spawnRangeMin;
    private final float spawnRangeMax;

    private int tick;
    private final Random random;

    /**
     * Initialise an EnemyManager for the world.
     * @param world The world where the manager will operate on.
     * @param enemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param enemyConfigs Blueprints of enemies (one for each type) the manager will automatically spawn.
     *                     The enemyConfigs should not contain bosses, or they will be randomly placed and generated.
     */
    public EnemyManager(AbstractWorld world, int enemyCap, List<EnemyPeon> enemyConfigs) {
        this.running = true;

        this.world = world;
        this.enemyCap = enemyCap;
        this.enemyConfigs = enemyConfigs;

        this.enemyCount = 0;
        this.enemies = new ArrayList<>();
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
    public EnemyManager(AbstractWorld world, int enemyCap, List<EnemyPeon> enemyConfigs, Boss boss) {
        this(world, enemyCap, enemyConfigs);
        this.boss = boss;
    }

    /**
     * Check if the enemy is currently spawning.
     * @return true if the enemy is currently spawning, false otherwise
     */
    public boolean checkEnemySpawning() {
        return this.running;
    }

    /**
     * Start the enemy spawning after stopped.
     */
    public void startEnemySpawning() {
        this.running = true;
    }

    /**
     * Stop the enemy spawning.
     */
    public void stopEnemySpawning() {
        this.running = false;
    }

    public List<EnemyPeon> getEnemies() {
        return new ArrayList<>(enemies);
    }

    /**
     * Spawns an enemy at the given position on the map. It will be automatically called by the configuration
     * @param enemy the EnemyPeon object to be spawned
     * @param x the x position on the map
     * @param y the y position on the map
     * @return True if successfully spawned, false otherwise.
     * todo: check if the position is outside of map
     */
    public boolean spawnEnemy(EnemyPeon enemy, float x, float y) {
        if (running && enemyCount < enemyCap) {
            enemy.setPosition(x, y);
            world.addEntity(enemy);
            enemies.add(enemy);
            enemyCount++;
            return true;
        }
        return false;
    }

    /**
     * Spawns a random enemy from the configuration list.
     * Normally it will be called automatically by the manager.
     */
    public void spawnRandomEnemy() {
        // currently spawns an enemy every 100 ticks
        // generate a random value between -max and -min, or between +min and +max
        float xOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
        float yOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
        // choose a random enemy blueprint
        EnemyPeon enemy = enemyConfigs.get(random.nextInt(enemyConfigs.size())).deepCopy();
        spawnEnemy(enemy, world.getPlayerEntity().getCol() + xOffset, world.getPlayerEntity().getRow() + yOffset);
    }

    /**
     * Spawns the boss at the given position.
     */
    public void spawnBoss(Boss boss, float x, float y) {
        boss.setPosition(x, y);
        world.addEntity(boss);
    }

    /**
     * Removes an enemy (when it's dead or de-spawned)
     */
    public void removeEnemy(EnemyPeon enemy) {
        enemies.remove(enemy);
        world.deleteEntity(enemy.getEntityID());
        enemyCount--;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    /**
     * Removes the boss.
     */
    public void removeBoss() {
        world.deleteEntity(boss.getEntityID());
    }

    public int getEnemyCap() {
        return enemyCap;
    }

    public void setEnemyCap(int enemyCap) {
        this.enemyCap = enemyCap;
    }

    @Override
    public void onTick(long i) {
        if (++tick > 100) {
            if (enemyCount < enemyCap) {
                spawnRandomEnemy();
            }
            tick = 0;
        }
    }
}
