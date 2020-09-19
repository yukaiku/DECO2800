package deco2800.thomas.entities.enemies;

/**
 * An interface that defines the behaviour of passive enemies.
 * Passive enemies will not attack the player unless attacked
 * or the player provokes them in some other fashion.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/passive-enemies
 */
public interface PassiveEnemy {

    void hitByTarget();
}
