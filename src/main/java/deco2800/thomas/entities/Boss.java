package deco2800.thomas.entities;

/**
 * A class that defines a specific type enemy called a boss.
 * Bosses will generally be stationary and difficult to defeat,
 * and provide rewards when defeated.
 */
public abstract class Boss extends EnemyPeon {
    public Boss(String name, String texture, int height, float speed, int health, Peon target) {
        super(name, texture, height, speed, health, target);
    }
}
