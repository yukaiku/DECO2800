package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.entities.environment.TripWire;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.VolcanoDungeonNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.volcano.VolcanoWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implemented subclass of Abstract world for bonus dungeon within the Volcano
 * Zone within Polyhedron.This class generates & manages all objects related to
 * the Volcano Dungeon environment in the game including * relevant StaticEntities,
 * Tiles, traps, rewards & Textures.
 *
 *  * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoDungeon extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/dungeons/VolcanoDungeonMaze.json";

    private ArrayList<AbstractDialogBox> volcanoDungeonDialogue;
    private boolean notGenerated = true;

    private long timeLastTick;
    /**
     * Default Constructor for volcano world.
     */
    public VolcanoDungeon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Primary constructor for volcano world.
     * @param width width of the world in terms of tiles
     * @param height height of the world in terms of tiles
     */
    public VolcanoDungeon(int width, int height){
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);

        this.setPlayerEntity(new PlayerPeon(-0f, -0f, 0.15f));
        addEntity(this.getPlayerEntity());

        GameManager.get().addManager(new EnemyManager(this));
        this.volcanoDungeonDialogue = new ArrayList<>();
        setupPuzzle();

        timeLastTick = System.currentTimeMillis();
    }

    /**
     * Sets up the puzzle of of lava Maze.
     */
    public void setupPuzzle(){
        setupNpc();
        setupIncorrectTreasure1();
        setupIncorrectTreasure2();
        setupReward();

        Tile exitTile = this.getTile(-24f, 23f);
        addEntity(new ExitPortal(exitTile, false, "portal", "ExitPortal"));
    }

    /**
     * Sets up the NPC guide that spawns at the start of the lava maze.
     */
    public void setupNpc() {
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();

        //Sets up NPC
        VolcanoDungeonNPC lavaMazeNpc = new VolcanoDungeonNPC("npc_lava_maze", new SquareVector(-2f, 0f),"volcano_npc1");
        npnSpawns.add(lavaMazeNpc);
        this.volcanoDungeonDialogue.add(lavaMazeNpc.getBox());

        //Adds Npc manager
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);

        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.volcanoDungeonDialogue);
        GameManager.get().addManager(dialog);


    }

    /**
     * Sets up the first teleport tripwire that executes upon the player
     * stepping on the bottom left corner portal (an incorrect portal).
     */
    public void setupIncorrectTreasure1() {
        //Add
        Tile teleportTile = getTile(-23f, -23f);
        TripWire teleportTrap = new TripWire(teleportTile, false, "VolcanoPortal");

        teleportTile.setParent(teleportTrap);
        this.addEntity(teleportTrap);

        teleportTile.setTeleportTile(true);
        teleportTile.setTeleportCol(0);
        teleportTile.setTeleportRow(0);
    }


    /**
     * Sets up the second tripwire that spawns enemies upon the player
     * stepping on the bottom right corner portal (an incorrect portal).
     */
    public void setupIncorrectTreasure2() {


        Tile trapTile = getTile(22f, -23f);
        TripWire enemyTrap = new TripWire(trapTile, false, "VolcanoPortal");

        trapTile.setParent(enemyTrap);
        this.addEntity(enemyTrap);

        trapTile.setTrapTile(true);

    }

    /**
     * Sets up the only reward tripwire that spawns a treasurebox & grants a skill
     * upon the player stepping on the top right corner portal
     * (an incorrect portal).
     */
    public void setupReward() {
        Tile rewardTile = getTile(22f, 23f);
        TripWire powerUpReward = new TripWire(rewardTile, false, "VolcanoPortal");

                rewardTile.setParent(powerUpReward);
        this.addEntity(powerUpReward);

        rewardTile.setRewardTile(true);
    }

    /**
     * Spawns enemies around the player after triggering the South Eastern trap.
     * @param tile of the trap tile.
     */
    @Override
    public void activateTrapTile(Tile tile) {
        if (tile.getTrapActivated()) {
            EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
            enemyManager.spawnSpecialEnemy("volcanoGoblin", 24f, -25f);
            enemyManager.spawnSpecialEnemy("volcanoGoblin", 24f, -22f);
            enemyManager.spawnSpecialEnemy("volcanoGoblin", 24f, -25f);
            enemyManager.spawnSpecialEnemy("volcanoGoblin", 24f, -22f);
        }

    }

    /**
     * Spawns a loot box & grants the player a Fireball skill.
     * @param tile of the trap tile.
     */
    @Override
    public void activateRewardTile(Tile tile) {
        if (tile.getRewardActivated()) {
            Tile rewardTile = getTile(24f, 24f);
            //Treasure rewardBox = new Treasure(rewardTile, true, (PlayerPeon) getPlayerEntity(), "volcano" );
            //tile.setParent(rewardBox);
            //entities.add(rewardBox);
            //volcanoDungeonDialogue.add(rewardBox.getDisplay());
            GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.FIREBALL);

        }
    }

    @Override
    public List<AbstractDialogBox> returnAllDialogues() {
        return volcanoDungeonDialogue;
    }

    /**
     * Adds a dialog box to this dungeon
     * @param box
     */
    public void addDialogue(AbstractDialogBox box){ this.volcanoDungeonDialogue.add(box);}

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
            notGenerated = false;
        }
        super.onTick(i);
    }

    @Override
    public String getType() {
        return "VolcanoDungeon";
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
}
