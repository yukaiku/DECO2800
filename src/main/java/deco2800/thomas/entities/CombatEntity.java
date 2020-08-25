package deco2800.thomas.entities;

/**
 * Implemented by entity classes that engage in combat. Requires
 * the entity to have both health, and a bounding box.
 */
public interface CombatEntity {
    // Health interface / class (WHY WOULD IT BE AN INTERFACE?!)
    void getHealth();

    // Bounding box
    void getBoundingBox();
}
