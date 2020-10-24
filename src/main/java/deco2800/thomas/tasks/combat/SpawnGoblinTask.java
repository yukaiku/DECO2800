package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;

public class SpawnGoblinTask extends AbstractTask {

    float targetCol;
    float targetRow;
    EnemyIndex.Variation variation;

    // Task state
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    public SpawnGoblinTask(AbstractEntity entity, float targetCol, float targetRow, EnemyIndex.Variation variation) {
        super(entity);
        this.targetCol = targetCol;
        this.targetRow = targetRow;
        this.variation = variation;
    }

    private void spawn() {
        GameManager.get().getManager(EnemyManager.class).spawnSpecialEnemy(
                variation.name().toLowerCase() + "Goblin", this.entity.getCol() + 1, this.entity.getRow() + 2);
    }

    @Override
    public boolean isComplete() {
        return taskComplete;
    }

    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    @Override
    public void onTick(long tick) {
        spawn();
        taskComplete = true;
    }
}
