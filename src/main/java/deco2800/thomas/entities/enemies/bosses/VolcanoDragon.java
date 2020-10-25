package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.SummonGoblinSkill;
import deco2800.thomas.combat.skills.VolcanoFireballSkill;
import deco2800.thomas.combat.skills.dragon.DragonFireBombSkill;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class VolcanoDragon extends Dragon {

    public VolcanoDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.VOLCANO;
        this.identifier = "dragonVolcano";
        this.setObjectName("Chusulth");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));

        this.breathAttack = new VolcanoFireballSkill(this);
        this.elementalAttack = new DragonFireBombSkill(this);
        this.summonGoblin = new SummonGoblinSkill(this);

        this.dragonAttacking = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
        this.dragonWalking = new Animation<>(0.2f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Walk"));
    }

    @Override
    public void elementalAttack() {
        if (elementalAttack.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(elementalAttack.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {
                e.printStackTrace();
            }
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
