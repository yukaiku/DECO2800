package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class HealEffect extends CombatEntity implements Animatable {
    private Peon entity;
    private final Animation<TextureRegion> animationFrames;
    private float stateTimer = 0f;

    /**
     * Create a HealEffect entity. Set the position of this entity at
     * parent entity. Then call the heal function to heal the parent entity
     *
     * @param entity        the target entity we want to use heal effect onto
     * @param restoreHealth the amount of heal to restore
     */
    public HealEffect(Peon entity, int restoreHealth) {
        super(entity.getCol(), entity.getRow(), RenderConstants.PEON_EFFECT_RENDER, 0, entity.getFaction(), DamageType.COMMON);
        this.entity = entity;
        this.setColRenderLength(this.entity.getColRenderLength());
        this.setRowRenderLength(this.entity.getRowRenderLength());
        this.animationFrames = new Animation<>(0.15f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("healEffect"));
        this.heal(entity, restoreHealth);
    }

    /**
     * Heal the entity with the amount of restoreHealth
     *
     * @param entity        the target entity we want to heal
     * @param restoreHealth the amount of heal to restore
     */
    private void heal(Peon entity, int restoreHealth) {
        if (entity.getCurrentHealth() + restoreHealth >= entity.getMaxHealth()) {
            entity.setCurrentHealthValue(entity.getMaxHealth());
        } else {
            entity.regenerateHealth(restoreHealth);
        }
    }

    /**
     * Set the position for the heal effect to follow
     * the parent entity
     *
     * @param i on tick time
     */
    @Override
    public void onTick(long i) {
        this.setPosition(entity.getCol(), entity.getRow(), this.getRenderOrder());

        // If heal animation finished then remove the heal effect entity
        stateTimer += i;
        if (animationFrames.isAnimationFinished(stateTimer)) {
            GameManager.get().getWorld().removeEntity(this);
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return animationFrames.getKeyFrame(stateTimer);
    }
}
