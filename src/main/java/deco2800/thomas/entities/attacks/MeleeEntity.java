package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MeleeAttackTask;

public class MeleeEntity extends CombatEntity {

    private int range;
    private transient MeleeAttackTask task;

    public MeleeEntity () {
        super();
    }

    public MeleeEntity (float col, float row, int renderOrder, int damage, long lifetime, EntityFaction faction) {
        super(col, row, renderOrder, damage, faction);
    }

    @Override
    public void onTick(long i) {
        // Update combat task
        if(combatTask != null) {
            if(combatTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }
    }
}
