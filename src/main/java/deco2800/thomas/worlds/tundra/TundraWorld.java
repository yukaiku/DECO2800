package deco2800.thomas.worlds.tundra;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.NPC.MerchantNPC;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.entities.NPC.TutorialNPC;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.environment.tundra.TundraCampfire;
import deco2800.thomas.entities.environment.tundra.TundraRock;
import deco2800.thomas.entities.environment.tundra.TundraTreeLog;
import deco2800.thomas.entities.items.HealthPotion;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Shield;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.lwjgl.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.entities.AbstractEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TundraWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(TundraWorld.class);
	public static final String MAP_FILE = "resources/environment/tundra/tundra-map.json";

	public TundraWorld() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public TundraWorld(int width, int height) {
		DatabaseManager.loadWorld(this, MAP_FILE);
		generateStaticEntities();
		this.setPlayerEntity(new PlayerPeon(-3f, -24f, 0.15f));
		addEntity(this.getPlayerEntity());
		generateItemEntities();

		// Provide available enemies to the EnemyManager
		Orc tundraOrc = new Orc(1, 0.05f, 100, "orc_tundra");
		Dragon boss = new Dragon(3, 0.03f, 1000, "dragon_tundra", 3);

		EnemyManager enemyManager = new EnemyManager(this, 5, Arrays.asList(tundraOrc), boss);
		GameManager.get().addManager(enemyManager);
		enemyManager.spawnBoss(0, 0);

		//Creates Tundra NPCs
		List<NonPlayablePeon> npnSpawns = new ArrayList<>();
		List<Item> swampMerchantShop = new ArrayList<>();
		npnSpawns.add(new TutorialNPC("TundraQuestNPC", new SquareVector(-8, -24),"tundra_npc1"));
		npnSpawns.add(new MerchantNPC("TundraMerchantNPC", new SquareVector(-22, 9),"merchant_npc2",swampMerchantShop));
		NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
		GameManager.get().addManager(npcManager);
	}

	private void generateStaticEntities() {
		final int NUM_CAMPFIRES = 20;
		final int NUM_TREE_LOGS = 60;
		final int NUM_ROCKS = 60;

		int numTiles = getTiles().size();
		List<Integer> tileIDs = new ArrayList<>();

		for (int i = 0; i < numTiles; i++) {
			tileIDs.add(i);
		}

		Collections.shuffle(tileIDs);

		int counter = 0;

		for (int i = 0; i < NUM_CAMPFIRES + NUM_TREE_LOGS + NUM_ROCKS; i++) {
			if (counter > numTiles) {
				throw new RuntimeException("No tile left to create static entities in Tundra world");
			}

			Tile tile = null;

			while (tile == null || tile.hasParent()) {
				int tileID = tileIDs.get(counter++);
				tile = getTile(tileID);
			}

			if (i < NUM_CAMPFIRES) {
				addCampfire(tile.getCol(), tile.getRow());
			} else if (i < NUM_CAMPFIRES + NUM_TREE_LOGS) {
				addTreeLog(tile.getCol(), tile.getRow());
			} else {
				addRock(tile.getCol(), tile.getRow());
			}
		}

		for (AbstractEntity entity: entities) {
			if (entity.getTexture().equals("tundra-campfire")) {
				SquareVector vector = entity.getPosition();
				Tile tile = getTile(vector);
				for (Tile neighbouringTile : tile.getNeighbours().values()) {
					neighbouringTile.setType("TundraFireTile");
					neighbouringTile.setStatusEffect(true);
				}
			}
		}
	}

	/**
	 * Generates items for tundra region, all positions of item are randomized
	 * every time player loads into tundra zone.
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
					(PlayerPeon) getPlayerEntity(),"tundra");
			entities.add(potion);
			items.add(potion.getDisplay());
		}

		for (int i = 0; i < NUM_SHIELDS; i++) {
			Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
					Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
			Shield shield = new Shield(tile, false,
					(PlayerPeon) getPlayerEntity(),"tundra");
			entities.add(shield);
			items.add(shield.getDisplay());
		}

		DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
				items);
		GameManager.get().addManager(dialog);
	}


	private void addCampfire(float col, float row) {
		Tile tile = getTile(col, row);
		TundraCampfire campfire = new TundraCampfire(tile);
		entities.add(campfire);
	}

	private void addTreeLog(float col, float row) {
		Tile tile = getTile(col, row);
		TundraTreeLog treeLog = new TundraTreeLog(tile);
		entities.add(treeLog);
	}

	private void addRock(float col, float row) {
		Tile tile = getTile(col, row);
		TundraRock rock = new TundraRock(tile);
		entities.add(rock);
	}

	@Override
	protected void generateTiles() {
		// Provide available enemies to the EnemyManager
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);
		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}
	}
}