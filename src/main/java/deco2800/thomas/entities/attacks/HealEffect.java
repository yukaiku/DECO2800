package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class HealEffect extends CombatEntity implements Animatable {
    private Peon entity;
    private final Animation<TextureRegion> animationFrames;
    private float stateTimer = 0f;

    public HealEffect(Peon entity, int restoreHealth) {
        super(entity.getCol(), entity.getRow(), RenderConstants.PEON_EFFECT_RENDER, 0, entity.getFaction());
        this.entity = entity;
        this.setColRenderLength(this.entity.getColRenderLength());
        this.setRowRenderLength(this.entity.getRowRenderLength());
        this.animationFrames = new Animation<>(0.13f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("waterShield"));
        this.heal(entity, restoreHealth);
    }

    private void heal(Peon entity, int restoreHealth) {
        if (entity.getCurrentHealth() + restoreHealth >= entity.getMaxHealth()) {
            entity.setCurrentHealthValue(entity.getMaxHealth());
        } else {
            entity.regenerateHealth(restoreHealth);
        }
    }

    /**
     * Set the position for the shield to follow
     * the parent entity
     *
     * @param i on tick time
     */
    @Override
    public void onTick(long i) {
        this.setPosition(entity.getCol(), entity.getRow(), this.getRenderOrder());

        // Check if lifetime has expired
        stateTimer += Gdx.graphics.getDeltaTime();
        if (animationFrames.isAnimationFinished(stateTimer)) {
            GameManager.get().getWorld().removeEntity(this);
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return animationFrames.getKeyFrame(stateTimer, true);
    }
}
