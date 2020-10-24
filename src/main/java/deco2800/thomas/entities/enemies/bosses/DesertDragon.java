package deco2800.thomas.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.DesertFireballSkill;
import deco2800.thomas.combat.skills.SandTornadoSkill;
import deco2800.thomas.combat.skills.SummonGoblinSkill;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class DesertDragon extends Dragon {
    public DesertDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = EnemyIndex.Variation.DESERT;
        this.identifier = "dragonDesert";
        this.setTexture("dragonDesert");
        this.setObjectName("Doavnaen");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.elementalAttack = new SandTornadoSkill(this);
        this.breathAttack = new DesertFireballSkill(this);
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
        }
    }
}
