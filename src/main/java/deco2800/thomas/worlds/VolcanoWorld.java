package deco2800.thomas.worlds;

import deco2800.thomas.entities.PlayerPeon;

import java.util.Random;

public class VolcanoWorld extends AbstractWorld{
    private final int WORLD_WIDTH = 25; // Height and width vars for the map size; constrains tile gen
    private final int WORLD_HEIGHT = 25; // Note the map will double these numbers (bounds are +/- these limits)

    public VolcanoWorld() {
        super();
    }

    /**
     * Generates the tiles for the world
     */
    protected void generateTiles() {
        Random random = new Random();
        for (int q = -WORLD_WIDTH; q < WORLD_WIDTH; q++) {
            for (int r = -WORLD_HEIGHT; r < WORLD_HEIGHT; r++) {
                int elevation = random.nextInt(3);
                String type = "grass_";
                type += elevation;
                tiles.add(new Tile(type, q, r));
                //MAKE THE ABOVE SWAMP TILE
            }
        }

        // Create the entities in the game
        addEntity(new PlayerPeon(10f, 5f, 0.1f));
    }

    public void onTick(){

    }

    public void addCrater(){

    }

    public void addRuins(){

    }

    public void addLavaPool(){

    }

    public void addBones(){

    }

    public void volcanoEvent(){

    }


}
