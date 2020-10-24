package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.VolcanoFireball;

public class VolcanoFireballAttackTask extends RangedAttackTask {

    public VolcanoFireballAttackTask(AbstractEntity entity, float targetCol, float targetRow, int damage, float speed, int lifetime) {
        super(entity, targetCol, targetRow, damage, speed, lifetime);
    }

    @Override
    protected void spawn(float col, float row, float targetCol, float targetRow, int damage, float speed, long lifetime, EntityFaction faction) {
        VolcanoFireball.spawn(col + 1, row + 1, targetCol,
                targetRow, 20, 0.15f, 60, EntityFaction.EVIL);

        VolcanoFireball.spawn(col, row, targetCol,
                targetRow, 20, 0.15f, 60, EntityFaction.EVIL);

        VolcanoFireball.spawn(col - 1, row - 1, targetCol,
                targetRow, 20, 0.15f, 60, EntityFaction.EVIL);
    }
}
