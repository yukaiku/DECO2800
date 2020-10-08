package deco2800.thomas.entities.enemies.bosses;

import deco2800.thomas.entities.enemies.EnemyPeon;

/**
 * A class that defines a specific type enemy called a boss.
 * Bosses will generally be stationary and difficult to defeat,
 * and provide rewards when defeated.
 */
public abstract class Boss extends EnemyPeon {
    public Boss(int health, float speed) {
        super(health, speed);
    }
}
