package deco2800.thomas.entities.enemies.bosses;
import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.combat.skills.FireballSkill;
import deco2800.thomas.combat.skills.ScorpionStingSkill;
import deco2800.thomas.combat.skills.SummonGoblinSkill;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class SwampDragon extends Dragon {

    public SwampDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.SWAMP;
        this.identifier = "dragonSwamp";
        this.setObjectName("Siendiadut");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.elementalAttack = new ScorpionStingSkill(this);
        this.breathAttack = new FireballSkill(this);
        this.summonGoblin = new SummonGoblinSkill(this);
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
        } else {
            System.out.println(breathAttack.getCooldownRemaining());
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
