package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.util.WorldUtil;

/**
 * An explosion is a small explosion, and temporary burning on the ground
 * that some skills can spawn. It deals damage by:
 * a) Applying damage when it spawns, and
 * b) Applying damage over time until its lifetime is over
 */
public class Explosion extends CombatEntity {
    /**
     * Default constructor, sets texture and object name.
     */
    public Explosion() {
        super();
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
    }

    /**
     * Parametric constructor, that sets the initial conditions of the explosion
     * as well as texture and name.
     * @param col X position
     * @param row Y position
     * @param damage Damage to apply on impact
     * @param faction EntityFaction of the explosion
     */
    public Explosion (float col, float row, int damage, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, faction);
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
    }

    /**
     * Executes a single tick of the Explosion.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                WorldUtil.removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }
    }
}
