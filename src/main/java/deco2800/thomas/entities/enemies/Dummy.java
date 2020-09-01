package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;

/**
 * A dummy enemy has limited movement and will apply zero or minor damage to the players.
 */
public class Dummy extends Monster implements PassiveEnemy {
    public Dummy(int height, float speed, int health) {
        super("Dummy", "spacman_blue", height, speed, health);
    }

    public void hitByTarget(Peon Target) {
        // ouch
    }

    @Override
    public Dummy deepCopy() {
        return new Dummy(super.getHeight(), super.getSpeed(), super.getMaxHealth());
    }
}
