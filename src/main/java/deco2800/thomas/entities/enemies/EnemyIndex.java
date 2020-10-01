package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.enemies.bosses.DesertDragon;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.entities.enemies.bosses.TundraDragon;
import deco2800.thomas.entities.enemies.bosses.VolcanoDragon;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.enemies.monsters.Dummy;
import deco2800.thomas.entities.enemies.monsters.Orc;

/**
 * Here lists all available enemies and their variations for convenient configuration. i.e. blueprints
 * Each variation of enemies will now has an index (e.g. "swampOrc").
 * You need to provide the enemy index when initialising the enemy manager.
 * The blueprint params like speed, damage, etc. are initial values and can be changed later
 * inside of EnemyManager by getting blueprints using enemyManager.getEnemyConfig(enemyIndex).
 *
 * Example of creating a blueprint:
 *     case "speedySwampOrc": return new Orc(Variation.SWAMP, 50, 0.15f);
 *
 * Usage of blueprints:
 *     For wild enemies (auto-spawn) and bosses, provide their enemyIds when initialising the enemy manager.
 *     For special enemies (manual-spawn), use EnemyManager.spawnSpecialEnemy(String enemyIndex, float x, float y)
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/blueprints
 */
public class EnemyIndex {
    /** World variations, just used for textures and names */
    public enum Variation { DESERT, SWAMP, TUNDRA, VOLCANO }

    /**
     * This function will be called only once when initialising a new EnemyManager.
     */
    public static EnemyPeon getEnemy(String enemyIndex) throws InvalidEnemyException {
        switch (enemyIndex) {
            // swamp world
            case "swampOrc": return new Orc(Variation.SWAMP, 50, 0.09f, 5, 8, 2, 0.01f);
            case "swampGoblin": return new Goblin(Variation.SWAMP, 20, 0.1f, 5, 10, 2);
            case "swampDragon": return new SwampDragon(1050, 0.03f, 2);

            // tundra world
            case "tundraOrc": return new Orc(Variation.TUNDRA, 100, 0.05f, 5, 8, 2, 0.01f);
            case "tundraGoblin": return new Goblin(Variation.TUNDRA, 20, 0.1f, 5, 10, 2);
            case "tundraDragon": return new TundraDragon(950, 0.03f, 3);

            // desert world
            case "desertOrc": return new Orc(Variation.DESERT, 50, 0.09f, 5, 8, 2, 0.01f);
            case "desertGoblin": return new Goblin(Variation.DESERT, 20, 0.1f, 5, 10, 2);
            case "desertDragon": return new DesertDragon(850, 0.03f, 4);

            // volcano world
            case "volcanoOrc": return new Orc(Variation.VOLCANO, 50, 0.09f, 5, 8, 2, 0.01f);
            case "volcanoGoblin": return new Goblin(Variation.VOLCANO, 20, 0.1f, 5, 10, 2);
            case "volcanoDragon": return new VolcanoDragon(1000, 0.03f , 1);

            // tutorial world
            case "dummy": return new Dummy(100, 0);

            // debugging
            case "summonGoblin": return new Goblin(Variation.SWAMP, 50, 0.1f, 1, 1, 1);
            case "testOrc": return new Orc(Variation.SWAMP, 100, 1, 1, 1, 1, 1);
            case "testGoblin": return new Goblin(Variation.SWAMP, 1, 1, 1, 1, 1);
            case "testDragon": return new SwampDragon(1, 1, 2);
            default: throw new InvalidEnemyException();
        }
    }

    private EnemyIndex() {
        // Prevent calling the public constructor.
    }
}
