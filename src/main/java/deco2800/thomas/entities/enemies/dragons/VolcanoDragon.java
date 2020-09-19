package deco2800.thomas.entities.enemies.dragons;
import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.FireBombAttackTask;
import deco2800.thomas.util.EnemyUtil;

public class VolcanoDragon extends Dragon {
    int attackRange = 4;

    public VolcanoDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.identifier = "dragonVolcano";
        this.setTexture("dragonVolcano");
        this.variation = Variation.VOLCANO;
        this.setObjectName("Deilaenth");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void attackPlayer() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange));
            setCombatTask(new FireBombAttackTask(this, 20,8,  20, 5, 5));
    }

    @Override
    public void summonRangedAttack() {
        VolcanoFireball.spawn(this.getCol() + 1, this.getRow() + 1, getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.Evil);

        VolcanoFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.Evil);

        VolcanoFireball.spawn(this.getCol() - 1, this.getRow() - 1, getTarget().getCol(),
                getTarget().getRow(), 20, 0.15f, 60, EntityFaction.Evil);
    }
}
