package deco2800.thomas.entities.enemies.bosses;
import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.SummonGoblinSkill;
import deco2800.thomas.combat.skills.dragon.DragonFireballSkill;
import deco2800.thomas.combat.skills.dragon.DragonScorpionStingSkill;
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

        this.elementalAttack = new DragonScorpionStingSkill(this);
        this.elementalAttack.setCooldownMax(300);
        this.breathAttack = new DragonFireballSkill(this, 120, 0.4f, 0.3f, 40);

        this.summonGoblin = new SummonGoblinSkill(this);
        this.summonGoblin.setCooldownMax(500);

        this.dragonAttacking = new Animation<>(0.2f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Attack"));
        this.dragonWalking = new Animation<>(0.5f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Walk"));
    }

    @Override
    public void elementalAttack() {
        if (elementalAttack.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(elementalAttack.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {

            }
        }
    }

    @Override
    public void breathAttack() {
        if (breathAttack.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(breathAttack.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {

            }
        }
    }

    @Override
    public void summonGoblin() {
        if (summonGoblin.getCooldownRemaining() <= 0) {
            try {
                this.setCombatTask(summonGoblin.getNewSkillTask(getTarget().getCol(), getTarget().getRow()));
            } catch (SkillOnCooldownException e) {

            }
        }
    }
}
