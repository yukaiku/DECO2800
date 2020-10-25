package deco2800.thomas.worlds.volcano;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.volcano.*;
import deco2800.thomas.entities.items.*;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.VolcanoNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    private static final int ORB_COLUMN = 21;
    private static final int ORB_ROW = 20;

    private long timeLastTick;

    private boolean notGenerated = true;

    private List<AbstractDialogBox> allVolcanoDialogues;

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

        this.allVolcanoDialogues = new ArrayList<>();

        // Provide enemies
        EnemyManager enemyManager = new EnemyManager(this, "volcanoDragon", 7, "volcanoOrc");
        GameManager.get().addManager(enemyManager);
        enemyManager.spawnBoss(16, 20);

        //Create Volcano NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        VolcanoNPC volcanoNpc1 = new VolcanoNPC("VolcanoQuestNPC1", new SquareVector(-20, -13),"volcano_npc1");
        VolcanoNPC volcanoNpc2 = new VolcanoNPC("VolcanoQuestNPC2", new SquareVector(-21, 22),"volcano_npc2");
        npnSpawns.add(volcanoNpc2);
        npnSpawns.add(volcanoNpc1);
        this.allVolcanoDialogues.add(volcanoNpc2.getBox());
        this.allVolcanoDialogues.add(volcanoNpc1.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);

        //Add items to game
        generateItemEntities();

        //Creates dialogue manager
        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allVolcanoDialogues);
        GameManager.get().addManager(dialog);

        //Add local Event to this world
        this.setWorldEvent(new VolcanoEvent(this));

        //Updates difficulty manager
        DifficultyManager difficultyManager = GameManager.getManagerFromInstance(DifficultyManager.class);
        difficultyManager.setPlayerEntity((PlayerPeon) this.getPlayerEntity());
        difficultyManager.setDifficultyLevel(getType());

        // Start ambience
        GameManager.getManagerFromInstance(SoundManager.class).playAmbience("volcanoAmbience");

        timeLastTick = System.currentTimeMillis();
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

        checkLavaTileUpdates();

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
        entities.add(createDungeonPortal(-24, -13));
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
        final int NUM_IRON_ARMOUR = 1;
        final int NUM_CHESTS = 2;
        final String ITEM_BOX_STYLE = "volcano";


        for (int i = 0; i < NUM_POTIONS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
            && !tile.hasParent()) {
                HealthPotion potion = new HealthPotion(tile,false,
                        (PlayerPeon) getPlayerEntity(), ITEM_BOX_STYLE);
                entities.add(potion);
                this.allVolcanoDialogues.add(potion.getDisplay());
            } else {
                i--;
            }
        }
        for (int i =0; i < NUM_IRON_ARMOUR; i++){
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
                    && !tile.hasParent()) {
                IronArmour ironArmour = new IronArmour(tile, false,
                        (PlayerPeon) getPlayerEntity(),ITEM_BOX_STYLE,200);
                entities.add(ironArmour);
                this.allVolcanoDialogues.add(ironArmour.getDisplay());
            } else {
                i--;
            }
        }
        for (int i = 0; i <NUM_CHESTS; i++){
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            if (Integer.parseInt(tile.getTextureName().split("_")[1]) < 5
                    && !tile.hasParent()) {
                Treasure chest = new Treasure(tile, false,
                        (PlayerPeon) getPlayerEntity(),ITEM_BOX_STYLE);
                entities.add(chest);
                this.allVolcanoDialogues.add(chest.getDisplay());
            } else {
                i--;
            }
        }

        Tile cooldownring = getTile(20,-7);
        CooldownRing cdring = new CooldownRing(cooldownring, false,
                (PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,0.5f);
        entities.add(cdring);
        this.allVolcanoDialogues.add(cdring.getDisplay());

        Tile attackAmuletTile = getTile(2,14);
        Amulet attackAmulet = new Amulet(attackAmuletTile, false,
                (PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,10);
        entities.add(attackAmulet);
        this.allVolcanoDialogues.add(attackAmulet.getDisplay());

    }

    /**
     * Adds 20 random Static entities with health (Either a burning tree or boulder)
     * to the map tiles that do not have lava textures (shades of red/yellow).
     */
    public void addRandoms() {
    //Add random boulders on tiles that aren't Lava
        int tileNumber;
        int selector;
        Tile t;
        String tileTexture;
        Random random = new Random();
        SquareVector playerPos = getPlayerEntity().getPosition();
        int tileCount = GameManager.get().getWorld().getTiles().size();
        AbstractWorld world = GameManager.get().getWorld();
        int randIndex;

        for (int i = 0; i < 20; i++) {
            //Get respective volcano tile (5 <= Lava tiles Index <= 8
            do {
                randIndex = random.nextInt(tileCount);
                t = world.getTile(randIndex);
            } while (t.getCoordinates().isCloseEnoughToBeTheSame(playerPos));

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
        List<Part> parts = new ArrayList<>();
        parts.add(new Part(new SquareVector(0, 0), "DragonSkull", true));
        return new VolcanoDragonSkull(col, row, parts);
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
        List<Part> parts = new ArrayList<>();

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

        return new VolcanoRuins(col, row, parts);
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
        List<Part> parts = new ArrayList<>();
        //Top left
        parts.add(new Part(new SquareVector(1, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(2, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(3,  0), "fenceE-W", true));
        //Top right
        parts.add(new Part(new SquareVector(9, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(10, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(11,  0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(12, 0), "fenceE-W", true));
        //Corner

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

        return new VolcanoGraveYard(col, row, parts);
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
        List<Part> parts = new ArrayList<>();

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
        return lavaPool;
    }

    /**
     * Creates a static portal entity which is teleports the player upon collision
     *
     * @param col - The specified column coordinate of the orb.
     * @param row - The specified row coordinate of the orb.
     * @return  A static entity for the Volcano Zone
     */
    public VolcanoPortal createDungeonPortal(float col, float row){
        Tile portalTile = getTile(col, row);
        return new VolcanoPortal(portalTile, false, "VolcanoPortal", "VolcanoDungeonPortal" );
    }
    
    /**
     * Adds dialogue to the volcano zone.
     * @param box The box ADT containing the dialogue
     */
    public void addDialogue(AbstractDialogBox box){
        this.allVolcanoDialogues.add(box);
    }

    /**
     * Checks for whether lava-tile updates should occur
     */
    public void checkLavaTileUpdates() {
        // 1 second (1 bn nanoseconds) between each tick
        long timeBetweenTicks = 1000;
        long newTime = System.currentTimeMillis();

        // if it has been 1 second: decrement ticks, set a new time and return true
        if (newTime - timeLastTick >= timeBetweenTicks) {
            timeLastTick = newTime;
            updateLavaTiles();
        }
    }

    /**
     * Creates tile-Updates that act like a active lava-flow animation
     */
    public void updateLavaTiles() {
        for (Tile tile : getTiles()) {
            Random random = new Random();
            if (tile.getType().matches("BurnTile")) {
                tile.setTexture("Volcano_" + (random.nextInt(4) + 5));
            }
        }
    }
    
    public List<AbstractDialogBox> returnAllDialogues(){
        return this.allVolcanoDialogues;
    }
}
