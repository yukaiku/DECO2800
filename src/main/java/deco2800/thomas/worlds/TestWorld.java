package deco2800.thomas.worlds;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Orc;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Environment.Rock;
import deco2800.thomas.entities.Environment.Tree;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.managers.NonPlayablePeonManager;
import deco2800.thomas.util.SquareVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.Environment.Tree;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Environment.Rock;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import java.util.*;

@SuppressWarnings("unused")
public class TestWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(TestWorld.class);
	/*
	 * radius for tiles 1 - 7 2 - 19 3 - 37 4 - 61 5 - 91 10 - 331 25 - 1951 50 -
	 * 7,651 100 - 30,301 150 - 67,951 200 - 120601
	 * 
	 * N = 1 + 6 * summation[0 -> N]
	 */
	boolean notGenerated = true;
	private final int WORLD_WIDTH = 25; // Height and width vars for the map size; constrains tile gen
	private final int WORLD_HEIGHT = 25; // Note the map will double these numbers (bounds are +/- these limits)

	public TestWorld() {
		super();
		this.width = WORLD_WIDTH;
		this.height = WORLD_HEIGHT;
	}

	@Override
	protected void generateWorld() {

	}

	//5 tile building
	private StaticEntity createBuilding1(float col, float row) {
		StaticEntity building;
		List<Part> parts = new ArrayList<Part>();

		parts.add(new Part(new SquareVector(1, -1f), "spacman_ded", true));
		parts.add(new Part(new SquareVector(-1, -1f), "spacman_ded", true));
		parts.add(new Part(new SquareVector(-1, 1f), "spacman_ded", true));
		parts.add(new Part(new SquareVector(1, 1f), "spacman_ded", true));
		parts.add(new Part(new SquareVector(0, 0), "spacman_ded", true));

		return new StaticEntity(col, row, 1, parts);
	}
	
	//building with a fence
	private StaticEntity createBuilding2(float col, float row) {
		List<Part> parts = new ArrayList<Part>();
		parts.add(new Part(new SquareVector(0, 0), "buildingA", true));
		// left
		parts.add(new Part(new SquareVector(-2, 0), "fenceN-S", true));
		parts.add(new Part(new SquareVector(-2, 1), "fenceN-S", true));

		// Bottom
		parts.add(new Part(new SquareVector(-1, -1), "fenceE-W", true));
		parts.add(new Part(new SquareVector(0, -1), "fenceE-W", true));
		parts.add(new Part(new SquareVector(1, -1), "fenceE-W", true));

		// Top
		parts.add(new Part(new SquareVector(-1, 2), "fenceE-W", true));
		parts.add(new Part(new SquareVector(0, 2), "fenceE-W", true));
		parts.add(new Part(new SquareVector(1, 2), "fenceE-W", true));

		// bottom right corner
		parts.add(new Part(new SquareVector(2, -1), "fenceN-W", true));

		// bottom left
		parts.add(new Part(new SquareVector(-2, -1), "fenceN-E", true));

		// top left
		parts.add(new Part(new SquareVector(-2, 2), "fenceS-E", true));

		// top right
		parts.add(new Part(new SquareVector(2, 2), "fenceS-W", true));

		StaticEntity building = new StaticEntity(col, row, 1, parts);
		entities.add(building);
		return building;

	}

	private void addTree(float col, float row) {
		Map<SquareVector, String> textures = new HashMap<SquareVector, String>();
		Tile t = GameManager.get().getWorld().getTile(col, row);
		Tree tree = new  Tree(t, true);
		entities.add(tree);
	}


	//this get ran on first game tick so the world tiles exist.
	public void createBuildings() {
		Random random = new Random();
		int tileCount = GameManager.get().getWorld().getTiles().size();
		// Generate some rocks to mine later
		for (int i = 0; i < 100; i++) {
			Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
			if (t != null) {
				entities.add(new Rock(t, true));
			}
		}
		// Add some trees
		for (int i = 0; i < 50; i++) {
			Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
			if (t != null) {
				entities.add(new Tree(t, true));
			}
		}
		entities.add(createBuilding2(-5, 0f));

	}

	@Override
	protected void generateTiles() {
		Random random = new Random();
		for (int q = -WORLD_WIDTH; q < WORLD_WIDTH; q++) {
			for (int r = -WORLD_HEIGHT; r < WORLD_HEIGHT; r++) {
				int elevation = random.nextInt(3);
				String type = "grass_";
				type += elevation;
				tiles.add(new Tile(type, q, r));
			}
		}
		// Create the entities in the game
		this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.15f, 50));
		addEntity(this.getPlayerEntity());

		// Provide available enemies to the EnemyManager
		Orc orc = new Orc(1, 0.05f, 100);
		Orc speedyOrc = new Orc(1, 0.09f, 50, "spacman_red");
		Orc hostileTree = new Orc(1, 0.18f, 20, "tree"); // be careful with this enemy
		Dragon boss = new Dragon(1, 1, 1000);
		EnemyManager enemyManager = new EnemyManager(this, 7, Arrays.asList(orc, speedyOrc));
		GameManager.get().addManager(enemyManager);

		// Create a combatManager to create combatEntities on click
		CombatManager combatManager = new CombatManager(this);
		GameManager.get().addManager(combatManager);
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);
		//addTree(0f, 0f);
		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}

		if (notGenerated) {
			createBuildings();
			//addTree(-1, -3f);
			
			notGenerated = false;
		}
	}

}

/*
 * print out Neighbours for (Tile tile : tiles) { System.out.println();
 * System.out.println(tile); for (Entry<Integer, Tile> firend :
 * tile.getNeighbours().entrySet()) { switch (firend.getKey()) { case
 * Tile.north: System.out.println("north " +(firend.getValue())); break; case
 * Tile.north_east: System.out.println("north_east " + (firend.getValue()));
 * break; case Tile.north_west: System.out.println("north_west " +
 * (firend.getValue())); break; case Tile.south: System.out.println("south " +
 * (firend.getValue())); break; case Tile.south_east:
 * System.out.println("south_east " +(firend.getValue())); break; case
 * Tile.south_west: System.out.println("south_west " + (firend.getValue()));
 * break; } } }
 * 
 */
