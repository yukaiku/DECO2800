package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.FireBombSkill;
import deco2800.thomas.combat.skills.SummonGoblinSkill;
import deco2800.thomas.combat.skills.VolcanoFireballSkill;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.combat.FireBombAttackTask;
import deco2800.thomas.util.EnemyUtil;

public class VolcanoDragon extends Dragon {
    int attackRange = 4;

    public VolcanoDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.VOLCANO;
        this.identifier = "dragonVolcano";
        this.setObjectName("Chusulth");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.breathAttack = new VolcanoFireballSkill(this);
        this.elementalAttack = new FireBombSkill(this);
        this.summonGoblin = new SummonGoblinSkill(this);
    }

    @Override
    public void hitByTarget() {
        super.hitByTarget();
        GameManager.getManagerFromInstance(SoundManager.class).playSound("dragon1");
    }

    @Override
    public void elementalAttack() {
        if (super.getTarget() != null && EnemyUtil.playerInRange(this, getTarget(), attackRange)) {
            setCombatTask(new FireBombAttackTask(this, 20, 8, 20, 5, 5));
        }
    }

    @Override
    public void breathAttack() {
        if (breathAttack.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(breathAttack.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {
                e.printStackTrace();
            }
        }
    }
}
