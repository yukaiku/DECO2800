package deco2800.thomas.entities.enemies;

/**
 * An interface that defines the behaviour of passive enemies.
 * Aggressive enemies will generally attempt to pursue the player.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/aggressive-enemies
 */
public interface AggressiveEnemy {

    void detectTarget();
}
