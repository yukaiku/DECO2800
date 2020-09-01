package deco2800.thomas.entities;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;

public abstract class CombatEntity extends AbstractEntity {
    private boolean projectile;
    private int damage;
    private float speed;
    private transient AbstractTask task;

    public CombatEntity() {
        super();
    }

    public CombatEntity(float col, float row, int renderOrder, int damage) {
        super(col, row, renderOrder);
        this.damage = damage;
    }

    public CombatEntity(float col, float row, int renderOrder, int damage, float speed) {
        super(col, row, renderOrder);
        this.damage = damage;
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void onTick(long i) {
        if(task != null && task.isAlive()) {
            if(task.isComplete()) {
                this.task = GameManager.getManagerFromInstance(TaskPool.class).getCombatTask(this);
            }
            task.onTick(i);
        } else {
            task = GameManager.getManagerFromInstance(TaskPool.class).getCombatTask(this);
        }
    }

    protected void setTask(AbstractTask task) {
        this.task = task;
    }

    public AbstractTask getTask() {
        return task;
    }
}
