package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.SwampDungeonNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.TestWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SwampDungeon extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/swamp_dungeon/swamp_dungeon_map.json";

    private SquareVector topTrap;
    private boolean topTrapActivated = false;

    private SquareVector midTrap;
    private boolean midTrapActivated = false;

    private List<AbstractDialogBox> allSwampDungeonDialogues;

    public SwampDungeon() {
        this(12, 7);
    }

    public SwampDungeon(int width, int height) {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.generateTiles();
        this.generateTileMap();
        this.generateTileIndices();
        this.allSwampDungeonDialogues = new ArrayList<>();

        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(0f, 0f, 0.15f));
        addEntity(this.getPlayerEntity());

        // Provide enemies
        EnemyManager enemyManager = new EnemyManager(this);
        GameManager.get().addManager(enemyManager);

        // Creates swamp NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        SwampDungeonNPC npc1 = new SwampDungeonNPC("npc_swamp_dungeon_blue", new SquareVector(-9, 5),"npc_swamp_dungeon_blue");
        SwampDungeonNPC npc2 = new SwampDungeonNPC("npc_swamp_dungeon_green", new SquareVector(-7, 5),"npc_swamp_dungeon_green");
        SwampDungeonNPC npc3 = new SwampDungeonNPC("npc_swamp_dungeon_orange", new SquareVector(-5, 5),"npc_swamp_dungeon_orange");
        SwampDungeonNPC npc4 = new SwampDungeonNPC("npc_swamp_dungeon_red", new SquareVector(-3, 5),"npc_swamp_dungeon_red");
        SwampDungeonNPC npc5 = new SwampDungeonNPC("npc_swamp_dungeon_white", new SquareVector(-1, 5),"npc_swamp_dungeon_white");
        SwampDungeonNPC npc6 = new SwampDungeonNPC("npc_swamp_dungeon_yellow", new SquareVector(1, 5),"npc_swamp_dungeon_yellow");
        npnSpawns.add(npc1);
        npnSpawns.add(npc2);
        npnSpawns.add(npc3);
        npnSpawns.add(npc4);
        npnSpawns.add(npc5);
        npnSpawns.add(npc6);
        this.allSwampDungeonDialogues.add(npc1.getBox());
        this.allSwampDungeonDialogues.add(npc2.getBox());
        this.allSwampDungeonDialogues.add(npc3.getBox());
        this.allSwampDungeonDialogues.add(npc4.getBox());
        this.allSwampDungeonDialogues.add(npc5.getBox());
        this.allSwampDungeonDialogues.add(npc6.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);

        // Creates dialogue manager
        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allSwampDungeonDialogues);
        GameManager.get().addManager(dialog);

        topTrap = new SquareVector(7, 1);
        midTrap = new SquareVector(7, -1);
    }

    @Override
    protected void generateTiles() {
    }

    private void spawnEnemies() {
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.spawnSpecialEnemy("swampGoblin", 1, 2);
        enemyManager.spawnSpecialEnemy("swampGoblin", -1, 1);
        enemyManager.spawnSpecialEnemy("swampGoblin", -2, -1);
        enemyManager.spawnSpecialEnemy("swampGoblin", -1, -3);
        enemyManager.spawnSpecialEnemy("swampGoblin", 1, -5);
        enemyManager.spawnSpecialEnemy("swampGoblin", 8, 2);
        enemyManager.spawnSpecialEnemy("swampGoblin", 10, 1);
        enemyManager.spawnSpecialEnemy("swampGoblin", 11, -1);
        enemyManager.spawnSpecialEnemy("swampGoblin", 10, -3);
        enemyManager.spawnSpecialEnemy("swampGoblin", 8, -5);

        for (int i = -6; i < 4; i++) {
            enemyManager.spawnSpecialEnemy("swampOrc", -3, i);
        }
    }

    private void activateTrap() {
        if (this.getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(topTrap) && !topTrapActivated) {
            this.getTile(topTrap).setTexture("dungeon-light-black");
            spawnEnemies();
            topTrapActivated = true;
        }
        if (this.getPlayerEntity().getPosition().isCloseEnoughToBeTheSame(midTrap) && !midTrapActivated) {
            this.getTile(midTrap).setTexture("dungeon-light-black");
            spawnEnemies();
            midTrapActivated = true;
        }
    }

    @Override
    public void onTick(long i) {
        activateTrap();
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }
}
