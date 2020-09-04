package deco2800.thomas.worlds.desert;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.desert.*;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.lwjgl.Sys;

import java.util.Random;

/**
 * The Desert World of the game.
 * Contains obstacles such as quicksand and cactus plants that force the player
 * to be careful about their movement.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertWorld extends AbstractWorld {

    // a bool to signal that entities still need to be generated
    private boolean notGenerated = true;

    // the save file location for the desert zone map
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_map.json";

    /**
     * Constructor that creates a world with default width and height.
     */
    public DesertWorld() {
        super();
        generateTiles();
    }

    /**
     * Constructor that creates a world with given width and height.
     *
     * @param width  width of the world; horizontal coordinates of the world will be within `[-width, width]`
     * @param height eight of the world; vertical coordinates of the world will be within `[-height, height]`
     */
    public DesertWorld(int width, int height) {
        super(width, height);
        generateTiles();
    }

    /**
     * Returns the type of this world.
     *
     * @return The type of this world.
     */
    @Override
    public String getType() {
        return "Desert";
    }

    /**
     * Generates the tiles for the world by opening a save file.
     */
    @Override
    protected void generateTiles() {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);

        // Add the player entity
        addEntity(new PlayerPeon(5f, -23f, 0.1f));
    }

    /**
     * Creates the static entities to populate the world and makes some tiles obstructed.
     * This includes sand dunes, cactus plants, dead trees and quicksand.
     */
    public void createStaticEntities() {
        int tileCount = GameManager.get().getWorld().getTiles().size();
        TextureManager tex = new TextureManager();
        Random rand = new Random();
        int randIndex;

        // add the orb to a specific location
        entities.add(new DesertOrb(GameManager.get().getWorld().getTile(21, 6)));

        // Check each tile for specific textures which indicate that an entity must be added
        for (Tile tile : tiles) {

            switch (tile.getTextureName()) {
                // make the wall sections of the world with sand dunes
                case "desert_5":
                case "desert_6":
                    entities.add(new DesertSandDune(tile));
                    break;

                // add the cactus plants and dead trees
                case "desert_3":
                    // half of all plant spawn locations are cacti - half are dead trees
                    if (tile.getType().equals("Cactus")) {
                        entities.add(new DesertCactus(tile));
                    } else {
                        entities.add(new DesertDeadTree(tile));
                    }
                    break;

                // add the quicksand
                case "desert_7":
                    entities.add(new DesertQuicksand(tile));
                    break;

                // add the oasis plants
                case "oasis_1":
                case "oasis_2":
                case "oasis_3":
                    // one third of grass area should be plants, with half tree - half shrub
                    randIndex = rand.nextInt(6);
                    if (randIndex == 0) {
                        entities.add(new OasisShrub(tile));
                    } else if (randIndex == 1) {
                        entities.add(new OasisTree(tile));
                    }
                    break;

                // make the oasis water obstructed
                case "oasis_4":
                case "oasis_5":
                    tile.setObstructed(true);
                    break;
            }
        }
    }

    /**
     * Handles what happens after each tick of the game.
     */
    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        // generates the static entities if they have not been created
        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }
    }
}
