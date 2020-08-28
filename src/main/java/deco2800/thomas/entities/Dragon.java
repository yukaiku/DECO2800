package deco2800.thomas.entities;

/**
 * A class that defines an implementation of a boss
 * called a Dragon.
 */
public class Dragon extends Boss implements PassiveEnemy {

    public Dragon(PlayerPeon target, float row, float col, float speed) {
        super(target, row, col, speed);
    }
}
