package deco2800.thomas.entities;

/**
 * A class that defines an implementation of a minion enemy type
 * called a Goblin.
 */
public class Goblin extends Minion implements AggressiveEnemy {
    public Goblin(PlayerPeon target, float row, float col, float speed) {
        super(target, row, col, speed);
    }
}
