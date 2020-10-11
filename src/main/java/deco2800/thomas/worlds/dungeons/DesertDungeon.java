package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.monsters.ImmuneOrc;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.entities.environment.desert.*;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.Random;

public class DesertDungeon extends AbstractWorld {
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_dungeon_map.json";
    private boolean notGenerated = true;

    /**
     * Default Constructor for volcano world.
     */
    public DesertDungeon() {
        this(50, 50);
    }

    public DesertDungeon(int width, int height){
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-2f, -11f, 0.15f));
        addEntity(this.getPlayerEntity());

        GameManager.get().addManager(new EnemyManager(this));
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.spawnSpecialEnemy("immuneOrc", 0, 0);
    }

    @Override
    protected void generateTiles() {

    }

    /**
     * Creates the static entities to populate the world and makes some tiles obstructed.
     * This includes sand dunes, cactus plants, dead trees and quicksand.
     */
    public void createStaticEntities() {
        Random rand = new Random();
        int randIndex;

        // Check each tile for specific textures which indicate that an entity must be added
        for (Tile tile : tiles) {

            switch (tile.getTextureName()) {
                // make the wall sections of the world with sand dunes
                case "desert_6":
                    entities.add(new DesertSandDune(tile));
                    break;

                // add the cactus plants and dead trees
                case "desert_3":
                    // half of all plant spawn locations are cacti - half are dead trees
                    if (tile.getType().equals("Cactus")) {
                        // get a random cactus texture
                        randIndex = rand.nextInt(4);
                        entities.add(new DesertCactus(tile, String.format("desertCactus%d", randIndex + 1)));

                        // set neighbours to damage player
                        for (Tile t : tile.getNeighbours().values()) {
                            t.setType("CactusNeighbour");
                            t.setStatusEffect(true);
                        }
                    } else {
                        // get a random dead tree texture
                        randIndex = rand.nextInt(2);
                        entities.add(new DesertDeadTree(tile, String.format("desertDeadTree%d", randIndex + 1)));
                    }
                    break;

                // add the quicksand
                case "desert_7":
                    entities.add(new DesertQuicksand(tile));
                    break;
            }
        }
    }

    /**
     * Manages actions taking place for volcano world every tick.
     * @param i - tick count
     */
    public void onTick(long i) {
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }
        super.onTick(i);
    }
}
