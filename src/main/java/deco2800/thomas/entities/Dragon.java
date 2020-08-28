package deco2800.thomas.entities;

/**
 * A class that defines an implementation of a boss
 * called a Dragon.
 */
public class Dragon extends Boss implements PassiveEnemy {
    public Dragon(int height, float speed, int health, Peon target) {
        super("Elder Dragon", "spacman_blue", height, speed, health, target);
    }
}
