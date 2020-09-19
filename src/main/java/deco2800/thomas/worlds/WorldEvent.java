package deco2800.thomas.worlds;

/**
 * An Abstract world class with skeleton methods to be used
 * in subclass implementation. Methods getActive & setActive are both called within
 * Event renderer which generates UI particles & also manages events.
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public abstract class WorldEvent {
    //Active state of the event
    private boolean active;
    //The world in which the event ocurs
    private AbstractWorld world;

    /**
     * Primary constructor for the WorldEvent class
     * @param world The world in which the event takes place.
     */
    public WorldEvent(AbstractWorld world) {
        this.world = world;
    }

    /**
     * Sets the active state of the event
     * @param activeState The new state of the event within the world.
     */
    public void setActive(boolean activeState) {
        active = activeState;
    }

    /**
     * Returns the current state of the event within its respective world
     * @return The active state of the event.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Returns the world in which the event is initialised with.
     * @return the world where the respective event occurs.
     */
    public AbstractWorld getWorld(){
        return world;
    }

    /**
     * Skeleton method that will update & modify tiles within the respective world.
     */
    public void triggerEvent() {

    }

    /**
     * Skeleton method that will return the world to its original state. This
     * method is called once the duration of the event has elasped.
     */
    public void removeEvent() {

    }

}
