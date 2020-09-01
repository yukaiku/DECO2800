package deco2800.thomas.tasks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class CombatTask extends AbstractTask{

    private CombatEntity entity;
    private SquareVector position;
    private AbstractWorld world;

    public CombatTask(CombatEntity entity) {
        super(entity);
        this.entity = entity;
        this.position = position;
        world = GameManager.get().getWorld();
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void onTick(long tick) {
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
        if (!collidingEntities.isEmpty()) {
            for (AbstractEntity e : collidingEntities) {
                if (e.getObjectName() == "playerPeon") {
                    applyDamage(((PlayerPeon) e));
                }
            }
        }
    }

    private void applyDamage (PlayerPeon e) {
        e.reduceHealth(entity.getDamage());
    }
}
