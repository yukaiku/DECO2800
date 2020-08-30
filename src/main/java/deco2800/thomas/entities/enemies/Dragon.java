package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;

/**
 * A class that defines an implementation of a boss
 * called a Dragon.
 */
public class Dragon extends Boss implements PassiveEnemy {
    public Dragon(int height, float speed, int health) {
        super("Elder Dragon", "spacman_blue", height, speed, health);
    }

    @Override
    public void setHealth(int newHealth) {
        super.setHealth(newHealth);
        if (newHealth <= 0) {
            onBossDefeat();
        }
    }

    @Override
    public void onBossDefeat() {

    }

    public void hitByTarget(Peon Target) {}
}
