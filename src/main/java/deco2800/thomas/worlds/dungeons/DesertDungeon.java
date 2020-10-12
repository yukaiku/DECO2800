package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.desert.*;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonDialog;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonOpeningDialog;

import java.util.Random;

/**
 * A dungeon for the desert world, where a player must use the
 * environment to kill an otherwise immune enemy.
 */
public class DesertDungeon extends AbstractWorld {

    // the save file location to load this dungeon
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_dungeon_map.json";

    // whether the static entities for this world have been generated
    private boolean notGenerated = true;

    // a skin for the opening dialog box of the dungeon
    private final Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

    /**
     * Creates a Desert Dungeon with size 50x50.
     */
    public DesertDungeon() {
        this(50, 50);
    }

    /**
     * Creates a Desert Dungeon with specified width and height.
     *
     * @param width The specified width.
     * @param height The specified height.
     */
    public DesertDungeon(int width, int height){
        // loads the world
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-2f, -11f, 0.15f));
        addEntity(this.getPlayerEntity());

        // spawns the immune orc enemy
        GameManager.get().addManager(new EnemyManager(this));
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.spawnSpecialEnemy("immuneOrc", -2f, 8f);

        // sets up all types of dialogs
        Stage stage = GameManager.get().getStage();
        DesertDungeonDialog.setup(stage, skin);

        // displays the opening dialog to help kill the enemy
        DesertDungeonOpeningDialog announcementDialog = new DesertDungeonOpeningDialog("The Desert Dungeon", skin);
        announcementDialog.show();
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
