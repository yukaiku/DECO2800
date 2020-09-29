package deco2800.thomas.worlds.swamp;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.IronArmour;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.SwampNPC;
import deco2800.thomas.entities.enemies.*;
import deco2800.thomas.entities.environment.swamp.SwampDeadTree;
import deco2800.thomas.entities.environment.swamp.SwampFallenTree;
import deco2800.thomas.entities.environment.swamp.SwampPond;
import deco2800.thomas.entities.environment.swamp.SwampTreeLog;
import deco2800.thomas.entities.environment.swamp.SwampTreeStub;
import deco2800.thomas.entities.items.HealthPotion;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Treasure;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.entities.enemies.dragons.SwampDragon;
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

@SuppressWarnings("unused")
public class SwampWorld extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/swamp/swamp-game-map.json";

    private List<AbstractDialogBox> allSwampDialogues;

    public SwampWorld() {
        this(AbstractWorld.DEFAULT_WIDTH, AbstractWorld.DEFAULT_HEIGHT);
    }

    public SwampWorld(int width, int height) {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.generateTileMap();
        this.generateTileIndices();
        this.generateStaticEntities();
        this.allSwampDialogues = new ArrayList<>();

        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.15f));
        addEntity(this.getPlayerEntity());

        //Creates Items
        this.generateItemEntities();

        // Provide available enemies to the EnemyManager
        Orc swampOrc = new Orc(Variation.SWAMP, 50, 0.09f);
        SwampDragon boss = new SwampDragon(1050, 0.03f, 2);
        EnemyManager enemyManager = new EnemyManager(this, 7, Arrays.asList(swampOrc), boss);
        GameManager.get().addManager(enemyManager);
        enemyManager.spawnBoss(19, -24);

        //Creates swamp NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        SwampNPC swampNpc1 = new SwampNPC("SwampQuestNPC1", new SquareVector(-21, 5),"swamp_npc1");
        SwampNPC swampNpc2 = new SwampNPC("SwampQuestNPC2", new SquareVector(-22, 9),"swamp_npc2");
        npnSpawns.add(swampNpc1);
        npnSpawns.add(swampNpc2);
        this.allSwampDialogues.add(swampNpc1.getBox());
        this.allSwampDialogues.add(swampNpc2.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);

        //Creates dialogue manager
        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allSwampDialogues);
        GameManager.get().addManager(dialog);
    }

    @Override
    public String getType() {
        return "Swamp";
    }

    @Override
    protected void generateTiles() {
    }

    public void generateDeadTree() {
        // South Forest
        entities.add(new SwampDeadTree(this.getTile(-5, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-6, -23), true));
        entities.add(new SwampDeadTree(this.getTile(-4, -22), true));
        entities.add(new SwampDeadTree(this.getTile(-3, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-2, -22), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -22), true));

        // North Forest
        entities.add(new SwampDeadTree(this.getTile(20, 15), true));
        entities.add(new SwampDeadTree(this.getTile(22, 15), true));
        entities.add(new SwampDeadTree(this.getTile(22, 14), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -22), true));

        // Mid Forest
        entities.add(new SwampDeadTree(this.getTile(13, -6), true));
        entities.add(new SwampDeadTree(this.getTile(14, -7), true));
        entities.add(new SwampDeadTree(this.getTile(15, -5), true));
        entities.add(new SwampDeadTree(this.getTile(15, -8), true));
        entities.add(new SwampDeadTree(this.getTile(16, -6), true));
        entities.add(new SwampDeadTree(this.getTile(12, -6), true));
        entities.add(new SwampDeadTree(this.getTile(12, -9), true));
        entities.add(new SwampDeadTree(this.getTile(13, -10), true));
        entities.add(new SwampDeadTree(this.getTile(14, -9), true));

        // Near the Orb
        entities.add(new SwampDeadTree(this.getTile(17, -24), true));
        entities.add(new SwampDeadTree(this.getTile(16, -23), true));
        entities.add(new SwampDeadTree(this.getTile(18, -22), true));
        entities.add(new SwampDeadTree(this.getTile(15, -24), true));
        entities.add(new SwampDeadTree(this.getTile(18, -24), true));
    }

    public void createTreeStub() {
        // South Forest
        entities.add(new SwampTreeStub(this.getTile(-5, -22), true));
        entities.add(new SwampTreeStub(this.getTile(0, -23), true));
        entities.add(new SwampTreeStub(this.getTile(1, -24), true));

        // North Forest
        entities.add(new SwampTreeStub(this.getTile(21, 13), true));
        entities.add(new SwampTreeStub(this.getTile(24, 15), true));
        entities.add(new SwampTreeStub(this.getTile(21, 16), true));

        // Mid Forest
        entities.add(new SwampTreeStub(this.getTile(14, -5), true));
        entities.add(new SwampTreeStub(this.getTile(12, -7), true));
        entities.add(new SwampTreeStub(this.getTile(16, -7), true));
        entities.add(new SwampTreeStub(this.getTile(13, -8), true));
        entities.add(new SwampTreeStub(this.getTile(17, -5), true));
        entities.add(new SwampTreeStub(this.getTile(17, -8), true));
        entities.add(new SwampTreeStub(this.getTile(13, -4), true));
    }

    public void createPond() {
        // Near the Orb (Above)
        entities.add(new SwampPond(this.getTile(20, -19), true));
        entities.add(new SwampPond(this.getTile(21, -19), true));

        // Near the Orb (Below)
        entities.add(new SwampPond(this.getTile(16, -24), true));
        entities.add(new SwampPond(this.getTile(17, -25), true));
        entities.add(new SwampPond(this.getTile(16, -25), true));
        entities.add(new SwampPond(this.getTile(19, -23), true));
        entities.add(new SwampPond(this.getTile(19, -24), true));

        // North Forest
        entities.add(new SwampPond(this.getTile(21, 14), true));
    }

    public void createTreeLog() {
        // Near the Orb
        entities.add(new SwampTreeLog(this.getTile(19, -19), true));
        entities.add(new SwampTreeLog(this.getTile(20, -18), true));

        // North Forest
        entities.add(new SwampTreeLog(this.getTile(20, 13), true));
        entities.add(new SwampTreeLog(this.getTile(19, 14), true));

        // Mid Forest
        entities.add(new SwampTreeLog(this.getTile(12, -4), true));
        entities.add(new SwampTreeLog(this.getTile(11, -5), true));
        entities.add(new SwampTreeLog(this.getTile(11, -6), true));
    }

    public void createFallenTree() {
        // Near the Orb
        entities.add(new SwampFallenTree((this.getTile(22, -19)), true));
        entities.add(new SwampFallenTree((this.getTile(22, -20)), true));

        // South Forest
        entities.add(new SwampFallenTree((this.getTile(-4, -23)), true));
        entities.add(new SwampFallenTree((this.getTile(-2, -23)), true));

        // North Forest
        entities.add(new SwampFallenTree((this.getTile(24, 14)), true));
        entities.add(new SwampFallenTree((this.getTile(23, 13)), true));
    }

    public void generateStaticEntities() {
        this.createPond();
        this.generateDeadTree();
        this.createTreeStub();
        this.createFallenTree();
        this.createTreeLog();
    }

    /**
     * Generates items for swamp region, all positions of item are randomized
     * every time player loads into swamp zone.
     *
     * Items: Health potions, Iron shields etc.
     */
    private void generateItemEntities(){
        final int NUM_POTIONS = 6;
        final int NUM_SHIELDS = 4;
        final int NUM_CHESTS = 3;

        for (int i = 0; i < NUM_POTIONS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));

                HealthPotion potion = new HealthPotion(tile, false,
                        (PlayerPeon) getPlayerEntity(), "swamp");
                entities.add(potion);
                this.allSwampDialogues.add(potion.getDisplay());

        }

        for (int i = 0; i < NUM_SHIELDS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            IronArmour ironArmour = new IronArmour(tile, false,
                    (PlayerPeon) getPlayerEntity(),"swamp");
            entities.add(ironArmour);
            this.allSwampDialogues.add(ironArmour.getDisplay());
        }

        for (int i = 0; i < NUM_CHESTS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            Treasure chest = new Treasure(tile, false,
                    (PlayerPeon) getPlayerEntity(),"swamp");
            entities.add(chest);
            this.allSwampDialogues.add(chest.getDisplay());
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }
}
