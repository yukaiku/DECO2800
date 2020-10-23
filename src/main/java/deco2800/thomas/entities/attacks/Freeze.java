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
    private float stateTimer = 0f;
    private float direction;

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
        this.direction = direction;
        this.setColRenderLength(0.3f);
        this.setRowRenderLength(1.0f);
        this.setTexture("explosion");
        this.setDamageType(DamageType.ICE);
        this.setDefaultState(new Animation<>(0.02f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("freezeTile")));
    }

    /**
     * Returns the direction this entity is moving.
     * @return Direction entity is moving.
     */
    @Override
    public float getDirection() {
        return this.direction;
    }

    @Override
    public void onTick(long i) {
        // Update combat task
        if (combatTask != null) {
            combatTask.onTick(i);
            if (combatTask.isComplete()) {
                combatTask = null;
            }
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        if (stateTimer >= defaultState.getAnimationDuration()) {
            WorldUtil.removeEntity(this);
        }
        region = defaultState.getKeyFrame(stateTimer);
        stateTimer = stateTimer + delta;
        return region;
    }
}
