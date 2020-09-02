package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;

/**
 * A class that defines an implementation of a Dragon.
 * Dragons are bosses and they need to be manually initialised (using constructor or setBoss())
 * and spawned (using spawnBoss()) inside EnemyManager.
 */
public class Dragon extends Boss implements PassiveEnemy {
    public Dragon(int height, float speed, int health) {
        super("Elder Dragon", "goblin", height, speed, health);

    }

    @Override
    public void death() {
        // some special rules when the boss is dead.
    }

    public void hitByTarget(Peon Target) {
    }
}
