package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;

/**
 * A class that defines an implementation of a Dragon.
 * Dragons are bosses and they need to be manually initialised (using constructor or setBoss())
 * and spawned (using spawnBoss()) inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/bosses/dragon
 */
public class Dragon extends Boss implements PassiveEnemy {
    public Dragon(int height, float speed, int health) {
        super("Elder Dragon", "goblin", height, speed, health);
    }

    public void summonGoblin() {
        Goblin goblin = new Goblin(1, 0.1f, 10);
        GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(goblin, this.getCol(), this.getRow());
    }

    public void hitByTarget(Peon Target) {
        summonGoblin();
    }

    @Override
    public void death() {
        // some special rules when the boss is dead.
    }

    @Override
    public void onTick(long i) {

    }
}
