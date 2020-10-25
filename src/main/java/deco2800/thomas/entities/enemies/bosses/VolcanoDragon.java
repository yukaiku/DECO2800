package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.FireBombAttackTask;

public class VolcanoDragon extends Dragon {
    int attackRange = 4;

    public VolcanoDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.VOLCANO;
        this.identifier = "dragonVolcano";
        this.setObjectName("Chusulth");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.dragonAttacking = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
        this.dragonWalking = new Animation<>(0.2f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Walk"));
    }

    @Override
    public void elementalAttack() {
        setCombatTask(new FireBombAttackTask(this, 20, 8, 20, 5, 5));
    }

    @Override
    public void breathAttack() {
        VolcanoFireball.spawn(this.getCol() + 1, this.getRow() + 1, getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.EVIL);

        VolcanoFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.EVIL);

        VolcanoFireball.spawn(this.getCol() - 1, this.getRow() - 1, getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.EVIL);
    }
}
