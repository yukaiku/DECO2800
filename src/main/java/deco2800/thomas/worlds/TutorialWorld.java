package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.entities.environment.tutorial.*;
import deco2800.thomas.entities.items.*;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.TutorialNPC;
import deco2800.thomas.managers.DialogManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.NonPlayablePeonManager;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.util.SquareVector;

import java.util.ArrayList;
import java.util.List;

public class TutorialWorld extends AbstractWorld{

    DialogManager dialog;
    List<AbstractDialogBox> allDialogBoxes;
    boolean notGenerated = true;
    static final int TUTORIAL_WORLD_WIDTH = 10; // Height and width vars for the map size; constrains tile gen
    static final int TUTORIAL_WORLD_HEIGHT = 6; // Note the map will double these numbers (bounds are +/- these limits)
    private static final int PORTAL_COL = 0;
    private static final int PORTAL_ROW = -TUTORIAL_WORLD_HEIGHT;


    public TutorialWorld() {
        super(TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT);
    }

    @Override
    protected void generateTiles() {
        for (int col = -TUTORIAL_WORLD_WIDTH; col < TUTORIAL_WORLD_WIDTH; col++) {
            String type = "stone-3";
            tiles.add(new Tile(type, col, TUTORIAL_WORLD_HEIGHT - 1));
            tiles.add(new Tile(type, col, -TUTORIAL_WORLD_HEIGHT));
        }

        for (int row = -TUTORIAL_WORLD_HEIGHT + 1; row < TUTORIAL_WORLD_HEIGHT - 1; row++) {
            String type = "stone-3";
            tiles.add(new Tile(type, TUTORIAL_WORLD_WIDTH - 1, row));
            tiles.add(new Tile(type, -TUTORIAL_WORLD_WIDTH, row));
        }

        for (int col = -TUTORIAL_WORLD_WIDTH+1; col < TUTORIAL_WORLD_WIDTH-1; col++) {
            String type = "stone-2";
            tiles.add(new Tile(type, col, TUTORIAL_WORLD_HEIGHT - 2));
            tiles.add(new Tile(type, col, -TUTORIAL_WORLD_HEIGHT + 1));
        }

        for (int row = -TUTORIAL_WORLD_HEIGHT + 2; row < TUTORIAL_WORLD_HEIGHT - 2; row++) {
            String type = "stone-2";
            tiles.add(new Tile(type, TUTORIAL_WORLD_WIDTH - 2, row));
            tiles.add(new Tile(type, -TUTORIAL_WORLD_WIDTH + 1, row));
        }

        for (int col = -TUTORIAL_WORLD_WIDTH+2; col < TUTORIAL_WORLD_WIDTH-2; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT+2; row < TUTORIAL_WORLD_HEIGHT-2; row++) {
                String type = "stone-1";
                tiles.add(new Tile(type, col, row));
            }
        }

        PlayerPeon.setBuffDamageTotal(0);
        PlayerPeon.setCooldownBuff(false);
        PlayerPeon.setWallet(0);
        PlayerPeon.setBuffArmourTotal(0);
        PlayerPeon player = new PlayerPeon(-2f, -2f, 0.1f, 100);
            addEntity(player);
        this.setPlayerEntity(player);
        addEntity(this.getPlayerEntity());
        player.setCurrentHealthValue(50);

        // Spawn dummy
        EnemyManager enemyManager = new EnemyManager(this);
        enemyManager.spawnSpecialEnemy("dummy", 5, 0);
        GameManager.get().addManager(enemyManager);

        // Add NPC
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        TutorialNPC npc = new TutorialNPC("Master", new SquareVector(0, 2),"tutorial_npc");
        npnSpawns.add(npc);
        this.allDialogBoxes = new ArrayList<>();
        this.allDialogBoxes.add(npc.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, player, npnSpawns);
        GameManager.get().addManager(npcManager);

