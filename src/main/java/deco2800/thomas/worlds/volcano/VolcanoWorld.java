package deco2800.thomas.worlds.volcano;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.*;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.NPC.MerchantNPC;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.entities.NPC.TutorialNPC;
import deco2800.thomas.entities.NPC.VolcanoNPC;
import deco2800.thomas.entities.enemies.*;
import deco2800.thomas.entities.environment.volcano.VolcanoDragonSkull;
import deco2800.thomas.entities.environment.volcano.VolcanoGraveYard;
import deco2800.thomas.entities.environment.volcano.VolcanoRuins;
import deco2800.thomas.entities.environment.volcano.VolcanoBurningTree;
import deco2800.thomas.entities.items.HealthPotion;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Shield;
import deco2800.thomas.entities.items.Treasure;
import deco2800.thomas.managers.*;
import deco2800.thomas.entities.environment.volcano.*;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.enemies.dragons.VolcanoDragon;
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

/**
 * Implemented subclass of Abstract world for the Volcano Zone in Polyhedron.
 * This class generates & manages all objects related to the Volcano environment in the game including
 * relevant StaticEntities, Tiles & Textures.
 */
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
        generateItemEntities();

        // Provide available enemies to the EnemyManager
        Orc volcanoOrc = new Orc(Variation.VOLCANO, 50, 0.09f);
        VolcanoDragon boss = new VolcanoDragon(1000, 0.03f, 1);
        EnemyManager enemyManager = new EnemyManager(this, 7, Arrays.asList(volcanoOrc), boss);
        GameManager.get().addManager(enemyManager);
        enemyManager.spawnBoss(16, 20);

        //Create Volcano NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        npnSpawns.add(new VolcanoNPC("VolcanoQuestNPC2", new SquareVector(-21, 22),"volcano_npc2"));
        npnSpawns.add(new VolcanoNPC("VolcanoQuestNPC1", new SquareVector(-24, -13),"volcano_npc1"));
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);
        //Add local Event to this world
        this.setWorldEvent(new VolcanoEvent(this));
    }

    @Override
    public String getType() {
        return "Volcano";
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
            notGenerated = false;
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
        entities.add(createLavaPool(-12, -20)); //Left Lava Pool
        entities.add(createLavaPool(5, -10)); //Middle Lava Pool
        entities.add(createLavaPool(19, -7)); //Right Lava Pool
        //For objects that are added randomly & require more specific addition
        //entities, their methodology will follow add()
        addRandoms();
    }

    /**
     * Generates items for volcano region, all positions of item are randomized
     * every time player loads into volcano zone.
     *
     * Items: Health potions, Iron shields etc.
     */
    private void generateItemEntities(){
        final int NUM_POTIONS = 6;
        final int NUM_SHIELDS = 4;
        final int NUM_CHESTS = 3; 

        ArrayList<AbstractDialogBox> items = new ArrayList<>();
        
        for (int i = 0; i < NUM_POTIONS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
            && !tile.hasParent()) {
                HealthPotion potion = new HealthPotion(tile,false,
                        (PlayerPeon) getPlayerEntity(), "volcano");
                entities.add(potion);
                items.add(potion.getDisplay());

            } else {
                i--;
            }

        }

        for (int i = 0; i < NUM_SHIELDS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
                    && !tile.hasParent()) {
                Shield shield = new Shield(tile, false,
                        (PlayerPeon) getPlayerEntity(),"volcano");
                entities.add(shield);
                items.add(shield.getDisplay());
            } else {
                i--;
            }

        }
        
        for (int i = 0; i < NUM_CHESTS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
                    && !tile.hasParent()) {
                Treasure chest = new Treasure(tile, false,
                        (PlayerPeon) getPlayerEntity(),"volcano");
                entities.add(chest);
                items.add(chest.getDisplay());
            } else {
                i--;
            }

        }

        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                items);
        GameManager.get().addManager(dialog);
    }

    /**
     * Adds 20 random Static entities with health (Either a burning tree or boulder)
     * to the map tiles that do not have lava textures (shades of red/yellow).
     */
    public void addRandoms() {
    //Add random boulders on tiles that aren't Lava
        int tileNumber, selector;
        Tile t;
        String tileTexture;
        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTiles().size();
        AbstractWorld world = GameManager.get().getWorld();
        int randIndex;

        for (int i = 0; i < 20; i++) {
            randIndex = random.nextInt(tileCount);

            //Get respective volcano tile (5 <= Lava tiles Index <= 8
            t = world.getTile(randIndex);
            tileTexture = t.getTextureName();
            tileNumber = Integer.parseInt(tileTexture.split("_")[1]);

            selector = random.nextInt(2);
            if (tileNumber < 5 && tileNumber > 1 && selector == 1 && !t.hasParent()) {
                entities.add(new Rock(t, true));
            } else if (t != null && (tileNumber == 3 || tileNumber == 4) &&
                    selector == 0 && !t.hasParent()) {
                entities.add(new VolcanoBurningTree(t, true));
            } else {
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

        //Vertical sides Left
        parts.add(new Part(new SquareVector(0, -6), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -5), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0,  -2), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -1), "fenceN-S", true));
        //Vertical sides Right
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
     * Creates a static lavapool entity which is consequently updated during
     * weather events in the Volcano Zone
     *
     * @param col - The specified column coordinate of the orb.
     * @param row - The specified row coordinate of the orb.
     * @return  A static entity for the Volcano Zone
     */
    public VolcanoLavaPool createLavaPool(float col, float row) {
        List<Part> parts = new ArrayList<Part>();

        //Second layer of edges
        parts.add(new Part(new SquareVector(-3, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(-2, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(-1, 2), "LavaPool", false));
        parts.add(new Part(new SquareVector(0, 3), "LavaPool", false));

        parts.add(new Part(new SquareVector(1, 3), "LavaPool", false));
        parts.add(new Part(new SquareVector(2, 2), "LavaPool", false));
        parts.add(new Part(new SquareVector(3, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(4, 0), "LavaPool", false));

        parts.add(new Part(new SquareVector(4, -1), "LavaPool", false));
        parts.add(new Part(new SquareVector(3, -2), "LavaPool", false));
        parts.add(new Part(new SquareVector(2, -3), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, -4), "LavaPool", false));

        parts.add(new Part(new SquareVector(0, -4), "LavaPool", false));
        parts.add(new Part(new SquareVector(-1, -3), "LavaPool", false));
        parts.add(new Part(new SquareVector(-2, -2), "LavaPool", false));
        parts.add(new Part(new SquareVector(-3, -1), "LavaPool", false));

        //First layer of edges going clock wise
        parts.add(new Part(new SquareVector(-2, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(-1, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(0, 2), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, 2), "LavaPool", false));

        parts.add(new Part(new SquareVector(2, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(3, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(3, -1), "LavaPool", false));
        parts.add(new Part(new SquareVector(2, -2), "LavaPool", false));

        parts.add(new Part(new SquareVector(1, -3), "LavaPool", false));
        parts.add(new Part(new SquareVector(0, -3), "LavaPool", false));
        parts.add(new Part(new SquareVector(-1, -2), "LavaPool", false));
        parts.add(new Part(new SquareVector(-2, -1), "LavaPool", false));

        //Inner parts
        parts.add(new Part(new SquareVector(0, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, -1), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(0, -1), "LavaPool", false));

        parts.add(new Part(new SquareVector(-1, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(-1, -1), "LavaPool", false));
        parts.add(new Part(new SquareVector(0, -2), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, -2), "LavaPool", false));

        //Outer parts
        parts.add(new Part(new SquareVector(0, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(1, 1), "LavaPool", false));
        parts.add(new Part(new SquareVector(2, 0), "LavaPool", false));
        parts.add(new Part(new SquareVector(2, -1), "LavaPool", false));

        VolcanoLavaPool lavaPool = new VolcanoLavaPool(col, row, parts);

        //REMOVE THIS ONCE MAP SIZE IS INCREASE TO 50 x 50 IN LATER SPRINTS,
        // AND SETUP LAVA POOLS ACCORDINGLY
        for (SquareVector coord : lavaPool.getChildrenPositions()) {
            getTile(coord).setTexture("Volcano_1");
        }

        System.out.println(lavaPool.getChildrenPositions().toString());
        return lavaPool;
    }
}
