package deco2800.thomas.entities;

/**
 * A class that defines an implementation of an enemy
 * called an Orc.
 */
public class Orc extends EnemyPeon implements AggressiveEnemy {

    public Orc(PlayerPeon target, float row, float col, float speed) {
        super(target, row, col, speed);
    }
}
