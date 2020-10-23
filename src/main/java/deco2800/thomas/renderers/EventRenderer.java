package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;
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
        /*
        If allowRendering == true, the Zone Event occurs immediately when player enters the Zone.
        Otherwise, the Event starts after its respective inactive duration (set as constants above).
         */
        this.allowRendering = allowRendering;
        this.originalAllowRendering = allowRendering;

        eventDuration = getEventDuration();

        particleEffect = new ParticleEffect();
        loadParticleFile();
        particleEffect.start();

        /*
        Make use of Timer and TimerTask objects to count the duration during which
        an Event is active/inactive.
        The run function below is called once every 1 sec, incrementing counter until the activity of the
        Event needs to be switched from active to inactive and vice versa.
         */

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

    /**
     * Get the amount of time (in secs) for a Zone Event to be active or inactive
     * based on allowRendering (if the Event is allowed to render) and the Zone Type
     *
     * @return One of constants: SWAMP_EVENT_ACTIVE, SWAMP_EVENT_INACTIVE, TUNDRA_EVENT_ACTIVE,
     * TUNDRA_EVENT_INACTIVE, DESERT_EVENT_ACTIVE, DESERT_EVENT_INACTIVE, VOLCANO_EVENT_ACTIVE,
     * VOLCANO_EVENT_INACTIVE, TEST_EVENT_ACTIVE, TEST_EVENT_INACTIVE
     */
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

    /**
     * Load the correct Particle Effect file and texture(s) based on Zone Type
     */
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
            filePath = "embers/ember-effect.p";
            textureDir = "embers";
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

    /**
     * Called when the Player moves to the next Zone:
     * - Reset allowRendering back to its original value when the object was first initialized
     * and counter back to 0.
     * - Get new according eventDuration for the current new Zone.
     * - Load new according files for the current new Zone.
     */
    private void resetEventForNewWorld() {
        allowRendering = originalAllowRendering;
        counter = 0;
        eventDuration = getEventDuration();
        loadParticleFile();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // movedToNextWorld is a Boolean in GameManager, set to true whenever a World/Zone is set
        if (GameManager.get().getMovedToNextWorld()) {
            resetEventForNewWorld();
            GameManager.get().setMovedToNextWorld(false);
        }
        if (this.allowRendering && (GameManager.get().getWorld() instanceof SwampWorld ||
                GameManager.get().getWorld() instanceof DesertWorld ||
                GameManager.get().getWorld() instanceof TundraWorld ||
                GameManager.get().getWorld() instanceof VolcanoWorld)) {
            particleEffect.getEmitters().first().setPosition(camera.position.x - camera.viewportWidth / 2,
                    camera.position.y + camera.viewportHeight / 2);

            particleEffect.update(Gdx.graphics.getDeltaTime());

            batch.begin();
            particleEffect.draw(batch);
            batch.end();

            //Trigger updated entities & tiles for the respective event
            if (GameManager.get().getWorld().getWorldEvent() != null &&
                    !GameManager.get().getWorld().getWorldEvent().getActive()) {
                GameManager.get().getWorld().getWorldEvent().setActive(true);
                GameManager.get().getWorld().getWorldEvent().triggerEvent();
            }

            if (particleEffect.isComplete()) {
                particleEffect.reset();
            }
            //Restore original tile/entity updates to original state prior event
        } else if (GameManager.get().getWorld().getWorldEvent() != null &&
                GameManager.get().getWorld().getWorldEvent().getActive()) {
            GameManager.get().getWorld().getWorldEvent().setActive(false);
            GameManager.get().getWorld().getWorldEvent().removeEvent();
        }
    }
}
