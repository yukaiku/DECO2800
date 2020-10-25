package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.WorldUtil;

public class Freeze extends Projectile implements Animatable {
    /**
     * Parametric constructor, that sets the initial conditions of the explosion
     * as well as texture and name.
     * @param col X position
     * @param row Y position
     * @param damage Damage to apply on impact
     * @param faction EntityFaction of the explosion
     */
    public Freeze (float col, float row, int damage, EntityFaction faction, float direction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, 0, faction);
        this.setObjectName("freezeWave");
        this.setDirection(direction);
        this.setColRenderLength(0.3f);
        this.setRowRenderLength(1.0f);
        this.setTexture("explosion");
        this.setDamageType(DamageType.ICE);
        this.setDefaultState(new Animation<>(0.02f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("freezeTile")));
    }

    /**
     * Only update the combat task, not the movement task.
     * @param i current game tick
     */
    @Override
    public void onTick(long i) {
        // Update combat task
        if (combatTask != null) {
            combatTask.onTick(i);
            if (combatTask.isComplete()) {
                combatTask = null;
            }
        }

        // Remove entity if timer exceeds lifetime
        if (stateTimer >= defaultState.getAnimationDuration()) {
            WorldUtil.removeEntity(this);
        }
    }

    /**
     * Override getFrame to prevent it from looping.
     * @param delta the interval of the ticks
     * @return TextureRegion of current frame to render
     */
    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = defaultState.getKeyFrame(stateTimer);
        stateTimer = stateTimer + delta;
        return region;
    }
}
