package deco2800.thomas.worlds.tundra;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Orb;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.entities.environment.tundra.TundraCampfire;
import deco2800.thomas.entities.environment.tundra.TundraRock;
import deco2800.thomas.entities.environment.tundra.TundraTreeLog;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
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

		// Provide available enemies to the EnemyManager
		Orc tundraOrc = new Orc(1, 0.05f, 100, "orc_tundra");
		Dragon boss = new Dragon(3, 0.03f, 1000, "dragon_tundra", 3);

		EnemyManager enemyManager = new EnemyManager(this, 5, Arrays.asList(tundraOrc), boss);
		GameManager.get().addManager(enemyManager);
		enemyManager.spawnBoss(0, 0);
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