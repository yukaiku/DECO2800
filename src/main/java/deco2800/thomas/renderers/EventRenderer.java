package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import java.util.Timer;
import java.util.TimerTask;

public class EventRenderer implements Renderer {
    private static final int SWAMP_EVENT_ACTIVE = 10;
    private static final int SWAMP_EVENT_INACTIVE = 20;
    private static final int TUNDRA_EVENT_ACTIVE = 10;
    private static final int TUNDRA_EVENT_INACTIVE = 20;
    private static final int DESERT_EVENT_ACTIVE = 10;
    private static final int DESERT_EVENT_INACTIVE = 20;
    private static final int VOLCANO_EVENT_ACTIVE = 10;
    private static final int VOLCANO_EVENT_INACTIVE = 20;

    private ParticleEffect particleEffect;

    private Timer timer = new Timer();
    private int counter = 0;
    private int eventDuration;

    private boolean allowRendering;

    public EventRenderer(boolean allowRendering) {
        this.allowRendering = allowRendering;

        if (allowRendering) {
            eventDuration = SWAMP_EVENT_ACTIVE;
        } else {
            eventDuration = SWAMP_EVENT_INACTIVE;
        }

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("resources/particles/rain/blue-rain.p"),
                Gdx.files.internal("resources/particles/rain"));
        particleEffect.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (counter == eventDuration) {
                    if (getAllowRendering()) {
                        eventDuration = SWAMP_EVENT_INACTIVE;
                    } else {
                        eventDuration = SWAMP_EVENT_ACTIVE;
                    }
                    setAllowRendering(!getAllowRendering());
                    counter = -1;
                }
                counter++;
            }
        }, 0, 1000);

    }

    public boolean getAllowRendering() {
        return allowRendering;
    }

    public void setAllowRendering(boolean allowRendering) {
        this.allowRendering = allowRendering;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (this.allowRendering) {
            particleEffect.getEmitters().first().setPosition(camera.position.x - camera.viewportWidth / 2,
                    camera.position.y + camera.viewportHeight / 2);
            particleEffect.update(Gdx.graphics.getDeltaTime());

            batch.begin();

            particleEffect.draw(batch);

            batch.end();

            if (particleEffect.isComplete()) {
                particleEffect.reset();
            }
        }
    }
}
