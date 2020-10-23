package deco2800.thomas.entities.environment.volcano;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.agent.HasHealth;
import deco2800.thomas.worlds.Tile;


/**
 * A Burning tree entity that spawns in the VolcanoWorld.
 *
 *
 * @author Arthur Mitchekk (Gitlab: @ArthurM99115)
 */
public class VolcanoBurningTree extends StaticEntity implements HasHealth {
    private static final String ENTITY_ID_STRING = "VolcanoBurningTree";
    private int health = 100;

    /**
     * A default constructor for the VolcanoBurnTree class that inherits from Static
     * Entity with default textures & no tile given.
     *
     */
    public VolcanoBurningTree() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     * A constructor for the VolcanoBurningTree class that inherits from Static
     * Entity with given tile & obstructed values.
     *
     * @param tile A Tile instance that resides within the VolcanoWorld.
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public VolcanoBurningTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.ROCK_RENDER, "BurningTree", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {

    }

    /**
     * Returns the health of the burning tree
     * @return an integer value of the tree's health.
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the burning tree
     *
     * @param health the new health value for the respective tree
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rock{");
        sb.append("health=").append(health);
        sb.append(", children=").append(children);
        sb.append(", position=").append(position);
        sb.append('}');
        sb.append("\n");
        return sb.toString();
    }
}
