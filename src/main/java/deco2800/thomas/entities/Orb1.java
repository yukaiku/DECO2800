package deco2800.thomas.entities;

import deco2800.thomas.Tickable;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Orb1 extends StaticEntity implements Tickable {
    private final Logger LOG = LoggerFactory.getLogger(Orb1.class);

    AbstractWorld world;


    public Orb1(float col, float row, int renderOrder, List<Part> parts) {
        super(col, row, renderOrder, parts);
        LOG.info("Making a orb at {}, {}", col, row);
        this.setTexture("orb1");
    }

    public Orb1(Tile t, boolean obstructed) {
        super(t, RenderConstants.ORB, "orb1", obstructed);
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Orb1)) {
            return false;
        }
        Orb1 otherOrbs = (Orb1) other;
        if (this.getCol() != otherOrbs.getCol() || this.getRow() != otherOrbs.getRow() || this.getHeight() != otherOrbs.getHeight()) {
            return false;
        }
        return true;
    }


    /**
     * Gets the hashCode of the orbs.
     *
     * @return the hashCode of the orbs
     */
    @Override
    public int hashCode() {
        final float prime = 31;
        float result = 1;
        result = (result + super.getCol()) * prime;
        result = (result + super.getRow()) * prime;
        result = (result + super.getHeight()) * prime;
        return (int) result;
    }


    /**
     * Animates the orbs on every game tick.

     * @param tick current game tick
     */
    @Override
    public void onTick(long tick) {
    }
}
