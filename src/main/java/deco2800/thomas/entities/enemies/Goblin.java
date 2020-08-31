package deco2800.thomas.entities.enemies;

/**
 * A class that defines an implementation of a minion enemy type
 * called a Goblin.
 */
public class Goblin extends Minion implements AggressiveEnemy {
    public Goblin(int height, float speed, int health) {
        super("Goblin", "spacman_blue", height, speed, health);
    }

    public void detectTarget() {}

    @Override
    public Goblin deepCopy() {
        return new Goblin(super.getHeight(), super.getSpeed(), super.getMaxHealth());
    }
}
