package deco2800.thomas.managers;

import com.badlogic.gdx.physics.box2d.World;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.worlds.WorldEvent;

import java.util.concurrent.CopyOnWriteArrayList;

public class WorldEventManager extends TickableManager {
    // A list for storing all status effects
    private WorldEvent currentEvent;

    /**
     * Creates a WorldEventManager instance which managers the events in each world.
     */
    public WorldEventManager() {

    }

    /**
     * Set the current Event of the world in which the player is in.
     *
     * @param worldEvent An implemented instance of the abstract World Event class.
     */
    public <T extends WorldEvent> void setEvent(T worldEvent) {
        currentEvent = worldEvent;
    }

    /**
     * Removes the worlds current event, if the player teleports to a new world
     * the event will consequently be removed to save memory.
     *
     */
    public void removeCurrentEvent() {
        currentEvent.removeEvent();
        currentEvent = null;
    }

    /**
     * Checks each tick whether the event should be triggered.
     *
     * @param i Tick count
     */
    public void onTick(long i) {
            if (currentEvent != null && !currentEvent.getTriggered()) {
                currentEvent.triggerEvent();
            }
    }
}
