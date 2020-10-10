package deco2800.thomas.entities.enemies.minions;

import deco2800.thomas.entities.enemies.EnemyPeon;

/**
 * A class that defines a subcategory of enemy called a Minion.
 * Features of the minion class will generally include a smaller size
 * and the capability to be spawned by other enemies.
 */
public abstract class Minion extends EnemyPeon {
    public Minion(int health, float speed) {
        super(health, speed);
    }
}
