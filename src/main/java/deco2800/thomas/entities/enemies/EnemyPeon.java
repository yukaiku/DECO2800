package deco2800.thomas.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

/**
 * An abstract class inheriting from Peon that will define the
 * behaviour of all enemies. All enemies must have some form of health.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies
 */
public abstract class EnemyPeon extends Peon implements Animatable {
    // This is for getting the textures and animation frames, not to confused with enemy index.
    // e.g. "orcDesert", "orcSwamp"...
    protected String identifier;

    // The target to follow and attack. This can be players or even enemies, or null for passive enemies.
    // Enemies are initialised with no target.
    // Aggressive enemies set a target when they detected a target.
    // Passive enemies set a target when being hit.
    private AgentEntity target;
    /**
     * Initialise an abstract Enemy.
     * The position of the enemy is normally set by the spawnSpecialEnemy() in EnemyManager.
     */
    public EnemyPeon(int health, float speed) {
        super(0, 0, speed < 0 ? 0.05f : speed, health);
        this.setObjectName("Enemy");
        // We don't need setTexture anymore because all enemies are now animatable
        this.setTexture("enemyDefault");
        this.setFaction(EntityFaction.EVIL);
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
     * @return the target
     */
    public AgentEntity getTarget() {
        return target;
    }

    /**
     * Sets the target for the enemy
     * @param target The target
     */
    public void setTarget(AgentEntity target) {
        this.target = target;
    }

    public Texture getIcon() {
        return GameManager.getManagerFromInstance(TextureManager.class).getTexture("iconDefault");
    }

    public void attackPlayer() {
    }

    public TextureRegion getFrame(float delta) {
        return new TextureRegion(GameManager.getManagerFromInstance(
                TextureManager.class).getTexture("enemyDefault"));
    }

    /**
     * Construct a new enemy from the blueprint.
     * @return A new enemy
     */
    public EnemyPeon deepCopy() {
        return null;
    }
}
