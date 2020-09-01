package deco2800.thomas.entities.attacks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.tasks.AbstractTask;

public abstract class CombatEntity extends AbstractEntity {
    private int damage;
    private transient AbstractTask task;

    public CombatEntity() {
        super();
    }

    public CombatEntity(float col, float row, int renderOrder, int damage) {
        super(col, row, renderOrder);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    protected void setTask(AbstractTask task) {
        this.task = task;
    }

    public AbstractTask getTask() {
        return task;
    }
}
