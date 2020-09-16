package deco2800.thomas.entities.Environment.volcano;

import deco2800.thomas.entities.Agent.HasHealth;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;


public class VolcanoBurningTree extends StaticEntity implements HasHealth {
    private static final String ENTITY_ID_STRING = "VolcanoBurningTree";
    private int health = 100;

    public VolcanoBurningTree() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public VolcanoBurningTree(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.ROCK_RENDER, "BurningTree", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {

    }

    @Override
    public int getHealth() {
        return health;
    }

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
