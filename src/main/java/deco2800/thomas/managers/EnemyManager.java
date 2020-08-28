package deco2800.thomas.managers;

import deco2800.thomas.entities.EnemyPeon;
import deco2800.thomas.entities.Orc;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EnemyManager handles the spawning mechanism of the enemies.
 * Different worlds should initialise different EnemyManagers.
 */
public class EnemyManager extends TickableManager {

    // the maximum number of enemies allowed existing (will change in the future depending on types)
    private int enemyCap;
    // the current number of enemies in the world
    private int enemyCount;
    // current enemies existing
    private List<EnemyPeon> enemies;
    // the target player
    private PlayerPeon player;
    // the target world
    private AbstractWorld world;

    private float spawnRangeMin;
    private float spawnRangeMax;

    private int tick;
    private final Random random;

    public EnemyManager(AbstractWorld world, PlayerPeon player, int enemyCap) {
//        this.world = GameManager.get().getWorld();
        this.world = world;
        this.enemyCap = enemyCap;
        this.enemyCount = 0;
        this.enemies = new ArrayList<>();
        this.player = player;
        // give an initial tick offset
        this.tick = 25;
        this.random = new Random();

        this.spawnRangeMin = 3;
        this.spawnRangeMax = 7;
    }

    /**
     * Spawns an enemy at the given position on the map.
     * @param enemy the EnemyPeon object to be spawned
     * @param x the x position on the map
     * @param y the y position on the map
     * @return True if successfully spawned, false otherwise.
     */
    public boolean spawnEnemy(EnemyPeon enemy, float x, float y) {
        if (enemyCount < enemyCap) {
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

    @Override
    public void onTick(long i) {
        if (tick > 50) {
            if (enemyCount < enemyCap) {
                // currently spawns a dummy enemy every 50 ticks
                // generate a random value between -max and -min, or between +min and +max
                float xOffset = spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin) * (random.nextInt() % 2 * 2 - 1);
                float yOffset = spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin) * (random.nextInt() % 2 * 2 - 1);
                Orc enemy = new Orc(1, 0.03f, 100, player);
                spawnEnemy(enemy, player.getRow() + xOffset, player.getCol() + yOffset);
            }
            tick = 0;
        } else {
            tick++;
        }
    }
}
