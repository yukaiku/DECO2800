package deco2800.thomas.entities.enemies.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.attacks.Freeze;
import deco2800.thomas.entities.attacks.Iceball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.IceBreathTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;
import deco2800.thomas.util.EnemyUtil;
import deco2800.thomas.util.SquareVector;

public class TundraDragon extends Dragon {
    public TundraDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.identifier = "dragonTundra";
        this.setTexture("dragonTundra");
        this.variation = Variation.TUNDRA;
        this.setObjectName("Diokiedes");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void elementalAttack() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange));
            setCombatTask(new IceBreathTask(this, getTarget().getCol(), getTarget().getRow(), 20));
    }

    @Override
    public void breathAttack() {
        Iceball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.Evil);
    }
}
