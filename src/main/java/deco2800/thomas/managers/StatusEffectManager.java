package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.status.StatusEffect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A manager class for all status effects on entities.
 * Stores and applies/removes effects every tick of the game.
 */
public class StatusEffectManager extends TickableManager {

    // A list for storing all status effects
    private final CopyOnWriteArrayList<StatusEffect> currentStatusEffects;

    /**
     * Creates a StatusEffectManager instance which stores a list of all current status effects.
     */
    public StatusEffectManager() {
        currentStatusEffects = new CopyOnWriteArrayList<>();
    }

    /**
     * Adds a new status effect to the manager.
     *
     * @param status An implemented instance of the abstract StatusEffect class.
     */
    public <T extends StatusEffect> void addStatus(T status) {
        currentStatusEffects.add(status);
    }

    /**
     * Returns the current list of status effects.
     *
     * @return The current list of status effect.
     */
    public CopyOnWriteArrayList<StatusEffect> getCurrentStatusEffects() {
        return currentStatusEffects;
    }

    /**
     * Removes all status effects from an entity.
     *
     * @param entity The entity having its effects removed.
     */
    public void removeEffectsOnEntity(AbstractEntity entity) {
        currentStatusEffects.removeIf(effect -> effect.getAffectedEntity().equals(entity));
    }

    /**
     * Applies any active statuses, and removes any inactive statuses.
     *
     * @param i Tick count
     */
    public void onTick(long i) {
        for (StatusEffect effect : currentStatusEffects) {
            if (!effect.getActive() || effect.getAffectedEntity() == null) {
                currentStatusEffects.remove(effect);
            } else {
                effect.applyEffect();
            }
        }
    }
}
