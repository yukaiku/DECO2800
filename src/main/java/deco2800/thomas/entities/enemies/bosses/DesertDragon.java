package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.SandTornadoAttackTask;

public class DesertDragon extends Dragon {
    public DesertDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.DESERT;
        this.identifier = "dragonDesert";
        this.setTexture("dragonDesert");
        this.setObjectName("Doavnaen");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void elementalAttack() {
        setCombatTask(new SandTornadoAttackTask(this, getTarget().getCol(), getTarget().getRow(),
                    10, 0.2f, 100));
    }

    @Override
    public void breathAttack() {
        DesertFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.EVIL);
    }
}
