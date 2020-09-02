package deco2800.thomas.entities.attacks;

import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MeleeAttackTask;

public class MeleeEntity extends CombatEntity {

    private int range;
    private transient MeleeAttackTask task;

    public MeleeEntity () {
        super();
    }

    public MeleeEntity (float col, float row, int renderOrder, int damage, int range) {
        super(col, row, renderOrder, damage);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public void onTick(long i) {
        if(task != null) {
            if(task.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            task.onTick(i);
        } else {
            GameManager.get().getManager(CombatManager.class).removeEntity(this);
        }
    }

    public void setTask(MeleeAttackTask task) {
        this.task = task;
    }

    public MeleeAttackTask getTask() {
        return task;
    }

}
