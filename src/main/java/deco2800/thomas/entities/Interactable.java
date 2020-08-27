package deco2800.thomas.entities;

public interface Interactable {
    /**
     * Any interactive entity (e.g. an NPC, dropped item, door etc)
     * should implement this method in order to execute a side-effect
     * of the interaction.
     *
     * For example: When interacting with an NPC, the player is passed as
     * a parameter for the NPC to gather quest information, level, health etc.
     * This allows for creating many different types of NPCs, such as healers,
     * traders, level-restricted access NPCs etc.
     */
    public void interact();
}
