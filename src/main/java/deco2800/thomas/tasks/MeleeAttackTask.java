package deco2800.thomas.tasks;

import deco2800.thomas.entities.attacks.RangedEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;

public class MeleeAttackTask extends AbstractTask{

    private AbstractWorld world;

    public MeleeAttackTask(RangedEntity entity) {
        super(entity);
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

    }
}
