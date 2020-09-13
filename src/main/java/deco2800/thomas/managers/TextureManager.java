package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game saving
 * file reads from being completed during rendering.
 * <p>
 * With this in mind don't load textures you're not going to use.
 * Textures that are not used should probably (at some point) be removed
 * from the list and then read from disk when needed again using some type
 * of reference counting
 *
 * @author Tim Hadwen
 */
public class TextureManager extends AbstractManager {

	/**
	 * The width of the tile to use then positioning the tile.
	 */
	public static final int TILE_WIDTH = 320;

	/**
	 * The height of the tile to use when positioning the tile.
	 */
	public static final int TILE_HEIGHT = 278;

	//private final Logger log = LoggerFactory.getLogger(TextureManager.class);

	/**
	 * A HashMap of all textures with string keys
	 */
	private Map<String, Texture> textureMap = new HashMap<>();

	/**
	 * Constructor
	 * Currently loads up all the textures but probably shouldn't/doesn't
	 * need to.
	 */
	public TextureManager() {
		try {
			textureMap.put("background", new Texture("resources/background.jpg"));
			textureMap.put("spacman_ded", new Texture("resources/spacman.png"));
			textureMap.put("spacman_blue", new Texture("resources/spacman_blue.png"));

			// enemies
			textureMap.put("goblin_swamp_left", new Texture("resources/enemies/goblin_swamp_left.png"));
			textureMap.put("goblin_swamp_right", new Texture("resources/enemies/goblin_swamp_right.png"));
			textureMap.put("goblin_volcano_left", new Texture("resources/enemies/goblin_volcano_left.png"));
			textureMap.put("goblin_volcano_right", new Texture("resources/enemies/goblin_volcano_right.png"));
			textureMap.put("goblin_tundra_left", new Texture("resources/enemies/goblin_tundra_left.png"));
			textureMap.put("goblin_tundra_right", new Texture("resources/enemies/goblin_tundra_right.png"));
			textureMap.put("goblin_desert_left", new Texture("resources/enemies/goblin_desert_left.png"));
			textureMap.put("goblin_desert_right", new Texture("resources/enemies/goblin_desert_right.png"));
			textureMap.put("orc_swamp_left", new Texture("resources/enemies/orc_swamp_left.png"));
			textureMap.put("orc_swamp_right", new Texture("resources/enemies/orc_swamp_right.png"));
			textureMap.put("orc_volcano_left", new Texture("resources/enemies/orc_volcano_left.png"));
			textureMap.put("orc_volcano_right", new Texture("resources/enemies/orc_volcano_right.png"));
			textureMap.put("orc_tundra_left", new Texture("resources/enemies/orc_tundra_left.png"));
			textureMap.put("orc_tundra_right", new Texture("resources/enemies/orc_tundra_right.png"));
			textureMap.put("orc_desert_left", new Texture("resources/enemies/orc_desert_left.png"));
			textureMap.put("orc_desert_right", new Texture("resources/enemies/orc_desert_right.png"));
			textureMap.put("dragon_swamp_left", new Texture("resources/enemies/dragon_swamp_left.png"));
			textureMap.put("dragon_swamp_right", new Texture("resources/enemies/dragon_swamp_right.png"));
			textureMap.put("dragon_volcano_left", new Texture("resources/enemies/dragon_volcano_left.png"));
			textureMap.put("dragon_volcano_right", new Texture("resources/enemies/dragon_volcano_right.png"));
			textureMap.put("dragon_tundra_left", new Texture("resources/enemies/dragon_tundra_left.png"));
			textureMap.put("dragon_tundra_right", new Texture("resources/enemies/dragon_tundra_right.png"));
			textureMap.put("dragon_desert_left", new Texture("resources/enemies/dragon_desert_left.png"));
			textureMap.put("dragon_desert_right", new Texture("resources/enemies/dragon_desert_right.png"));
			textureMap.put("dummy", new Texture("resources/enemies/dummy.png"));
			textureMap.put("elder_dragon", new Texture("resources/enemies/elder_dragon.png"));

			textureMap.put("grass_0", new Texture("resources/square.png"));
			textureMap.put("grass_1", new Texture("resources/square2.png"));
			textureMap.put("grass_2", new Texture("resources/square3.png"));
			textureMap.put("selection", new Texture("resources/square-select.png"));
			textureMap.put("path", new Texture("resources/yellow_selection.png"));
			textureMap.put("buildingA", new Texture("resources/buildingA.png"));
			textureMap.put("buildingB", new Texture("resources/building3x2.png"));

			textureMap.put("tree", new Texture("resources/tree.png"));
			textureMap.put("rock", new Texture("resources/rock_L.png"));
			textureMap.put("fenceN-S", new Texture("resources/fence_N-S.png"));
			textureMap.put("fenceE-W", new Texture("resources/fence_E-W.png"));
			textureMap.put("fenceN-W", new Texture("resources/fence_N-W.png"));
			textureMap.put("fenceS-W", new Texture("resources/fence_S-W.png"));
			textureMap.put("fenceN-E", new Texture("resources/fence_N-E.png"));
			textureMap.put("fenceS-E", new Texture("resources/fence_S-E.png"));
			textureMap.put("projectile", new Texture("resources/rocks.png"));

			// storyline
			textureMap.put("stone_floor", new Texture("resources/tutorial/tutorial-tile-design.png"));
			textureMap.put("target", new Texture("resources/tutorial/tutorial-target.png"));
			textureMap.put("portal", new Texture("resources/tutorial/tutorial-portal.png"));
			textureMap.put("stash", new Texture("resources/tutorial/tutorial-stash.png"));
			textureMap.put("barrel", new Texture("resources/tutorial/tutorial-barrel.png"));
			textureMap.put("chest", new Texture("resources/tutorial/tutorial-chest.png"));
			textureMap.put("dialog-box", new Texture("resources/tutorial/guideline.png"));
			textureMap.put("orb", new Texture("resources/orb.png"));
			textureMap.put("victory", new Texture("resources/tutorial/victory-screen.png"));
			textureMap.put("defeat", new Texture("resources/tutorial/defeat-screen.png"));
			textureMap.put("pause", new Texture("resources/tutorial/pause-menu-beta.png"));

			// npcs
			textureMap.put("tutorial_npc", new Texture("resources/npcs/tutorial_npc.png"));
			textureMap.put("desert_npc1", new Texture("resources/npcs/npc1_desert.png"));
			textureMap.put("desert_npc2", new Texture("resources/npcs/npc2_desert.png"));
			textureMap.put("tundra_npc1", new Texture("resources/npcs/npc1_tundra.png"));
			textureMap.put("tundra_npc2", new Texture("resources/npcs/npc2_tundra.png"));
			textureMap.put("swamp_npc1", new Texture("resources/npcs/npc1_swamp.png"));
			textureMap.put("swamp_npc2", new Texture("resources/npcs/npc2_swamp.png"));
			textureMap.put("volcano_npc1", new Texture("resources/npcs/npc1_volcano.png"));
			textureMap.put("volcano_npc2", new Texture("resources/npcs/npc2_volcano.png"));
			textureMap.put("merchant_npc1", new Texture("resources/npcs/npc3.png"));
			textureMap.put("merchant_npc2", new Texture("resources/npcs/npc4.png"));
			textureMap.put("merchant_npc3", new Texture("resources/npcs/npc5.png"));

			//health & game over screen
			textureMap.put("health0", new Texture("resources/healthResources/health-bar-0.png"));
			textureMap.put("health5", new Texture("resources/healthResources/health-bar-5.png"));
			textureMap.put("health10", new Texture("resources/healthResources/health-bar-10.png"));
			textureMap.put("health15", new Texture("resources/healthResources/health-bar-15.png"));
			textureMap.put("health20", new Texture("resources/healthResources/health-bar-20.png"));
			textureMap.put("health25", new Texture("resources/healthResources/health-bar-25.png"));
			textureMap.put("health30", new Texture("resources/healthResources/health-bar-30.png"));
			textureMap.put("health35", new Texture("resources/healthResources/health-bar-35.png"));
			textureMap.put("health40", new Texture("resources/healthResources/health-bar-40.png"));
			textureMap.put("health45", new Texture("resources/healthResources/health-bar-45.png"));
			textureMap.put("health50", new Texture("resources/healthResources/health-bar-50.png"));
			textureMap.put("health55", new Texture("resources/healthResources/health-bar-55.png"));
			textureMap.put("health60", new Texture("resources/healthResources/health-bar-60.png"));
			textureMap.put("health65", new Texture("resources/healthResources/health-bar-65.png"));
			textureMap.put("health70", new Texture("resources/healthResources/health-bar-70.png"));
			textureMap.put("health75", new Texture("resources/healthResources/health-bar-75.png"));
			textureMap.put("health80", new Texture("resources/healthResources/health-bar-80.png"));
			textureMap.put("health85", new Texture("resources/healthResources/health-bar-85.png"));
			textureMap.put("health90", new Texture("resources/healthResources/health-bar-90.png"));
			textureMap.put("health95", new Texture("resources/healthResources/health-bar-95.png"));
			textureMap.put("health100", new Texture("resources/healthResources/health-bar-100.png"));
			textureMap.put("game-over", new Texture("resources/healthResources/game-over.png"));

			// Attacks
			textureMap.put("fireball_left", new Texture("resources/combat/fireball_left.png"));
			textureMap.put("fireball_right", new Texture("resources/combat/fireball_right.png"));

			// Player
			textureMap.put("player_left", new Texture("resources/combat/move_left.png"));
			textureMap.put("player_right", new Texture("resources/combat/move_right.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		addEnvironmentTextures();
	}

	private void addEnvironmentTextures() {
		addDesertTextures();
		addSwampTextures();
		addTundraTextures();
		addVolcanoTextures();
		addOrbTextures();
	}

	private void addOrbTextures() {
		try {
			textureMap.put("orb_1", new Texture("resources/orbs/orb1.png"));
			textureMap.put("orb_2", new Texture("resources/orbs/orb2.png"));
			textureMap.put("orb_3", new Texture("resources/orbs/orb3.png"));
			textureMap.put("orb_4", new Texture("resources/orbs/orb4.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addVolcanoTextures() {
		try {
			textureMap.put("Volcano_1", new Texture("resources/environment/volcano/tile/Volcano_1.png"));
			textureMap.put("Volcano_2", new Texture("resources/environment/volcano/tile/Volcano_2.png"));
			textureMap.put("Volcano_3", new Texture("resources/environment/volcano/tile/Volcano_3.png"));
			textureMap.put("Volcano_4", new Texture("resources/environment/volcano/tile/Volcano_4.png"));
			textureMap.put("Volcano_5", new Texture("resources/environment/volcano/tile/Volcano_5.png"));
			textureMap.put("Volcano_6", new Texture("resources/environment/volcano/tile/Volcano_6.png"));
			textureMap.put("Volcano_7", new Texture("resources/environment/volcano/tile/Volcano_7.png"));
			textureMap.put("Volcano_8", new Texture("resources/environment/volcano/tile/Volcano_8.png"));
			textureMap.put("Ruins_1", new Texture("resources/environment/volcano/entities/Ruins_1.png"));
			textureMap.put("Ruins_2", new Texture("resources/environment/volcano/entities/Ruins_2.png"));
			textureMap.put("Ruins_3", new Texture("resources/environment/volcano/entities/Ruins_3.png"));
			textureMap.put("Ruins_4", new Texture("resources/environment/volcano/entities/Ruins_4.png"));
			textureMap.put("Ruins_5", new Texture("resources/environment/volcano/entities/Ruins_5.png"));
			textureMap.put("Ruins_6", new Texture("resources/environment/volcano/entities/Ruins_6.png"));
			textureMap.put("Ruins_7", new Texture("resources/environment/volcano/entities/Ruins_7.png"));
			textureMap.put("Bones", new Texture("resources/environment/volcano/entities/Bones.png"));
			textureMap.put("BurningTree", new Texture("resources/environment/volcano/entities/BurningTreePlaceholder.png"));
			textureMap.put("DragonSkull", new Texture("resources/environment/volcano/entities/DragonSkull2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTundraTextures() {
		try {
			textureMap.put("tundra-tile-1", new Texture("resources/environment/tundra/tiles/tundra-tile-1.png"));
			textureMap.put("tundra-tile-2", new Texture("resources/environment/tundra/tiles/tundra-tile-2.png"));
			textureMap.put("tundra-tile-3", new Texture("resources/environment/tundra/tiles/tundra-tile-3.png"));
			textureMap.put("tundra-tile-4", new Texture("resources/environment/tundra/tiles/tundra-tile-4.png"));
			textureMap.put("tundra-tile-5", new Texture("resources/environment/tundra/tiles/tundra-tile-5.png"));
			textureMap.put("tundra-campfire", new Texture("resources/environment/tundra/entities/campfire.png"));
			textureMap.put("tundra-tree-log", new Texture("resources/environment/tundra/entities/tree-log.png"));
			textureMap.put("tundra-rock-1", new Texture("resources/environment/tundra/entities/rock-1.png"));
			textureMap.put("tundra-rock-2", new Texture("resources/environment/tundra/entities/rock-2.png"));
			textureMap.put("tundra-rock-3", new Texture("resources/environment/tundra/entities/rock-3.png"));
			textureMap.put("tundra-rock-4", new Texture("resources/environment/tundra/entities/rock-4.png"));
			textureMap.put("tundra-rock-5", new Texture("resources/environment/tundra/entities/rock-5.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addSwampTextures() {
		try {
			textureMap.put("swamp_1", new Texture("resources/environment/swamp/tile/swamp_1.png"));
			textureMap.put("swamp_2", new Texture("resources/environment/swamp/tile/swamp_2.png"));
			textureMap.put("swamp_3", new Texture("resources/environment/swamp/tile/swamp_3.png"));
			textureMap.put("swamp_4", new Texture("resources/environment/swamp/tile/swamp_4.png"));

			// Swamp Zone Entity Textures
			textureMap.put("swamp_dead_tree",
					new Texture("resources/environment/swamp/entities/swamp_dead_tree.png"));
			textureMap.put("swamp_fallen_tree",
					new Texture("resources/environment/swamp/entities/swamp_fallen_tree.png"));
			textureMap.put("swamp_pond",
					new Texture("resources/environment/swamp/entities/swamp_lake.png"));
			textureMap.put("swamp_tree_log",
					new Texture("resources/environment/swamp/entities/swamp_tree_log.png"));
			textureMap.put("swamp_tree_stub",
					new Texture("resources/environment/swamp/entities/swamp_tree_stub.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addDesertTextures() {
		try {
			textureMap.put("desert_1", new Texture("resources/environment/desert/tile/sand/desert_1.png"));
			textureMap.put("desert_2", new Texture("resources/environment/desert/tile/sand/desert_2.png"));
			textureMap.put("desert_3", new Texture("resources/environment/desert/tile/sand/desert_3.png"));
			textureMap.put("desert_4", new Texture("resources/environment/desert/tile/sand/desert_4.png"));
			textureMap.put("desert_5", new Texture("resources/environment/desert/tile/sand/desert_5.png"));
			textureMap.put("desert_6", new Texture("resources/environment/desert/tile/sand/desert_6.png"));
			textureMap.put("desert_7", new Texture("resources/environment/desert/tile/sand/desert_7.png"));
			textureMap.put("oasis_1", new Texture("resources/environment/desert/tile/oasis/oasis_1.png"));
			textureMap.put("oasis_2", new Texture("resources/environment/desert/tile/oasis/oasis_2.png"));
			textureMap.put("oasis_3", new Texture("resources/environment/desert/tile/oasis/oasis_1.png"));
			textureMap.put("oasis_4", new Texture("resources/environment/desert/tile/oasis/oasis_4.png"));
			textureMap.put("oasis_5", new Texture("resources/environment/desert/tile/oasis/oasis_5.png"));
			textureMap.put("oasis_6", new Texture("resources/environment/desert/tile/oasis/oasis_6.png"));

			textureMap.put("desertOrb", new Texture("resources/environment/desert/entities/desert_orb.png"));
			textureMap.put("desertCactus1", new Texture("resources/environment/desert/entities/desert_cactus1.png"));
			textureMap.put("desertCactus2", new Texture("resources/environment/desert/entities/desert_cactus2.png"));
			textureMap.put("desertCactus3", new Texture("resources/environment/desert/entities/desert_cactus3.png"));
			textureMap.put("desertCactus4", new Texture("resources/environment/desert/entities/desert_cactus4.png"));
			textureMap.put("desertSandDune", new Texture("resources/environment/desert/entities/desert_sand_dune.png"));
			textureMap.put("desertDeadTree1", new Texture("resources/environment/desert/entities/desert_dead_tree1.png"));
			textureMap.put("desertDeadTree2", new Texture("resources/environment/desert/entities/desert_dead_tree2.png"));
			textureMap.put("desertQuicksand", new Texture("resources/environment/desert/entities/desert_quicksand.png"));
			textureMap.put("oasisTree1", new Texture("resources/environment/desert/entities/oasis_tree1.png"));
			textureMap.put("oasisTree2", new Texture("resources/environment/desert/entities/oasis_tree2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Attacks
		textureMap.put("fireball_right", new Texture("resources/combat/fireball_right.png"));
		textureMap.put("fireball_left", new Texture("resources/combat/fireball_left.png"));
	}

	/**
	 * Gets a texture object for a given string id
	 *
	 * @param id Texture identifier
	 * @return Texture for given id
	 */
	public Texture getTexture(String id) {
		if (textureMap.containsKey(id)) {
			return textureMap.get(id);
		} else {
			//log.info("Texture map does not contain P{}, returning default texture.", id);
			return textureMap.get("spacman_ded");
		}

	}

	/**
	 * Checks whether or not a texture is available.
	 *
	 * @param id Texture identifier
	 * @return If texture is available or not.
	 */
	public boolean hasTexture(String id) {
		return textureMap.containsKey(id);

	}

	/**
	 * Saves a texture with a given id
	 *
	 * @param id       Texture id
	 * @param filename Filename within the assets folder
	 */
	public void saveTexture(String id, String filename) {
		if (!textureMap.containsKey(id)) {
			textureMap.put(id, new Texture(filename));
		}
	}
}
