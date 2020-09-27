package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;

import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Iceball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.IceBreathTask;

public class TundraDragon extends Dragon {
    public TundraDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.TUNDRA;
        this.identifier = "dragonTundra";
        this.setTexture("dragonTundra");
        this.setObjectName("Diokiedes");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void elementalAttack() {
        setCombatTask(new IceBreathTask(this, getTarget().getCol(), getTarget().getRow(), 20, 0.2f, 4));
    }

    @Override
    public void breathAttack() {
        Iceball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.EVIL);
    }
}