        //Dialog manager
        dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allDialogBoxes);
        GameManager.get().addManager(dialog);

        generateItemEntities();
        generateEntities();
    }

    /**
     * Generates items for tutorial, all positions of item are randomized
     * every time player loads into the tutorial.
     *
     * Items: Health potions, Iron shields etc.
     */
    private void generateItemEntities(){
        final String ITEM_BOX_STYLE = "tutorial";

        Tile healthPotionTile = getTile(Item.randomItemPositionGenerator(TUTORIAL_WORLD_WIDTH),
                Item.randomItemPositionGenerator(TUTORIAL_WORLD_HEIGHT));
        HealthPotion potion = new HealthPotion(healthPotionTile, false,
                (PlayerPeon) getPlayerEntity(), ITEM_BOX_STYLE);
        entities.add(potion);
        this.allDialogBoxes.add(potion.getDisplay());

        Tile shieldTile = getTile(Item.randomItemPositionGenerator(TUTORIAL_WORLD_WIDTH),
                Item.randomItemPositionGenerator(TUTORIAL_WORLD_HEIGHT));
        IronArmour ironArmour = new IronArmour(shieldTile, false,
                (PlayerPeon) getPlayerEntity(),ITEM_BOX_STYLE, 100);
        entities.add(ironArmour);
        this.allDialogBoxes.add(ironArmour.getDisplay());

        Tile treasureTile = getTile(Item.randomItemPositionGenerator(TUTORIAL_WORLD_WIDTH),
                Item.randomItemPositionGenerator(TUTORIAL_WORLD_HEIGHT));
        Treasure chest = new Treasure(treasureTile, false,
                (PlayerPeon) getPlayerEntity(),ITEM_BOX_STYLE);
        entities.add(chest);
        this.allDialogBoxes.add(chest.getDisplay());

        Tile attackAmuletTile = getTile(Item.randomItemPositionGenerator(TUTORIAL_WORLD_WIDTH),
                Item.randomItemPositionGenerator(TUTORIAL_WORLD_HEIGHT));
        Amulet attackAmulet = new Amulet(attackAmuletTile, false,
                (PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,10);
        entities.add(attackAmulet);
        this.allDialogBoxes.add(attackAmulet.getDisplay());

        Tile coolDownRingTile = getTile(Item.randomItemPositionGenerator(TUTORIAL_WORLD_WIDTH),
                Item.randomItemPositionGenerator(TUTORIAL_WORLD_HEIGHT));
        CooldownRing cooldownRing = new CooldownRing(coolDownRingTile, false,
                (PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,0.5f);
        entities.add(cooldownRing);
        this.allDialogBoxes.add(cooldownRing.getDisplay());
    }


    public void generateEntities() {
        // Add stashes
        for (int i = -6; i < 6 + 1; i = i + 3) {
            Tile t = getTile(i, TUTORIAL_WORLD_HEIGHT - 1);
            if (t != null) {
                entities.add(new Stash(t, true));
            }
        }
        // Add targets
        for (int i = -6; i < 6 + 1; i = i + 2) {
            if (i == 0 || i == -2 || i == 2) {
                continue;
            }
            Tile t = getTile(i, -TUTORIAL_WORLD_HEIGHT);
            if (t != null) {
                entities.add(new Target(t, true));
            }
        }

        Tile t;
        // Add barrels
        t = getTile(TUTORIAL_WORLD_WIDTH - 1, TUTORIAL_WORLD_HEIGHT - 2);
        entities.add(new Barrel(t, true));

        t = getTile(TUTORIAL_WORLD_WIDTH - 1, -TUTORIAL_WORLD_HEIGHT + 1);
        entities.add(new Barrel(t, true));

        t = getTile(-TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT - 2);
        entities.add(new Barrel(t, true));

        t = getTile(-TUTORIAL_WORLD_WIDTH, -TUTORIAL_WORLD_HEIGHT + 1);
        entities.add(new Barrel(t, true));

        // Add chest
        t = getTile(-TUTORIAL_WORLD_WIDTH, 0);
        entities.add(new Chest(t, true));

        t = getTile(TUTORIAL_WORLD_WIDTH -1, 0);
        entities.add(new Chest(t, true));

        // Add portal
        t = getTile(PORTAL_COL, PORTAL_ROW);
        entities.add(new Portal(t, false));

        // Add message on how to leave tutorial (temporary)
        t = getTile(PORTAL_COL, PORTAL_ROW + 2);
        entities.add(new Notify(t, false));
    }


    public List<AbstractDialogBox> returnAllDialogues() {
        return this.allDialogBoxes;
    }
    
    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            PlayerPeon.credit(1000);
            notGenerated = false;
        }

        AgentEntity player = this.getPlayerEntity();
        // Check if the player is in the portal
        if (Math.abs(player.getCol() - PORTAL_COL) < 0.1 &
                Math.abs(player.getRow() - PORTAL_ROW) < 0.1) {
            // Remove the current map's enemy manager
            GameManager.get().removeManager(GameManager.get().getManager(EnemyManager.class));

            // Set new world
            GameManager gameManager = GameManager.get();
            gameManager.setWorld(GameScreen.gameType.NEW_GAME.method());


            GameManager.get().setNextWorld();
            // Change wallet to 0 on world change.
            PlayerPeon.credit(0);
        }
    }
}
