package deco2800.thomas.entities.attacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.Tickable;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class SandTornado extends Projectile implements Animatable, Tickable {
    private final Animation<TextureRegion> tornado;
    private float stateTimer = 0f;


    public SandTornado() {
        super();
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
        tornado = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("sandTornado"));
    }

    public SandTornado(float col, float row, int damage, float speed, EntityFaction faction) {
        super(col, row, RenderConstants.PROJECTILE_RENDER, damage, speed, faction);
        this.setTexture("explosion");
        this.setObjectName("combatExplosion");
        tornado = new Animation<TextureRegion>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("sandTornado"));
    }

    @Override
    public TextureRegion getFrame(float delta) {
        stateTimer += delta;
        if (stateTimer > tornado.getAnimationDuration()) {
            stateTimer = 0;
        }
        return tornado.getKeyFrame(stateTimer);
    }
}