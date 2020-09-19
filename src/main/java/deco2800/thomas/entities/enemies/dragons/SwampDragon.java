package deco2800.thomas.entities.enemies.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.skills.ScorpionStingSkill;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.StingProjectile;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.FireBombAttackTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;

public class SwampDragon extends Dragon {
    public SwampDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.setObjectName("Viondria");
        this.variation = Variation.SWAMP;

        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.dragonAttacking = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
    }

    @Override
    public void elementalAttack() {
        this.setCombatTask(new ScorpionStingAttackTask(this, getTarget().getCol(), getTarget().getRow(), 10, 0.15f, 60));
    }
}
