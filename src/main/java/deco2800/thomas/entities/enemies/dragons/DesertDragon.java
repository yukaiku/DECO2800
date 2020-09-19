package deco2800.thomas.entities.enemies.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;

public class DesertDragon extends Dragon {
    public DesertDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = Variation.DESERT;
        this.identifier = "dragonDesert";
        this.setTexture("dragonDesert");
        this.setObjectName("Chuzzinoath");

        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.dragonAttacking = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
    }

    @Override
    public void elementalAttack() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange));
        SquareVector origin = new SquareVector(this.getCol() - 1, this.getRow() - 1);
        currentState = State.ATTACKING;
        duration = 12;
        setCombatTask(new SandTornadoAttackTask(this, getTarget().getCol(), getTarget().getRow(),
                10, 0.2f, 100));
    }

    @Override
    public void breathAttack() {
        DesertFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.Evil);
    }
}
