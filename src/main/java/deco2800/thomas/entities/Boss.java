package deco2800.thomas.entities;

/**
 * A class that defines a specific type enemy called a boss.
 * Bosses will generally be stationary and difficult to defeat,
 * and provide rewards when defeated.
 */
public class Boss extends EnemyPeon {

    public Boss(PlayerPeon target, float row, float col, float speed) {
        super(target, row, col, speed);
    }

}
