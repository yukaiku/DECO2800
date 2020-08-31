package deco2800.thomas.entities.enemies;

/**
 * A class that defines a subcategory of enemy called a Minion.
 * Features of the minion class will generally include a smaller size
 * and the capability to be spawned by other enemies.
 */
public abstract class Minion extends EnemyPeon {
    public Minion(String name, String texture, int height, float speed, int health) {
        super(name, texture, height, speed, health);
    }
}
