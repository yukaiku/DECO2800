package deco2800.thomas.worlds.tundra;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.tundra.TundraCampfire;
import deco2800.thomas.entities.environment.tundra.TundraDungeonPortal;
import deco2800.thomas.entities.environment.tundra.TundraRock;
import deco2800.thomas.entities.environment.tundra.TundraTreeLog;
import deco2800.thomas.entities.items.*;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.TundraNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("Duplicates")
public class TundraWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(TundraWorld.class);

	/**
	 * Load MAP_FILE_TILES_ONLY with the old DatabaseManager.loadWorld() static method
	 * Load MAP_FILE with the new DatabaseManager.loadWorldFromJsonFile() static method
	 */
	public static final String MAP_FILE_TILES_ONLY = "resources/environment/tundra/tundra-map-tiles-only.json";
	public static final String MAP_FILE = "resources/environment/tundra/tundra-map.json";

	private ArrayList<AbstractDialogBox> allTundraDialogues;

	public TundraWorld() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public TundraWorld(int width, int height) {
		super(width, height);
		DatabaseManager.loadWorld(this, MAP_FILE_TILES_ONLY);
		this.allTundraDialogues = new ArrayList<>();

		// PlayerPeon
		this.setPlayerEntity(new PlayerPeon(-3f, -24f, 0.15f));
		addEntity(this.getPlayerEntity());
		generateStaticEntities();
		generateItemEntities();

		// Provide enemies
		EnemyManager enemyManager = new EnemyManager(this, "tundraDragon", 7, "tundraOrc");
		GameManager.get().addManager(enemyManager);
		enemyManager.spawnBoss(0, 0);

		// Creates Tundra NPCs
		List<NonPlayablePeon> npnSpawns = new ArrayList<>();
		TundraNPC tundraNpc1 = new TundraNPC("TundraQuestNPC1", new SquareVector(-8, -24),"tundra_npc1");
		TundraNPC tundraNpc2 = new TundraNPC("TundraQuestNPC2", new SquareVector(-22, -9),"tundra_npc2");
		npnSpawns.add(tundraNpc1);
		npnSpawns.add(tundraNpc2);
		NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity, npnSpawns);
		GameManager.get().addManager(npcManager);

		//Creates dialogue manager
		this.allTundraDialogues.add(tundraNpc1.getBox());
		this.allTundraDialogues.add(tundraNpc2.getBox());
		DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
				this.allTundraDialogues);
		GameManager.get().addManager(dialog);

		//Updates difficulty manager
		DifficultyManager difficultyManager = GameManager.getManagerFromInstance(DifficultyManager.class);
		difficultyManager.setPlayerEntity((PlayerPeon) this.getPlayerEntity());
		difficultyManager.setDifficultyLevel(getType());

		// Start ambience
		GameManager.getManagerFromInstance(SoundManager.class).playAmbience("tundraAmbience");
	}

	@Override
	public String getType(){
		return "Tundra";
	}

	private void generateStaticEntities() {
		final int NUM_CAMPFIRES = 20;
		final int NUM_TREE_LOGS = 60;
		final int NUM_ROCKS = 60;

		SquareVector playerPos = getPlayerEntity().getPosition();

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

			while (tile == null || tile.hasParent() || tile.getCoordinates().isCloseEnoughToBeTheSame(playerPos)) {
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

		entities.add(new TundraDungeonPortal(this, -23, -23));
	}

	/**
	 * Generates items for tundra region, all positions of item are randomized
	 * every time player loads into tundra zone.
	 *
	 * Items: Health potions, Iron shields etc.
	 */
	private void generateItemEntities(){
		final int NUM_POTIONS = 6;
		final int NUM_IRON_ARMOUR = 1;
		final int NUM_CHESTS = 2;
		final String ITEM_BOX_STYLE = "tundra";
		

		for (int i = 0; i < NUM_POTIONS; i++) {
			Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
					Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
			if (!tile.hasParent()) {
				HealthPotion potion = new HealthPotion(tile, false,(PlayerPeon) getPlayerEntity(), ITEM_BOX_STYLE);
				entities.add(potion);
				this.allTundraDialogues.add(potion.getDisplay());
			} else {
				i--;
			}

		}
		for (int i = 0; i < NUM_IRON_ARMOUR; i++) {
			Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
					Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
			if (!tile.hasParent()) {
				IronArmour ironArmour = new IronArmour(tile, false,
						(PlayerPeon) getPlayerEntity(), ITEM_BOX_STYLE,200);
				entities.add(ironArmour);
				this.allTundraDialogues.add(ironArmour.getDisplay());
			} else {
				i--;
			}

		}
		for (int i = 0; i < NUM_CHESTS; i++) {
			Tile tile = getTile(Item.randomItemPositionGenerator(DEFAULT_WIDTH),
					Item.randomItemPositionGenerator(DEFAULT_HEIGHT));
			if (!tile.hasParent()) {
				Treasure chest = new Treasure(tile, false,(PlayerPeon) getPlayerEntity(), ITEM_BOX_STYLE);
				entities.add(chest);
				this.allTundraDialogues.add(chest.getDisplay());
			} else {
				i--;
			}
		}

		Tile cooldownring = getTile(18,17);
		CooldownRing cdring = new CooldownRing(cooldownring, false,
				(PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,0.5f);
		entities.add(cdring);
		this.allTundraDialogues.add(cdring.getDisplay());

		Tile attackAmuletTile = getTile(-19,14);
		Amulet attackAmulet = new Amulet(attackAmuletTile, false,
				(PlayerPeon) this.getPlayerEntity(), ITEM_BOX_STYLE,10);
		entities.add(attackAmulet);
		this.allTundraDialogues.add(attackAmulet.getDisplay());

	}

	public void addDialogue(AbstractDialogBox box){ this.allTundraDialogues.add(box);}

	public List<AbstractDialogBox> returnAllDialogues(){
		return this.allTundraDialogues;
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