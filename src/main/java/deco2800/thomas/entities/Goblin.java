package deco2800.thomas.entities;

/**
 * A class that defines an implementation of a minion enemy type
 * called a Goblin.
 */
public class Goblin extends Minion implements AggressiveEnemy {
    public Goblin(int height, float speed, int health, Peon target) {
        super("Goblin", "spacman_blue", height, speed, health, target);
    }
}
