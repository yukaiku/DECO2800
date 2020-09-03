package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.entities.Tree;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

import java.util.Random;

public class DesertWorld extends AbstractWorld {

    private boolean notGenerated = true;
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_map.json";

    /**
     * Constructor that creates a world with default width and height
     */
    public DesertWorld() {
        super();
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
    }

    /**
     * Constructor that creates a world with given width and height
     *
     * @param width  width of the world; horizontal coordinates of the world will be within `[-width, width]`
     * @param height eight of the world; vertical coordinates of the world will be within `[-height, height]`
     */
    public DesertWorld(int width, int height) {
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
    }

    /**
     * Generates the tiles for the world
     */
    @Override
    protected void generateTiles() {
    }

    public void createStaticEntities() {
        int tileCount = GameManager.get().getWorld().getTiles().size();
        TextureManager tex = new TextureManager();

        // Check each tile for specific textures which indicate that an entity must be added
        for (Tile tile : tiles) {

            // make the three main wall sections of the world with rocks
            if (tile.getTextureName().equals("desert_5")) {

                // change if new texture is drawn
                entities.add(new Rock(tile, true));
            }

            // make the outer wall of the world with rocks
            if (tile.getTextureName().equals("desert_6")) {
                // change if new texture is drawn
                entities.add(new Rock(tile, true));
            }

            // add the cactus plants
            if (tile.getTextureName().equals("desert_3")) {
                // change if new texture is drawn
                entities.add(new Tree(tile, true));
            }

            // add the quicksand
            if (tile.getTextureName().equals("desert_7")) {
                // add once texture is drawn
            }
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }
    }
}
