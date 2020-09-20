package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Boss;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class EnemyManager extends TickableManager implements KeyDownObserver {
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

    // util variables for auto spawning
    private final float spawnRangeMin;
    private final float spawnRangeMax;
    private final Random random;
    private int tick = 0;
    private int nextTick = 60;

    /**
     * Initialise an enemy manager without wild enemies. (e.g. for tutorial maps)
     * This constructor is intended for manually spawning special enemies (e.g. dummies) later on the map,
     * if the world does not have enemies at all, you don't need an EnemyManager.
     * @param world The world where the manager will operate on.
     */
    public EnemyManager(AbstractWorld world) {
        this.world = world;

        this.wildSpawning = false;
        this.wildEnemyCap = 0;
        this.wildEnemyConfigs = new ArrayList<>();
        this.wildEnemiesAlive = new ArrayList<>();
        this.specialEnemiesAlive = new ArrayList<>();
        this.boss = null;

        this.spawnRangeMin = 8;
        this.spawnRangeMax = 14;
        this.random = new Random();
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
    }

    /**
     * Initialise an enemy manager with wild enemies configured.
     * @param world The world where the manager will operate on.
     * @param wildEnemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param wildEnemyConfigs Blueprints of enemies (one for each type) the manager will automatically spawn.
     * The wildEnemyConfigs should not contain bosses, otherwise they will be randomly placed and generated.
     */
    public EnemyManager(AbstractWorld world, int wildEnemyCap, List<EnemyPeon> wildEnemyConfigs) {
        this(world);
        this.wildSpawning = wildEnemyConfigs.size() > 0;
        this.wildEnemyCap = wildEnemyCap;
        this.wildEnemyConfigs.addAll(wildEnemyConfigs);
    }

    /**
     * Initialise an enemy manager with a boss configured.
     * @param world The world where the manager will operate on.
     * @param wildEnemyCap The maximum number of wild enemies allowed to exist at one time.
     * @param wildEnemyConfigs Blueprints of enemies (one for each type) the manager will automatically spawn.
     * @param boss The boss enemy in the map. Bosses need to be later manually placed using the spawnBoss() method.
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
        return wildEnemiesAlive.size() + specialEnemiesAlive.size() + (boss == null ? 0 : boss.isDead() ? 0 : 1);
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
     * @param x The x position on the map.
     * @param y The y position on the map.
     */
    public void spawnSpecialEnemy(EnemyPeon enemy, float x, float y) {
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
    private void spawnRandomWildEnemy() {
        for (int i = 0; i < 5; i++) {
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
                // choose a random enemy blueprint
                EnemyPeon enemy = wildEnemyConfigs.get(random.nextInt(wildEnemyConfigs.size())).deepCopy();
                spawnWildEnemy(enemy, tileX, tileY);
                break;
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

    /** Get the world name the manager is operating on */
    public String getWorldName() {
        return world.toString().substring(world.toString().lastIndexOf('.') + 1);
    }

    /**
     * Keyboard inputs (in debug mode only)
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        if (GameManager.get().debugMode) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                if (keycode == Input.Keys.P) {
                    // Ctrl + P: Toggle wild enemy spawning
                    wildSpawning = !wildSpawning;
                } else if (keycode == Input.Keys.K) {
                    // Ctrl + K: kill all wild and special enemies (excludes boss)
                    new ArrayList<>(wildEnemiesAlive).forEach(this::removeWildEnemy);
                    new ArrayList<>(specialEnemiesAlive).forEach(this::removeSpecialEnemy);
                    // Ctrl + L: Kill the dragon
                } else if (keycode == Input.Keys.L && boss != null) {
                    boss.applyDamage(boss.getCurrentHealth(), DamageType.COMMON);
                } else if (keycode == Input.Keys.S) {
                    // Ctrl + S: Spawn a special enemy
                    Goblin goblin = new Goblin(Variation.SWAMP, 50, 0.05f);
                    spawnSpecialEnemy(goblin, 0, 0);
                }
            }
        }
    }

    @Override
    public void onTick(long i) {
        if (++tick > nextTick) {
            if (wildSpawning && wildEnemiesAlive.size() < wildEnemyCap) {
                spawnRandomWildEnemy();
                if (wildEnemiesAlive.size() == wildEnemyCap) {
                    // give player a rest
                    nextTick = 1200 + random.nextInt(90);
                } else {
                    nextTick = random.nextInt(90);
                }
            }
            tick = 0;
        }
    }
}
