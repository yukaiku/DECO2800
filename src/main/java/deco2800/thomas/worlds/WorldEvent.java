package deco2800.thomas.worlds;

public abstract class WorldEvent {
    private boolean active;
    private AbstractWorld world;

    public WorldEvent(AbstractWorld world) {
        this.world = world;
    }

    public void setActive(boolean activeState) {
        active = activeState;
    }

    public boolean getActive() {
        return active;
    }

    public AbstractWorld getWorld(){
        return world;
    }

    public void triggerEvent() {

    }

    public void removeEvent() {

    }

}
