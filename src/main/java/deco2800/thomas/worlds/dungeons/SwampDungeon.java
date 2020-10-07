package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.SwampNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwampDungeon extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/swamp_dungeon/swamp_dungeon_map.json";

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

        //Creates swamp NPCs
//        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
//        SwampNPC swampNpc1 = new SwampNPC("SwampQuestNPC1", new SquareVector(-21, 5),"swamp_npc1");
//        SwampNPC swampNpc2 = new SwampNPC("SwampQuestNPC2", new SquareVector(-22, 9),"swamp_npc2");
//        npnSpawns.add(swampNpc1);
//        npnSpawns.add(swampNpc2);
//        this.allSwampDungeonDialogues.add(swampNpc1.getBox());
//        this.allSwampDungeonDialogues.add(swampNpc2.getBox());
//        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
//        GameManager.get().addManager(npcManager);

        //Creates dialogue manager
//        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
//                this.allSwampDungeonDialogues);
//        GameManager.get().addManager(dialog);
    }

    @Override
    protected void generateTiles() {
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }
}
