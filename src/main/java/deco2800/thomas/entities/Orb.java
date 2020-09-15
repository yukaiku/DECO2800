package deco2800.thomas.entities;

import deco2800.thomas.Tickable;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Orb extends StaticEntity implements Tickable {
    private final Logger LOG = LoggerFactory.getLogger(Orb.class);

    AbstractWorld world;

    public Orb(float col, float row, String texture, int renderOrder, List<Part> parts) {
        super(col, row, renderOrder, parts);
        LOG.info("Making a orb at {}, {}", col, row);
    }

    public Orb(Tile t, String texture) {
        super(t, RenderConstants.ORB, texture, false);
        this.setTexture(texture);
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Orb)) {
            return false;
        }
        Orb otherOrb = (Orb) other;
        if (this.getCol() != otherOrb.getCol() || this.getRow() != otherOrb.getRow() || this.getHeight() != otherOrb.getHeight()) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orb{");
        sb.append("world=").append(world);
        sb.append(", children=").append(children);
        sb.append(", position=").append(position);
        sb.append('}');
        sb.append("\n");
        return sb.toString();
    }
}
