package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.*;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.swamp.SwampWorld;
import deco2800.thomas.worlds.tundra.TundraWorld;
import deco2800.thomas.worlds.volcano.VolcanoWorld;

import java.util.Timer;
import java.util.TimerTask;

public class EventRenderer implements Renderer {
    private static final String COMMON_DIR_PATH = "resources/particles/";

    private static final int SWAMP_EVENT_ACTIVE = 60;
    private static final int SWAMP_EVENT_INACTIVE = 20;

    private static final int TUNDRA_EVENT_ACTIVE = 120;
    private static final int TUNDRA_EVENT_INACTIVE = 30;

    private static final int DESERT_EVENT_ACTIVE = 120;
    private static final int DESERT_EVENT_INACTIVE = 60;

    private static final int VOLCANO_EVENT_ACTIVE = 20;
    private static final int VOLCANO_EVENT_INACTIVE = 10;

    private static final int TEST_EVENT_ACTIVE = 20;
    private static final int TEST_EVENT_INACTIVE = 10;

    private ParticleEffect particleEffect;

    private Timer timer = new Timer();
    private int counter = 0;
    private int eventDuration;

    private boolean allowRendering;
    private boolean originalAllowRendering;

    public EventRenderer(boolean allowRendering) {
        this.allowRendering = allowRendering;
        this.originalAllowRendering = allowRendering;

        eventDuration = getEventDuration();

        particleEffect = new ParticleEffect();
        loadParticleFile();
        particleEffect.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (counter == eventDuration) {
                    setAllowRendering(!getAllowRendering());
                    eventDuration = getEventDuration();
                    counter = -1;
                }
                counter++;
            }
        }, 0, 1000);

    }

    private boolean getAllowRendering() {
        return allowRendering;
    }

    private void setAllowRendering(boolean allowRendering) {
        this.allowRendering = allowRendering;
    }

    private int getEventDuration() {
        AbstractWorld currentWorld = GameManager.get().getWorld();
        if (currentWorld instanceof SwampWorld) {
            return allowRendering ? SWAMP_EVENT_ACTIVE : SWAMP_EVENT_INACTIVE;
        } else if (currentWorld instanceof TundraWorld) {
            return allowRendering ? TUNDRA_EVENT_ACTIVE : TUNDRA_EVENT_INACTIVE;
        } else if (currentWorld instanceof DesertWorld) {
            return allowRendering ? DESERT_EVENT_ACTIVE : DESERT_EVENT_INACTIVE;
        } else if (currentWorld instanceof VolcanoWorld) {
            return allowRendering ? VOLCANO_EVENT_ACTIVE : VOLCANO_EVENT_INACTIVE;
        } else {
            return allowRendering ? TEST_EVENT_ACTIVE : TEST_EVENT_INACTIVE;
        }
    }

    private void loadParticleFile() {
        String filePath = null;
        String textureDir = null;
        AbstractWorld currentWorld = GameManager.get().getWorld();
        if (currentWorld instanceof SwampWorld) {
            filePath = "rain/blue-rain.p";
            textureDir = "rain";
        } else if (currentWorld instanceof TundraWorld) {
            filePath = "snow/white-snow.p";
            textureDir = "snow";
        } else if (currentWorld instanceof VolcanoWorld) {
            filePath = "rain/blue-rain.p";
            textureDir = "rain";
        } else if (currentWorld instanceof DesertWorld) {
            filePath = "sandstorm/sandstorm.p";
            textureDir = "sandstorm";
        } else {
            filePath = "rain/blue-rain.p";
            textureDir = "rain";
        }
        try {
            particleEffect.load(Gdx.files.internal(COMMON_DIR_PATH + filePath),
                    Gdx.files.internal(COMMON_DIR_PATH + textureDir));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetEventForNewWorld() {
        allowRendering = originalAllowRendering;
        counter = 0;
        eventDuration = getEventDuration();
        loadParticleFile();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (GameManager.get().movedToNextWorld) {
            resetEventForNewWorld();
            GameManager.get().movedToNextWorld = false;
        }
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
