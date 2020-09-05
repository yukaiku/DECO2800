package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.AgentEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;

import java.util.Objects;

/**
 * An abstract class inheriting from Peon that will define the
 * behaviour of all enemies. All enemies must have some form of health.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies
 */
public abstract class EnemyPeon extends Peon {

    // The target to follow and attack. This can be players or even enemies, or null for passive enemies.
    // Enemies are initialised with no target.
    // Aggressive enemies set a target when they detected a target.
    // Passive enemies set a target when being hit.
    private AgentEntity target;
    /**
     * Initialise an abstract Enemy.
     * The position of the enemy is normally set by the spawnSpecialEnemy() in EnemyManager.
     */
    public EnemyPeon(String name, String texture, int height, float speed, int health) {
        super(0, 0, speed < 0 ? 0.05f : speed, health);
        this.setObjectName(Objects.requireNonNullElse(name, "EnemyPeon"));
        this.setTexture(Objects.requireNonNullElse(texture, "orc_swamp_left"));
        this.setHeight(height <= 0 ? 1 : height);
        this.setFaction(EntityFaction.Evil);
        this.target = null;
    }

    /**
     * Sets the position of the enemy.
     * This will be automatically called from the spawnSpecialEnemy() in EnemyManager.
     * Can also be manually used for fancy combats. e.g. teleportation
     *
     * @param x the x position on the map
     * @param y the y position on the map
     */
    public void setPosition(float x, float y) {
        super.setCol(x);
        super.setRow(y);
    }

    /**
     * Returns the AgentEntity this enemy is currently targeting.
     * @return
     */
    public AgentEntity getTarget() {
        return target;
    }

    /**
     * Sets the  to be targeted by this enemy.
     * @param target
     */
    public void setTarget(AgentEntity target) {
        this.target = target;
    }

    /**
     * Construct a new enemy from the blueprint.
     * @return A new enemy
     */
    public EnemyPeon deepCopy() {
        return null;
    }
}
