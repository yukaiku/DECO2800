package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;

/**
 * An interface that defines the behaviour of passive enemies.
 * Passive enemies will not attack the player unless attacked
 * or the player provokes them in some other fashion.
 */
public interface PassiveEnemy {

    void hitByTarget(Peon Target);
}
