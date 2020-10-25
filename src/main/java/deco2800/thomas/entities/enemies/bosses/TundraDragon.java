package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.SummonGoblinSkill;
import deco2800.thomas.combat.skills.dragon.DragonIceBreathSkill;
import deco2800.thomas.combat.skills.dragon.DragonIceballSkill;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class TundraDragon extends Dragon {
    public TundraDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.TUNDRA;
        this.identifier = "dragonTundra";
        this.setObjectName("Diokiedes");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));

        this.elementalAttack = new DragonIceBreathSkill(this, 0.2f, 3);
        this.breathAttack = new DragonIceballSkill(this, 0.5f, 2);
        this.summonGoblin = new SummonGoblinSkill(this);

        this.dragonAttacking = new Animation<>(0.2f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
        this.dragonWalking = new Animation<>(0.25f,
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

    @Override
    public void summonGoblin() {
        if (summonGoblin.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(summonGoblin.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {
                e.printStackTrace();
            }
        }
    }
}
