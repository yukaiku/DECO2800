package deco2800.thomas.entities;

/**
 * Represents the entities faction, used to distinguish whether an entity
 * is friend or foe to another.
 */
public enum EntityFaction {
    NONE,       // Default behaviour, such as rocks / buildings
    EVIL,       // Such as orcs etc
    ALLY        // Player allegience
}
