package deco2800.thomas.worlds.volcano;

import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.WorldEvent;

public class VolcanoEvent extends WorldEvent {

    public VolcanoEvent(VolcanoWorld world, long duration) {
        super(world, duration);
    }

    public void triggerEvent() {
        System.out.println("EEEE");
    }

    public void removeEvent() {

    }


}
