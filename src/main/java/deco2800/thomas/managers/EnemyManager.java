package deco2800.thomas.managers;

import deco2800.thomas.entities.EnemyPeon;
import deco2800.thomas.entities.Orc;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EnemyManager handles the spawning and de-spawning of the enemies.
 * Different maps/environments can initialise different EnemyManagers.
 * Each EnemyManager will have a world and a target player as parameters.
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
    private Random random;

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
     * Spawns an enemy
     */
    public void spawnEnemy(EnemyPeon enemy) {
        world.addEntity(enemy);
        enemies.add(enemy);
        enemyCount++;
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
                EnemyPeon enemy = new Orc(player, player.getRow() + xOffset, player.getCol() + yOffset, 0.03f);
                spawnEnemy(enemy);
            }
            tick = 0;
        } else {
            tick++;
        }
    }
}
