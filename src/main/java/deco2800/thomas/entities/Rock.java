package deco2800.thomas.entities;
import deco2800.thomas.worlds.Tile;

public class Rock extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "rock";
    
    public Rock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Rock(Tile tile, boolean obstructed) {
        super(tile, RenderConstants.ROCK_RENDER, "rock", obstructed);
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
}
