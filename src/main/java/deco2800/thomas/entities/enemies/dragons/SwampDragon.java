package deco2800.thomas.entities.enemies.dragons;

import deco2800.thomas.combat.skills.ScorpionStingSkill;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.StingProjectile;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.tasks.combat.FireBombAttackTask;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;
import deco2800.thomas.util.EnemyUtil;

public class SwampDragon extends Dragon {
    public SwampDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.setObjectName("Viondria");
    }

    @Override
    public void summonRangedAttack() {
        this.setCombatTask(new ScorpionStingAttackTask(this, getTarget().getCol(), getTarget().getRow(), 1, 0.15f, 60));
    }
}
