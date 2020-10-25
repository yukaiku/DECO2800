package deco2800.thomas.managers;

import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.InvalidEnemyException;
import deco2800.thomas.entities.enemies.bosses.Boss;
import deco2800.thomas.entities.enemies.monsters.Monster;
import deco2800.thomas.entities.enemies.monsters.Orc;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.*;

/**
 * EnemyManager handles the spawning mechanism of the enemies.
 * Different worlds should initialise different EnemyManagers.
 *
 * EnemyManager tracks wild enemies, special enemies and bosses separately.
 * Wild enemies are automatically spawned at a random position on a fixed tick
 * (different enemies will have various spawning rates in the future sprints);
 * Special enemies do not randomly spawn and need to be manually placed at the given position.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/enemy-manager
 */
public class EnemyManager extends TickableManager {
    // the target world
    private final AbstractWorld world;

    // true if the spawning mechanism is active, false otherwise (default true)
    private boolean wildSpawning;

    // the maximum number of enemies allowed being alive at one time (will change in the future depending on types)
    private int wildEnemyCap;

    // list of wild enemies allowed to spawn
    private final List<String> wildEnemyIndexes;

    // blueprints of wild and special enemies (excludes boss)
    private final Map<String, EnemyPeon> enemyConfigs;

    // current wild enemies alive (excludes boss)
    private final List<EnemyPeon> wildEnemiesAlive;

    // current special enemies alive (excludes boss)
    private final List<EnemyPeon> specialEnemiesAlive;

    // the current boss (this can be null)
    private Boss boss;

    // util variables for auto spawning
    private final float spawnRangeMin;
    private final float spawnRangeMax;
    private final Random random;
    private int tick = 0;
    private static final int SPAWN_TICKS = 3;
    private static final int PACK_SPAWNS = 3;

    /**
     * Initialise an enemy manager with wild enemies and boss configured.
     * @param world The world where the manager will operate on.
     * @param bossIndex The enemy index of the boss. Bosses need to be later manually placed
     *                  using the spawnBoss() method.
     * @param wildEnemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param wildEnemyIndexes The enemy indexes of the wild enemies.
     */
    public EnemyManager(AbstractWorld world, String bossIndex, int wildEnemyCap, String ...wildEnemyIndexes) {
        this.world = world;
        this.wildSpawning = wildEnemyCap > 0;
        this.wildEnemyCap = wildEnemyCap;
        this.wildEnemyIndexes = new ArrayList<>();
        this.enemyConfigs = new HashMap<>();

        // wild enemy blueprints
        Collections.addAll(this.wildEnemyIndexes, wildEnemyIndexes);
        for (String index : wildEnemyIndexes) {
            try {
                enemyConfigs.put(index, EnemyIndex.getEnemy(index));
            } catch (InvalidEnemyException ignored) {
                // ignored
            }
        }
        this.wildEnemiesAlive = new ArrayList<>();

        this.specialEnemiesAlive = new ArrayList<>();
        try {
            this.boss = bossIndex == null ? null : (Boss) EnemyIndex.getEnemy(bossIndex);
        } catch (InvalidEnemyException e) {
            this.boss = null;
        }
        this.spawnRangeMin = 8;
        this.spawnRangeMax = 14;
        // Seeding for testing purposes
        this.random = new Random(world.getType().length());
    }

    /**
     * Initialise an enemy manager with wild enemies configured.
     * @param world The world where the manager will operate on.
     * @param wildEnemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param wildEnemyIndexes The enemy indexes of the wild enemies.
     */
    public EnemyManager(AbstractWorld world, int wildEnemyCap, String ...wildEnemyIndexes) {
        this(world, null, wildEnemyCap, wildEnemyIndexes);
    }

    /**
     Initialise an enemy manager without wild enemies and bosses. (e.g. for tutorial maps)
     * This constructor is intended for manually spawning special enemies (e.g. dummies) later on the map,
     * if the world does not have enemies at all, you don't need an EnemyManager.
     * @param world The world where the manager will operate on.
     */
    public EnemyManager(AbstractWorld world) {
        this(world, null, 0);
    }

    /**
     * Get the blueprint of the wild and special enemies. (excludes boss, for boss use getBoss())
     * @param enemyIndex The enemy id string shown in EnemyIndex
     * @return The blueprint of the enemy of given index
     */
    public EnemyPeon getEnemyConfig(String enemyIndex) {
        return enemyConfigs.get(enemyIndex);
    }

    /**
     * Add one or more blueprints to the manager.
     * If the blueprint is an Monster, it will automatically become a wild enemy and spawn automatically.
     * @param enemyIndex The enemy id string(s) shown in EnemyIndex
     * @throws InvalidEnemyException If the enemy index is not listed in EnemyIndex
     */
    public void addEnemyConfigs(String ...enemyIndex) throws InvalidEnemyException {
        for (String index : enemyIndex) {
            EnemyPeon config = EnemyIndex.getEnemy(index);
            if (config instanceof Monster) {
                wildEnemyIndexes.add(index);
            }
            enemyConfigs.put(index, EnemyIndex.getEnemy(index));
        }
    }

