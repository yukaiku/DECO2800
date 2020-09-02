package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.entities.Tree;
import deco2800.thomas.managers.GameManager;

import java.util.Random;

public class DesertWorld extends AbstractWorld {

    private boolean notGenerated = true;

    /**
     * Constructor that creates a world with default width and height
     */
    public DesertWorld() {
        super();
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
        Random rand = new Random();

        // makes all tiles for the world
        for (int x = -width; x < width; x++) {
            for (int y = -height; y < height; y++) {
                // randomly assign a tile one of two colours
                if (rand.nextInt(3) > 0) {
                    tiles.add(new DesertTile("desert_1", x, y));
                } else {
                    tiles.add(new DesertTile("desert_2", x, y));
                }
            }
        }

        // Sets the top/bottom of the world to a darker tile shade
        for (int x = -width; x < width; x++) {
            getTile(x, -height).setTexture("desert_6");
            getTile(x, height - 1).setTexture("desert_6");

            // randomly sets half of the tiles to darker shades one off the top/bottom
            if (rand.nextInt(2) == 0) {
                getTile(x, -height + 1).setTexture("desert_6");
            }
            if (rand.nextInt(2) == 0) {
                getTile(x, height - 2).setTexture("desert_6");
            }
        }

        // Sets the left/right of the world to a darker tile shade
        for (int y = -height; y < height; y++) {
            getTile(-width, y).setTexture("desert_6");
            getTile(width - 1, y).setTexture("desert_6");

            // randomly sets half of the tiles to darker shades one off the left/right
            if (rand.nextInt(2) == 0) {
                getTile(-width + 1, y).setTexture("desert_6");
            }
            if (rand.nextInt(2) == 0) {
                getTile(width - 2, y).setTexture("desert_6");
            }
        }

        // Creates the player entity
        addEntity(new PlayerPeon(10f, 5f, 0.1f));
    }

    public void createWalls() {
        int[] leftWallX = new int[]{-8, -8, -8, -8, -8, -8, -7, -7, -7, -7, -7,
                -7, -6, -6, -6, -6, -5, -5, -5, -5};

        int[] rightWallX = new int[]{12, 12, 12, 12, 11, 11, 11, 11, 10,
                10, 10, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7};

        int[] topWallStartY = new int[]{3, 4, 5, 5, 6, 6, 7, 7, 8, 9, 10};

        // make the left wall, closing the south-west area
        for (int i = 0; i < leftWallX.length; i++) {
            Tile tile = getTile(leftWallX[i], i - 25);
            if (tile != null) {
                entities.add(new Rock(tile, true));
                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (neighbour.getCol() <= tile.getCol()) {
                        entities.add(new Rock(neighbour, true));
                    }
                }
            }
        }

        // make the right wall, closing the south-east area
        for (int i = 0; i < rightWallX.length; i++) {
            Tile tile = getTile(rightWallX[i], i - 25);
            if (tile != null) {
                entities.add(new Rock(tile, true));
                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (neighbour.getCol() <= tile.getCol()) {
                        entities.add(new Rock(neighbour, true));
                    }
                }
            }
        }

        // make the start of the top wall, to enclose the north-east area
        for (int i = 0; i < topWallStartY.length; i++) {
            Tile tile = getTile(i-10, topWallStartY[i]);
            if (tile != null) {
                entities.add(new Rock(tile, true));
                for (Tile neighbour : tile.getNeighbours().values()) {
                    entities.add(new Rock(neighbour, true));
                }
            }
        }
    }


    public void createStaticEntities() {
        Random rand = new Random();
        int tileCount = GameManager.get().getWorld().getTiles().size();

        // Makes 20 patches of darker tiles randomly around the world
        for (int i = 0; i < 20; i++) {
            Tile tile = this.getTile(rand.nextInt(tileCount));
            for (Tile neighbour : tile.getNeighbours().values()) {
                for (Tile outer : neighbour.getNeighbours().values()) {
                    outer.setTexture("desert_3");
                }
                neighbour.setTexture("desert_4");
            }
            tile.setTexture("desert_4");
        }

        // 100 Rocks randomly placed
        for (int i = 0; i < 100; i++) {
            Tile tile = this.getTile(rand.nextInt(tileCount));
            if (tile != null) {
                entities.add(new Rock(tile, true));
            }
        }

        // 50 Trees randomly placed
        for (int i = 0; i < 50; i++) {
            Tile t = this.getTile(rand.nextInt(tileCount));
            if (t != null) {
                entities.add(new Tree(t, true));
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
            createWalls();
            notGenerated = false;
        }
    }
}
