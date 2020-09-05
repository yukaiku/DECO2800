package deco2800.thomas.entities;

/**
 * Represents the entities faction, used to distinguish whether an entity
 * is friend or foe to another.
 */
public enum EntityFaction {
    None,       // Default behaviour, such as rocks / buildings
    Evil,       // Such as orcs etc
    Ally        // Player allegience
}
