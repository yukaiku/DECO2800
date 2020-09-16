package deco2800.thomas.worlds;

public abstract class WorldEvent {
    private boolean triggered;
    private long duration = 30000;
    private long lastTimeTicked;
    private AbstractWorld world;

    public WorldEvent(AbstractWorld world) {
        this.world = world;
    }

    public WorldEvent(AbstractWorld world, long duration) {
        this.duration = duration;
        this.world = world;
    }

    public void setTriggered(boolean triggeredState) {
        triggered = triggeredState;
    }

    public boolean getTriggered() {
        return triggered;
    }

    public AbstractWorld getWorld(){
        return world;
    }

    public void triggerEvent() {

    }

    public void removeEvent() {

    }

}
