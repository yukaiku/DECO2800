package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;
import deco2800.thomas.entities.enemies.Boss;
import deco2800.thomas.entities.enemies.PassiveEnemy;

/**
 * A class that defines an implementation of a boss
 * called a Dragon.
 */
public class Dragon extends Boss implements PassiveEnemy {
    public Dragon(int height, float speed, int health) {
        super("Elder Dragon", "spacman_blue", height, speed, health);
    }

    public void hitByTarget(Peon Target) {}
}
