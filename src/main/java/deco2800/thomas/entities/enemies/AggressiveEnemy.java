package deco2800.thomas.entities.enemies;

/**
 * An interface that defines the behaviour of passive enemies.
 * Aggressive enemies will generally attempt to pursue the player.
 */
public interface AggressiveEnemy {

    void detectTarget();
}