    /** Removes the enemy blueprints */
    public void removeEnemyConfigs(String ...enemyIndex) {
        for (String index : enemyIndex) {
            wildEnemyIndexes.remove(index);
            enemyConfigs.remove(index);
        }
    }

    /**
     * Check if the enemy is currently spawning.
     * @return true if the enemy is currently spawning, false otherwise
     */
    public boolean checkWildEnemySpawning() {
        return wildSpawning;
    }

    /** Start the wild enemy spawning. */
    public void enableWildEnemySpawning() {
        wildSpawning = true;
    }

    /** Stop the wild enemy spawning. */
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
        return wildEnemiesAlive.size() + specialEnemiesAlive.size() + ((boss != null) ? 1 : 0);
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
    public void spawnWildEnemy(EnemyPeon enemy, float x, float y) {
        if (wildSpawning && wildEnemiesAlive.size() < wildEnemyCap) {
            enemy.setPosition(x, y);
            world.addEntity(enemy);
            wildEnemiesAlive.add(enemy);
        }
    }

    /**
     * Spawn the special enemies (e.g. minions, dummies) at the given position.
     * If the given index is not in the config list, it will automatically create a blueprint.
     * Special Enemies will bypass the wild enemy limits and configs.
     * @param enemyIndex The enemy index of the special enemy
     * @param x The x position on the map.
     * @param y The y position on the map.
     */
    public void spawnSpecialEnemy(String enemyIndex, float x, float y) {
        EnemyPeon enemy;
        if (enemyConfigs.containsKey(enemyIndex)) {
            enemy = enemyConfigs.get(enemyIndex).deepCopy();
        } else {
            try {
                enemy = EnemyIndex.getEnemy(enemyIndex);
            } catch (InvalidEnemyException ignored) {
                return;
            }
            enemyConfigs.put(enemyIndex, enemy.deepCopy());
        }
        enemy.setPosition(x, y);
        world.addEntity(enemy);
        specialEnemiesAlive.add(enemy);
    }

    /**
     * Spawn the boss at the given position. To specify the boss use setBoss().
     * @param x The x position on the map.
     * @param y The y position on the map.
     */
    public void spawnBoss(float x, float y) {
        if (boss != null) {
            boss.setPosition(x, y);
            world.addEntity(boss);
        }
    }

    /** Spawns a random enemy from the configuration list. Normally it will be called automatically by the manager. */
    public void spawnRandomWildEnemy() {
        EnemyPeon enemyBlueprint = enemyConfigs.get(wildEnemyIndexes.get(random.nextInt(wildEnemyIndexes.size())));
        // spawn rate of the enemy
        if (enemyBlueprint instanceof Orc && random.nextFloat() < ((Orc) enemyBlueprint).getSpawnRate()) {
            int spawnCount = Math.min(random.nextInt(PACK_SPAWNS - 1),
                    getWildEnemyCap() - getWildEnemiesAlive().size() - 1);
            for (int i = 0; i <= spawnCount; i++) {
                for (int j = 0; j < 5; j++) {
                    // generate a random position within radius range
                    float degree = random.nextFloat() * 360;
                    float radius = spawnRangeMin + random.nextFloat() * (spawnRangeMax - spawnRangeMin);
                    float tileX = world.getPlayerEntity().getCol() + Math.round(Math.sin(degree) * radius);
                    float tileY = world.getPlayerEntity().getRow() + Math.round(Math.cos(degree) * radius);

                    // prevent spawning outside of the map or tiles with effects
                    if (tileX > -world.getWidth() + 1 && tileX < world.getWidth() - 2 &&
                            tileY > -world.getHeight() + 1 && tileY < world.getHeight() - 1 &&
                            world.getTile(tileX, tileY) != null && !world.getTile(tileX, tileY).isObstructed() &&
                            !world.getTile(tileX, tileY).getType().equals("BurnTile")) {
                        spawnWildEnemy(enemyBlueprint.deepCopy(), tileX, tileY);
                        break;
                    }
                }
            }
        }
    }

    /** Removes a wild enemy (when it's dead or de-spawned) @require The enemy exists */
    public void removeWildEnemy(EnemyPeon wildEnemy) {
        wildEnemiesAlive.remove(wildEnemy);
        world.removeEntity(wildEnemy);
        world.disposeEntity(wildEnemy.getEntityID());
    }

    /** Removes a special enemy (when it's dead or de-spawned) @require The enemy exists */
    public void removeSpecialEnemy(EnemyPeon specialEnemy) {
        specialEnemiesAlive.remove(specialEnemy);
        world.removeEntity(specialEnemy);
        world.disposeEntity(specialEnemy.getEntityID());
    }

    /** Remove the boss after being defeated. */
    public void removeBoss() {
        if (boss != null) {
            world.removeEntity(boss);
            world.disposeEntity(boss.getEntityID());
            boss = null;
        }
    }

    @Override
    public void onTick(long i) {
        if (++tick > SPAWN_TICKS) {
            if (wildSpawning && wildEnemiesAlive.size() < wildEnemyCap) {
                spawnRandomWildEnemy();
            }
            tick = 0;
        }
    }
}
