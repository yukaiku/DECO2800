package deco2800.thomas.managers;

import deco2800.thomas.tasks.status.StatusEffect;

import java.util.ArrayList;

public class StatusEffectManager extends TickableManager {
    private ArrayList<StatusEffect> currentStatusEffects;

    public StatusEffectManager(){
        this.currentStatusEffects = new ArrayList<>();
    }

    /**
     *
     * @param status An implemented instance of the Abstract Status Effect class
     */
    public <T extends StatusEffect> void addStatus(T status) {
        currentStatusEffects.add(status);
    }

    /**
     *
     * @param i
     */
    public void onTick(long i) {
        if (!this.currentStatusEffects.isEmpty()){
            for (StatusEffect effect : this.currentStatusEffects) {
                effect.applyEffect();
            }
        }
    }
}
