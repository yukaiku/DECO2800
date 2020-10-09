package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.entities.environment.TripWire;
import deco2800.thomas.entities.items.Treasure;
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

public class VolcanoDungeon extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/dungeons/VolcanoDungeonMaze.json";

    private ArrayList<AbstractDialogBox> VolcanoDungeonDialogue;
    private boolean notGenerated = true;

    /**
     * Default Constructor for volcano world.
     */
    public VolcanoDungeon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public VolcanoDungeon(int width, int height){
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);

        this.setPlayerEntity(new PlayerPeon(-0f, -0f, 0.15f));
        addEntity(this.getPlayerEntity());

        this.setWorldZoomable(false);

        GameManager.get().addManager(new EnemyManager(this));
        this.VolcanoDungeonDialogue = new ArrayList<>();
        setupPuzzle();

    }

    public void setupPuzzle(){
        setupNpc();
        setupIncorrectTreasure1();
        setupIncorrectTreasure2();
        setupReward();

        Tile exitTile = this.getTile(-24f, 23f);
        addEntity(new ExitPortal(exitTile, false, "portal", "ExitPortal"));
    }

    public void setupNpc() {
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();

        //Sets up NPC
        VolcanoDungeonNPC lavaMazeNpc = new VolcanoDungeonNPC("npc_lava_maze", new SquareVector(-2f, 0f),"volcano_npc1");
        npnSpawns.add(lavaMazeNpc);
        this.VolcanoDungeonDialogue.add(lavaMazeNpc.getBox());

        //Adds Npc manager
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);

        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.VolcanoDungeonDialogue);
        GameManager.get().addManager(dialog);


    }

    public void setupIncorrectTreasure1() {
        //Add
        Tile teleportTile = getTile(-23f, -23f);
        TripWire teleportTrap = new TripWire(teleportTile, false, "portal");

        teleportTile.setParent(teleportTrap);
        this.addEntity(teleportTrap);

        teleportTile.setTeleportTile(true);
        teleportTile.setTeleportCol(0);
        teleportTile.setTeleportRow(0);
    }

    public void setupIncorrectTreasure2() {


        Tile trapTile = getTile(22f, -23f);
        TripWire enemyTrap = new TripWire(trapTile, false, "portal");

        trapTile.setParent(enemyTrap);
        this.addEntity(enemyTrap);

        trapTile.setTrapTile(true);

    }

    public void setupReward() {
        Tile rewardTile = getTile(22f, 23f);
        TripWire powerUpReward = new TripWire(rewardTile, false, "portal");

        rewardTile.setParent(powerUpReward);
        this.addEntity(powerUpReward);

        rewardTile.setRewardTile(true);
    }

    /**
     *
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
     *
     * @param tile of the trap tile.
     */
    @Override
    public void activateRewardTile(Tile tile) {
        if (tile.getRewardActivated()) {
            Tile rewardTile = getTile(24f, 24f);
            Treasure rewardBox = new Treasure(rewardTile, true, (PlayerPeon) getPlayerEntity(), "volcano" );
            tile.setParent(rewardBox);
            entities.add(rewardBox);
            VolcanoDungeonDialogue.add(rewardBox.getDisplay());

        }
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
            notGenerated = false;
        }



        super.onTick(i);
    }
}
