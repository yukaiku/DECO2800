package deco2800.thomas.managers;

import deco2800.thomas.tasks.status.StatusEffect;

import java.util.ArrayList;

public class StatusEffectManager extends TickableManager {
    private ArrayList<StatusEffect> currentStatusEffects;

    public StatusEffectManager() {
        currentStatusEffects = new ArrayList<>();
    }

    /**
     *
     * @param status An implemented instance of the Abstract Status Effect class
     */
    public <T extends StatusEffect> void addStatus(T status) {
        currentStatusEffects.add(status);
    }

    /**
     * Checks if there are statuses to be applied on each tick provided a status
     * isn't already active
     * @param i Tick count
     */
    public void onTick(long i) {
        for (StatusEffect effect : currentStatusEffects) {
                if (effect.getActive()) {
                    effect.applyEffect();
                }
            }
        }
}
