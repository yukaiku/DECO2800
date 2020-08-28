package deco2800.thomas.entities;

import java.util.Objects;

/**
 An abstract class inheriting from Peon that will define the
 behaviour of all enemies. All enemies must have some form of health.
 **/
public abstract class EnemyPeon extends Peon implements HasHealth {

    // The target to follow and attack. This can be players or even enemies, or null for passive enemies.
    private Peon target;

    // The health of the enemy.
    private int health;

    /**
     * Initialise an abstract Enemy. The position of the enemy is normally set by the spawnEnemy() in EnemyManager.
     */
    public EnemyPeon(String name, String texture, int height, float speed, int health, Peon target) {
        super(0, 0, speed < 0 ? 1f : speed);
        this.setObjectName(Objects.requireNonNullElse(name, "EnemyPeon"));
        this.setTexture(Objects.requireNonNullElse(texture, "spacman_blue"));
        this.setHeight(height <= 0 ? 1 : height);
        this.health = health;
        this.target = target;
    }

    /**
     * Sets the position of the enemy. Called from the spawnEnemy() in EnemyManager.
     * Can also be used for fancy combats. e.g. teleportation
     * @param x the x position on the map
     * @param y the y position on the map
     */
    public void setPosition(float x, float y) {
        super.setCol(x);
        super.setRow(y);
    }

    public Peon getTarget() {
        return target;
    }

    public void setTarget(Peon target) {
        this.target = target;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }
}
