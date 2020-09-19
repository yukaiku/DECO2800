package deco2800.thomas.entities.enemies.dragons;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;

public class DesertDragon extends Dragon {
    public DesertDragon(String name, int height, float speed, int health, String texture, int orb) {
        super(name, height, speed, health, texture, orb);
    }

    @Override
    public void summonRangedAttack() {
        DesertFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.Evil);
        setCombatTask(new SandTornadoAttackTask(this, getTarget().getCol(), getTarget().getRow(),
                10, 0.2f, 100));
    }
}
