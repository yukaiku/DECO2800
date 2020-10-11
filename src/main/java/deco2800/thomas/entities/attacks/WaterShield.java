package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

/**
 * The WaterShield for a target entity. Protect them
 * from all damage sources by locking the health
 * for that entity until the end of water shield's
 * lifer time
 */
public class WaterShield extends CombatEntity implements Animatable {
    private Peon entity;
    private final long lifetime;
    private long currentLifetime = 0;
    private final Animation<TextureRegion> animationFrames;
    private float stateTimer = 0f;

    public WaterShield(Peon entity, long lifeTime) {
        super(entity.getCol(), entity.getRow(), RenderConstants.PEON_EFFECT_RENDER, 0, entity.getFaction(), DamageType.COMMON);
        this.entity = entity;
        this.setColRenderLength(this.entity.getColRenderLength());
        this.setRowRenderLength(this.entity.getRowRenderLength());
        this.lifetime = lifeTime;
        this.entity.lockHealth();
        this.animationFrames = new Animation<>(0.13f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("waterShield"));
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
        if (++currentLifetime >= lifetime) {
            this.entity.unlockHealth();
            GameManager.get().getWorld().removeEntity(this);
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return animationFrames.getKeyFrame(stateTimer, true);
    }
}
