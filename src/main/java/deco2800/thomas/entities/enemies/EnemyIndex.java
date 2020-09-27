package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.enemies.bosses.DesertDragon;
import deco2800.thomas.entities.enemies.bosses.SwampDragon;
import deco2800.thomas.entities.enemies.bosses.TundraDragon;
import deco2800.thomas.entities.enemies.bosses.VolcanoDragon;
import deco2800.thomas.entities.enemies.minions.Goblin;
import deco2800.thomas.entities.enemies.monsters.Dummy;
import deco2800.thomas.entities.enemies.monsters.Orc;

/**
 * Usage:
 * For wild enemies (auto-spawn),
 * For special enemies (manual-spawn),
 */
public class EnemyIndex {
    public static EnemyPeon makeEnemy(String enemyId) throws InvalidEnemyException {
        switch (enemyId) {
            case "swampOrc": return new Orc(Variation.SWAMP, 50, 0.09f);
            case "swampGoblin": return new Goblin(Variation.SWAMP, 20, 0.1f);
            case "swampDragon": return new SwampDragon(1050, 0.03f, 2);

            case "tundraOrc": return new Orc(Variation.TUNDRA, 100, 0.05f);
            case "tundraGoblin": return new Goblin(Variation.TUNDRA, 20, 0.1f);
            case "tundraDragon": return new TundraDragon(950, 0.03f, 3);

            case "desertOrc": return new Orc(Variation.DESERT, 50, 0.09f);
            case "desertGoblin": return new Goblin(Variation.DESERT, 20, 0.1f);
            case "desertDragon": return new DesertDragon(850, 0.03f, 4);

            case "volcanoOrc": return new Orc(Variation.VOLCANO, 50, 0.09f);
            case "volcanoGoblin": return new Goblin(Variation.VOLCANO, 20, 0.1f);
            case "volcanoDragon": return new VolcanoDragon(1000, 0.03f , 1);

            case "dummy": return new Dummy(100, 0);

            default: throw new InvalidEnemyException();
        }
    }
}
