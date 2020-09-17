package deco2800.thomas.worlds.desert;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.NPC.MerchantNPC;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.entities.NPC.TutorialNPC;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.environment.desert.*;
import deco2800.thomas.entities.items.HealthPotion;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Shield;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The Desert World of the game.
 * Contains obstacles such as quicksand and cactus plants that force the player
 * to be careful about their movement.
 *
 * @author Zachary Oar (Gitlab: @zachary_oar) (Slack: Zac Oar)
 */
public class DesertWorld extends AbstractWorld {

    // a bool to signal that entities still need to be generated
    private boolean notGenerated = true;

    // the save file location for the desert zone map
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_map.json";

    /**
     * Constructor that creates a world with default width and height.
     */
    public DesertWorld() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor that creates a world with given width and height.
     *
     * @param width  width of the world; horizontal coordinates of the world will be within `[-width, width]`
     * @param height eight of the world; vertical coordinates of the world will be within `[-height, height]`
     */
    public DesertWorld(int width, int height) {
        super(width, height);
        generateTiles();

        //Creates Desert NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        List<Item> swampMerchantShop = new ArrayList<>();
        npnSpawns.add(new TutorialNPC("DesertQuestNPC", new SquareVector(-23, 17),"desert_npc1"));
        npnSpawns.add(new MerchantNPC("DesertMerchantNPC", new SquareVector(-23, 20),"desert_npc2",swampMerchantShop));
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
        GameManager.get().addManager(npcManager);
        generateItemEntities();
    }

    /**
     * Returns the type of this world.
     *
     * @return The type of this world.
     */
    @Override
    public String getType() {
        return "Desert";
    }

    /**
     * Generates the tiles for the world by opening a save file.
     */
    @Override
    protected void generateTiles() {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(6f, 5f, 0.15f));
        addEntity(this.getPlayerEntity());

        Orc desertOrc = new Orc(1, 0.09f, 50, "orc_desert");
        Dragon boss = new Dragon(3, 0.03f, 1000, "dragon_desert", 4);

        EnemyManager enemyManager = new EnemyManager(this, 5, Arrays.asList(desertOrc), boss);
        GameManager.get().addManager(enemyManager);
        enemyManager.spawnBoss(21, 6);
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
                case "desert_5":
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

                // add the oasis plants
                case "oasis_1":
                case "oasis_2":
                case "oasis_3":
                    // one third of grass area should be trees
                    randIndex = rand.nextInt(6);
                    if (randIndex == 0) {
                        entities.add(new OasisTree(tile, "oasisTree1"));
                    } else if (randIndex == 1) {
                        entities.add(new OasisTree(tile, "oasisTree2"));
                    }
                    break;

                // make the oasis water obstructed
                case "oasis_4":
                case "oasis_5":
                    tile.setObstructed(true);
                    break;
            }
        }
    }

    /**
     * Generates items for desert region, all positions of item are randomized
     * every time player loads into desert zone.
     *
     * Items: Health potions, Iron shields etc.
     */
    private void generateItemEntities(){
        final int NUM_POTIONS = 6;
        final int NUM_SHIELDS = 4;
        ArrayList<AbstractDialogBox> items = new ArrayList<>();
        
        for (int i = 0; i < NUM_POTIONS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            HealthPotion potion = new HealthPotion(tile,false,
                    (PlayerPeon) getPlayerEntity(),"desert");
            entities.add(potion);
            items.add(potion.getDisplay());
        }

        for (int i = 0; i < NUM_SHIELDS; i++) {
            Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
                    Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
            Shield shield = new Shield(tile, false,
                    (PlayerPeon) getPlayerEntity(),"desert");
            entities.add(shield);
            items.add(shield.getDisplay());
        }
        
        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                items);
        GameManager.get().addManager(dialog);
    }

    /**
     * Handles what happens after each tick of the game.
     */
    @Override
    public void onTick(long i) {
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        // generates the static entities if they have not been created
        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }

        super.onTick(i);
    }
}
