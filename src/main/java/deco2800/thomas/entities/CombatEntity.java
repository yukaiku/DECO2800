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
}
