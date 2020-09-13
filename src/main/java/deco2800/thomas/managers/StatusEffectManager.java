package deco2800.thomas.managers;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.status.StatusEffect;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class StatusEffectManager extends TickableManager {
    private CopyOnWriteArrayList<StatusEffect> currentStatusEffects;

    public StatusEffectManager() {
        currentStatusEffects = new CopyOnWriteArrayList<>();
    }

    /**
     *
     * @param status An implemented instance of the Abstract Status Effect class
     */
    public <T extends StatusEffect> void addStatus(T status) {
        currentStatusEffects.add(status);
    }

    public void removeEffectsOnEntity(AbstractEntity entity) {
        currentStatusEffects.removeIf(effect -> effect.getAffectedEntity().equals(entity));
    }

    /**
     * Checks if there are statuses to be applied on each tick provided a status
     * isn't already active
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
