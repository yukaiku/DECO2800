package deco2800.thomas.worlds.volcano;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.*;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.VolcanoDragonSkull;
import deco2800.thomas.entities.VolcanoGraveYard;
import deco2800.thomas.entities.VolcanoRuins;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.environment.volcano.VolcanoBurningTree;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VolcanoWorld extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/volcano/VolcanoZone.json";

    private final int ORB_COLUMN = 21;
    private final int ORB_ROW = 20;

    private boolean notGenerated = true;

    /**
     * Default Constructor for volcano world.
     */
    public VolcanoWorld() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for the volcano world.
     *
     * @param width - World height
     * @param height - World Width
     */
    public VolcanoWorld(int width, int height) {
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        //Add player to game
        this.setPlayerEntity(new PlayerPeon(-3f, -24f, 0.15f));
        addEntity(this.getPlayerEntity());

        GameManager.get().removeManager(GameManager.get().getManager(EnemyManager.class));
        Orc volcanoOrc = new Orc(1, 0.09f, 50, "orc_volcano");
        EnemyManager enemyManager = new EnemyManager(this, 5, Arrays.asList(volcanoOrc));
        GameManager.get().addManager(enemyManager);

        Dragon boss = new Dragon(3, 0.03f, 1000, "dragon_volcano");
        enemyManager.setBoss(boss);
        enemyManager.spawnBoss(16, 20);
    }


    /**
     * Generates the tiles for the world
     */
    @Override
    protected void generateTiles() {
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
            for (Tile t : GameManager.get().getWorld().getTiles()) {
            }
            notGenerated = false;
            System.out.println(this.getEntities());
            System.out.flush();
        }
        super.onTick(i);
    }

    /**
     * Generates static entities for VolcanoZone by calling helper methods that
     * add parts to respective static entities that are going to be added to the
     * world's list of entities.
     *
     * This includes:
     * - The Dragon skull (Side quest entity)
     * - Ruins (Building that will contain enemies & possible future items)
     * - Graveyard (Area that will contain enemies & possible future items)
     *
     */
    public void createStaticEntities() {

        //Add Main Static entities
        entities.add(createGraveYard(7, -15));
        entities.add(createRuins(-25, -5));
        entities.add(createDragonSkull(-23, 23));
        //For objects that are added randomly & require more specific addition
        //entities, they're methodology will folllow add()
        addRandoms();
    }

    /**
     * Adds 20 random Static entities with health (Either a burning tree or boulder)
     * to the map tiles that do not have lava textures (shades of red/yellow).
     */
    public void addRandoms() {
    //Add random boulders on tiles that aren't Lava
        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTiles().size();

        for (int i = 0; i < 20; i++) {

            //Get respective volcano tile (5 <= Lava tiles Index <= 8
            Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
            String tileTexture = t.getTextureName();
            int tileNumber = Integer.parseInt(tileTexture.split("_")[1]);

            int selector = random.nextInt(2);
            if (t != null && tileNumber < 5 && tileNumber > 1 && selector == 1) {
                entities.add(new Rock(t, true));
            } else if (t != null && (tileNumber == 3 || tileNumber == 4) &&
                    selector == 0) {
                entities.add(new VolcanoBurningTree(t, true));
            }else {
                i--;
            }
        }
    }

    /**
     *  Creates a dragon skull entity to be added to the zone
     *
     * @param col - The specified column coordinate of the orb.
     * @param row - The specified row coordinate of the orb.
     * @return A static entity for the Volcano Zone
     */
    public VolcanoDragonSkull createDragonSkull(float col, float row) {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(new SquareVector(0, 0), "DragonSkull", true));
        VolcanoDragonSkull dragonSkull = new VolcanoDragonSkull(col, row, parts);
        return dragonSkull;
    }

    /**
     *  Creates a ruins entity to be added to the world's list of entities. The
     *  Ruins are currently located on the left-hand side of the volcano zone.
     *
     * @param col - The specified column coordinate of the orb.
     * @param row - The specified row coordinate of the orb.
     * @return A static entity for the Volcano Zone
     */
    public VolcanoRuins createRuins(float col, float row) {
        List<Part> parts = new ArrayList<Part>();

        //Back Wall
        for (int i = 1; i < 16; i++) {
            parts.add(new Part(new SquareVector(0, -i), "Ruins_2", true));
        }

        //Top & Bottom walls
        parts.add(new Part(new SquareVector(2, 0), "Ruins_2", true));
        parts.add(new Part(new SquareVector(3, 0), "Ruins_2", true));
        parts.add(new Part(new SquareVector(4, 0), "Ruins_2", true));
        parts.add(new Part(new SquareVector(5, 0), "Ruins_2", true));
        parts.add(new Part(new SquareVector(6, 0), "Ruins_2", true));

        parts.add(new Part(new SquareVector(1, -15), "Ruins_2", true));
        parts.add(new Part(new SquareVector(2, -15), "Ruins_2", true));
        parts.add(new Part(new SquareVector(3, -15), "Ruins_2", true));
        parts.add(new Part(new SquareVector(4, -15), "Ruins_2", true));

        //Front Walls
        parts.add(new Part(new SquareVector(7, -1), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -2), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -5), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -6), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -10), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -11), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -12), "Ruins_5", true));
        parts.add(new Part(new SquareVector(7, -15), "Ruins_5", true));

        //Inner pillars
        parts.add(new Part(new SquareVector(3, -3), "Ruins_5", true));
        parts.add(new Part(new SquareVector(4, -4), "Ruins_5", true));

        parts.add(new Part(new SquareVector(4, -11), "Ruins_5", true));
        parts.add(new Part(new SquareVector(3, -12), "Ruins_5", true));

        //Front Top Pillars
        parts.add(new Part(new SquareVector(9, -5), "Ruins_4", true));
        parts.add(new Part(new SquareVector(9, -6), "Ruins_4", true));
        parts.add(new Part(new SquareVector(10, -6), "Ruins_4", true));

        //Front Bottom Pillars
        parts.add(new Part(new SquareVector(9, -9), "Ruins_4", true));
        parts.add(new Part(new SquareVector(9, -10), "Ruins_4", true));
        parts.add(new Part(new SquareVector(10, -9), "Ruins_4", true));

        //Bones at Ruins
        parts.add(new Part(new SquareVector(4, -3), "Bones", false));
        parts.add(new Part(new SquareVector(3, -4), "Bones", false));

        parts.add(new Part(new SquareVector(3, -11), "Bones", false));
        parts.add(new Part(new SquareVector(4, -12), "Bones", false));

        VolcanoRuins Ruins = new VolcanoRuins(col, row, parts);
        return Ruins;
    }

    /**
     *  Creates the Graveyard Area to be added to the world's list of entities.
     *  The Ruins are currently located on the left-hand side of the volcano zone.
     *
     * @param col - The specified column coordinate of the orb.
     * @param row - The specified row coordinate of the orb.
     * @return  A static entity for the Volcano Zone
     */
    public VolcanoGraveYard createGraveYard(float col, float row) {
        List<Part> parts = new ArrayList<Part>();
        //Top left
        parts.add(new Part(new SquareVector(1, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(2, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(3,  0), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(0, 0), "fenceS-E", true));
        //Top right
        parts.add(new Part(new SquareVector(9, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(10, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(11,  0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(12, 0), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(13, 0), "fenceS-W", true));

        // Bottom Left
        parts.add(new Part(new SquareVector(1, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(2, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(3,  -7), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(0, -7), "fenceN-E", true));

        //Bottom right
        parts.add(new Part(new SquareVector(9, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(10, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(11,  -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(12, -7), "fenceE-W", true));
        //Bottom right corner
        parts.add(new Part(new SquareVector(13, -7), "fenceN-W", true));

        //Verticle sides Left
        parts.add(new Part(new SquareVector(0, -6), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -5), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0,  -2), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -1), "fenceN-S", true));
        //Verticle sides Right
        parts.add(new Part(new SquareVector(13, -6), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13, -5), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13,  -2), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13, -1), "fenceN-S", true));

        // Corner Trees
        parts.add(new Part(new SquareVector(1, -1), "tree", true));
        parts.add(new Part(new SquareVector(12, -1), "tree", true));
        parts.add(new Part(new SquareVector(1,  -6), "tree", true));
        parts.add(new Part(new SquareVector(12, -6), "tree", true));

        //Bones top corners
        parts.add(new Part(new SquareVector(1, -2), "Bones", false));
        parts.add(new Part(new SquareVector(2, -1), "Bones", false));
        parts.add(new Part(new SquareVector(11,  -1), "Bones", false));
        parts.add(new Part(new SquareVector(12, -2), "Bones", false));

        //Bones Bottom corners
        parts.add(new Part(new SquareVector(1, -5), "Bones", false));
        parts.add(new Part(new SquareVector(2, -6), "Bones", false));
        parts.add(new Part(new SquareVector(11,  -6), "Bones", false));
        parts.add(new Part(new SquareVector(12, -5), "Bones", false));

        //Intersection corners
        parts.add(new Part(new SquareVector(5, -2), "Ruins_4", true));
        parts.add(new Part(new SquareVector(8, -2), "Ruins_4", true));
        parts.add(new Part(new SquareVector(5,  -5), "Ruins_4", true));
        parts.add(new Part(new SquareVector(8, -5), "Ruins_4", true));
        VolcanoGraveYard graveYard = new VolcanoGraveYard(col, row, parts);

        return graveYard;
    }

    /**
     * Volcano EventHandler method that will be called on in future builds
     * once a condition in gameticks passes.
     *
     */
    public void volcanoEvent () {

    }
}
