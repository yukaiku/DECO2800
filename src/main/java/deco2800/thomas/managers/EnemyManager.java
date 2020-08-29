package deco2800.thomas.managers;

import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.Peon;
import deco2800.thomas.entities.enemies.Boss;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.Arrays;
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

    // list of configured enemies allowed to spawn
    private List<EnemyPeon> enemyConfigs;

    // the maximum number of enemies allowed being alive at one time (will change in the future depending on types)
    private int enemyCap;

    // the current number of enemies alive in the world
    private int enemyCount;

    // current enemies alive
    private List<EnemyPeon> enemies;

    // the target player
    private AgentEntity player;

    // the target world
    private final AbstractWorld world;

    private final float spawnRangeMin;
    private final float spawnRangeMax;

    private int tick;
    private final Random random;

    public EnemyManager(AbstractWorld world, int enemyCap, List<EnemyPeon> enemyConfigs) {
        this.running = true;

        this.world = world;
        this.enemyCap = enemyCap;
        this.enemyConfigs = enemyConfigs;

        this.enemyCount = 0;
        this.enemies = new ArrayList<>();
        this.player = world.getPlayerEntity();
        // give an initial tick offset
        this.tick = 25;
        this.random = new Random();

        this.spawnRangeMin = 5;
        this.spawnRangeMax = 14;
    }

    public EnemyManager(AbstractWorld world, int enemyCap, List<EnemyPeon> enemyConfigs, Boss boss) {
        this(world, enemyCap, enemyConfigs);
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

    /**
     * Check if the enemy is currently spawning.
     * @return true if the enemy is currently spawning, false otherwise
     */
    public boolean checkEnemySpawning() {
        return this.running;
    }

    /**
     * Special Enemies (such as bosses and storyline-specific enemies) will bypass the enemyCap limit and
     * need to be manually placed.
     */
    public void spawnSpecialEnemy(EnemyPeon enemy, float x, float y) {

    }

    /**
     * Spawns an enemy at the given position on the map. It will be automatically called by the configuration
     * @param enemy the EnemyPeon object to be spawned
     * @param x the x position on the map
     * @param y the y position on the map
     * @return True if successfully spawned, false otherwise.
     * todo: check if the position is out of map
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
     * Removes an enemy (when it's dead or de-spawned)
     */
    public void removeEnemy(EnemyPeon enemy) {
        enemies.remove(enemy);
        world.deleteEntity(enemy.getEntityID());
        enemyCount--;
    }

    public List<EnemyPeon> getEnemies() {
        return new ArrayList<>(enemies);
    }

    @Override
    public void onTick(long i) {
        if (tick > 100) {
            if (enemyCount < enemyCap) {
                // currently spawns a dummy enemy every 50 ticks
                // generate a random value between -max and -min, or between +min and +max
                float xOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
                float yOffset = (spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin)) * (random.nextInt(2) * 2 - 1);
                Orc enemy = new Orc(1, 0.05f, 100);
//                enemy.setTarget((Peon) player);
                spawnEnemy(enemy, player.getCol() + xOffset, player.getRow() + yOffset);
            }
            tick = 0;
        } else {
            tick++;
        }
    }
}
