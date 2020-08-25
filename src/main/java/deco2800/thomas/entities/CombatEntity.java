package deco2800.thomas.entities;

import deco2800.thomas.util.BoundingBox;

/**
 * Implemented by entity classes that engage in combat. Requires
 * the entity to have both health, and a bounding box.
 */
public interface CombatEntity {
    /**
     * Get the health object for this entity for use in combat.
     * @return reference to the health object of the entity.
     */
    void getHealth();

    /**
     * Get the current bounding box of this entity for use in collision checking.
     * @return reference to bounding box of entity.
     * @implNote BoundingBox position needs to be updated when used in AgentEntities.
     */
    BoundingBox getBoundingBox();
}
