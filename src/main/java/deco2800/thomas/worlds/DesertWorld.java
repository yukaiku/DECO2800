package deco2800.thomas.worlds;

import deco2800.thomas.entities.PlayerPeon;

import java.util.Random;

public class DesertWorld extends AbstractWorld {

    /**
     * Constructor that creates a world with default width and height
     */
    public DesertWorld() {
    }

    /**
     * Constructor that creates a world with given width and height
     *
     * @param width  width of the world; horizontal coordinates of the world will be within `[-width, width]`
     * @param height eight of the world; vertical coordinates of the world will be within `[-height, height]`
     */
    public DesertWorld(int width, int height) {
        super(width, height);
    }

    /**
     * Generates the tiles for the world
     */
    @Override
    protected void generateTiles() {
        int index;
        Random rand = new Random();
        String[] names = new String[]{"desert_1", "desert_2", "desert_3", "desert_4", "desert_5", "desert_6"};

        for (int x = -width; x < width; x++) {
            for (int y = -height; y < height; y++) {
                index = rand.nextInt(6);
                tiles.add(new DesertTile(names[index], x, y));
            }
        }


        // Create the entities in the game
        addEntity(new PlayerPeon(10f, 5f, 0.1f));
    }
}
