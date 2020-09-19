package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.WorldUtil;

public class Freeze extends CombatEntity implements Animatable {
    private final Animation<TextureRegion> freeze;
    private float stateTimer = 0f;

    /**
     * Default constructor, sets texture and object name.
     */
    public Freeze() {
        super();
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
        freeze = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("fireballExplosion"));
    }

    /**
     * Parametric constructor, that sets the initial conditions of the explosion
     * as well as texture and name.
     * @param col X position
     * @param row Y position
     * @param damage Damage to apply on impact
     * @param faction EntityFaction of the explosion
     */
    public Freeze (float col, float row, int damage, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, faction);
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
        freeze = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("freezeTile"));
    }

    @Override
    public void onTick(long i) {
        // Update combat task
        if (combatTask != null) {
            if (combatTask.isComplete()) {
                WorldUtil.removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            WorldUtil.removeEntity(this);
        }
    }

    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        return freeze.getKeyFrame(stateTimer);
    }
}
