package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;

public class SwampDragon extends Dragon {
    public SwampDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.SWAMP;
        this.identifier = "dragonSwamp";
        this.setObjectName("Siendiadut");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void breathAttack() {
            Fireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                    getTarget().getRow(), 10, 0.2f, 60, EntityFaction.EVIL);
        }

    @Override
    public void elementalAttack() {
        this.setCombatTask(new ScorpionStingAttackTask(this, getTarget().getCol(), getTarget().getRow(), 10, 0.15f, 60));
    }
}
