package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

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
 * @author Tim Hadwen and studio 2
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
	private final Map<String, Texture> textureMap = new HashMap<>();

	// a hashmap storing all animation frames
	private final Map<String, Array<TextureRegion>> animationFrames = new HashMap<>();

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
			textureMap.put("enemyDefault", new Texture("resources/enemies/goblin_swamp.png"));
			textureMap.put("goblinDesert", new Texture("resources/enemies/goblin_desert.png"));
			textureMap.put("goblinSwamp", new Texture("resources/enemies/goblin_swamp.png"));
			textureMap.put("goblinTundra", new Texture("resources/enemies/goblin_tundra.png"));
			textureMap.put("goblinVolcano", new Texture("resources/enemies/goblin_volcano.png"));
			textureMap.put("orcDesert", new Texture("resources/enemies/orc_desert.png"));
			textureMap.put("orcSwamp", new Texture("resources/enemies/orc_swamp.png"));
			textureMap.put("orcTundra", new Texture("resources/enemies/orc_tundra.png"));
			textureMap.put("orcVolcano", new Texture("resources/enemies/orc_volcano.png"));
			textureMap.put("dragonDesert", new Texture("resources/enemies/dragon_desert.png"));
			textureMap.put("dragonSwamp", new Texture("resources/enemies/dragon_swamp.png"));
			textureMap.put("dragonTundra", new Texture("resources/enemies/dragon_tundra.png"));
			textureMap.put("dragonVolcano", new Texture("resources/enemies/dragon_volcano.png"));

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

			// Storyline
			// tutorial_world
			textureMap.put("stone-1", new Texture("resources/storyline/tile/tile1.png"));
			textureMap.put("stone-2", new Texture("resources/storyline/tile/tile2.png"));
			textureMap.put("stone-3", new Texture("resources/storyline/tile/tile3.png"));
			textureMap.put("target", new Texture("resources/storyline/object/target.png"));
			textureMap.put("portal", new Texture("resources/storyline/object/portal.png"));
			textureMap.put("stash", new Texture("resources/storyline/object/stash.png"));
			textureMap.put("barrel", new Texture("resources/storyline/object/barrel.png"));
			textureMap.put("chest", new Texture("resources/storyline/object/chest.png"));
			textureMap.put("leave", new Texture("resources/storyline/object/leave-message.png"));
			textureMap.put("dialog-box", new Texture("resources/storyline/story-intro.png"));
			textureMap.put("control", new Texture("resources/storyline/control.png"));
			textureMap.put("orb", new Texture("resources/orb.png"));
			// main_world
			textureMap.put("victory", new Texture("resources/storyline/victory-screen.png"));
			textureMap.put("defeat", new Texture("resources/storyline/defeat-screen.png"));
			textureMap.put("pause", new Texture("resources/storyline/pause-menu.png"));
			// zone_intro
			textureMap.put("trs-desert", new Texture("resources/storyline/zone_intro/transition_desert.png"));
			textureMap.put("trs-swamp", new Texture("resources/storyline/zone_intro/transition_swamp.png"));
			textureMap.put("trs-tundra", new Texture("resources/storyline/zone_intro/transition_tundra.png"));
			textureMap.put("trs-volcano", new Texture("resources/storyline/zone_intro/transition_volcano.png"));
			// team_selection
			textureMap.put("air-water", new Texture("resources/storyline/team_selection/air-water.png"));
			textureMap.put("earth-air", new Texture("resources/storyline/team_selection/earth-air.png"));
			textureMap.put("earth-water", new Texture("resources/storyline/team_selection/earth-water.png"));
			textureMap.put("fire-air", new Texture("resources/storyline/team_selection/fire-air.png"));
			textureMap.put("fire-earth", new Texture("resources/storyline/team_selection/fire-earth.png"));
			textureMap.put("fire-water", new Texture("resources/storyline/team_selection/fire-water.png"));

			// NPCs
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

			//Health
			// Player health & game over screen
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

			//boss health
			textureMap.put("bossHealth-desert", new Texture("resources/healthResources/bossHealthBar-desert2.png"));
			textureMap.put("bossHealth-volcano100", new Texture("resources/healthResources/bossHealthBar-fire2 - 100.png"));
			textureMap.put("bossHealth-volcano95", new Texture("resources/healthResources/bossHealthBar-fire2 - 95.png"));
			textureMap.put("bossHealth-volcano90", new Texture("resources/healthResources/bossHealthBar-fire2 - 90.png"));
			textureMap.put("bossHealth-volcano85", new Texture("resources/healthResources/bossHealthBar-fire2 - 85.png"));
			textureMap.put("bossHealth-volcano80", new Texture("resources/healthResources/bossHealthBar-fire2 - 80.png"));
			textureMap.put("bossHealth-volcano75", new Texture("resources/healthResources/bossHealthBar-fire2 - 75.png"));
			textureMap.put("bossHealth-volcano70", new Texture("resources/healthResources/bossHealthBar-fire2 - 70.png"));
			textureMap.put("bossHealth-volcano65", new Texture("resources/healthResources/bossHealthBar-fire2 - 65.png"));
			textureMap.put("bossHealth-volcano60", new Texture("resources/healthResources/bossHealthBar-fire2 - 60.png"));
			textureMap.put("bossHealth-volcano55", new Texture("resources/healthResources/bossHealthBar-fire2 - 55.png"));
			textureMap.put("bossHealth-volcano50", new Texture("resources/healthResources/bossHealthBar-fire2 - 50.png"));
			textureMap.put("bossHealth-volcano45", new Texture("resources/healthResources/bossHealthBar-fire2 - 45.png"));
			textureMap.put("bossHealth-volcano40", new Texture("resources/healthResources/bossHealthBar-fire2 - 40.png"));
			textureMap.put("bossHealth-volcano35", new Texture("resources/healthResources/bossHealthBar-fire2 - 35.png"));
			textureMap.put("bossHealth-volcano30", new Texture("resources/healthResources/bossHealthBar-fire2 - 30.png"));
			textureMap.put("bossHealth-volcano25", new Texture("resources/healthResources/bossHealthBar-fire2 - 25.png"));
			textureMap.put("bossHealth-volcano20", new Texture("resources/healthResources/bossHealthBar-fire2 - 20.png"));
			textureMap.put("bossHealth-volcano15", new Texture("resources/healthResources/bossHealthBar-fire2 - 15.png"));
			textureMap.put("bossHealth-volcano10", new Texture("resources/healthResources/bossHealthBar-fire2 - 10.png"));
			textureMap.put("bossHealth-volcano5", new Texture("resources/healthResources/bossHealthBar-fire2 - 5.png"));
			textureMap.put("bossHealth-volcano0", new Texture("resources/healthResources/bossHealthBar-fire2 - 0.png"));

			textureMap.put("bossHealth-tundra100", new Texture("resources/healthResources/bossHealthBar-ice2 - 100.png"));
			textureMap.put("bossHealth-tundra95", new Texture("resources/healthResources/bossHealthBar-ice2 - 95.png"));
			textureMap.put("bossHealth-tundra90", new Texture("resources/healthResources/bossHealthBar-ice2 - 90.png"));
			textureMap.put("bossHealth-tundra85", new Texture("resources/healthResources/bossHealthBar-ice2 - 85.png"));
			textureMap.put("bossHealth-tundra80", new Texture("resources/healthResources/bossHealthBar-ice2 - 80.png"));
			textureMap.put("bossHealth-tundra75", new Texture("resources/healthResources/bossHealthBar-ice2 - 75.png"));
			textureMap.put("bossHealth-tundra70", new Texture("resources/healthResources/bossHealthBar-ice2 - 70.png"));
			textureMap.put("bossHealth-tundra65", new Texture("resources/healthResources/bossHealthBar-ice2 - 65.png"));
			textureMap.put("bossHealth-tundra60", new Texture("resources/healthResources/bossHealthBar-ice2 - 60.png"));
			textureMap.put("bossHealth-tundra55", new Texture("resources/healthResources/bossHealthBar-ice2 - 55.png"));
			textureMap.put("bossHealth-tundra50", new Texture("resources/healthResources/bossHealthBar-ice2 - 50.png"));
			textureMap.put("bossHealth-tundra45", new Texture("resources/healthResources/bossHealthBar-ice2 - 45.png"));
			textureMap.put("bossHealth-tundra40", new Texture("resources/healthResources/bossHealthBar-ice2 - 40.png"));
			textureMap.put("bossHealth-tundra35", new Texture("resources/healthResources/bossHealthBar-ice2 - 35.png"));
			textureMap.put("bossHealth-tundra30", new Texture("resources/healthResources/bossHealthBar-ice2 - 30.png"));
			textureMap.put("bossHealth-tundra25", new Texture("resources/healthResources/bossHealthBar-ice2 - 25.png"));
			textureMap.put("bossHealth-tundra20", new Texture("resources/healthResources/bossHealthBar-ice2 - 20.png"));
			textureMap.put("bossHealth-tundra15", new Texture("resources/healthResources/bossHealthBar-ice2 - 15.png"));
			textureMap.put("bossHealth-tundra10", new Texture("resources/healthResources/bossHealthBar-ice2 - 10.png"));
			textureMap.put("bossHealth-tundra5", new Texture("resources/healthResources/bossHealthBar-ice2 - 5.png"));
			textureMap.put("bossHealth-tundra0", new Texture("resources/healthResources/bossHealthBar-ice2 - 0.png"));
			
			textureMap.put("bossHealth-swamp", new Texture("resources/healthResources/bossHealthBar-swamp2.png"));

			// Attacks
			textureMap.put("fireball_right", new Texture("resources/combat/fireball_right.png"));
			textureMap.put("explosion", new Texture("resources/combat/explosive_fireball3.png"));
			textureMap.put("wizard_icon", new Texture("resources/combat/waterwizard.png"));
			textureMap.put("knight_icon", new Texture("resources/combat/knight.png"));
			textureMap.put("knight_hotbar", new Texture("resources/combat/hotbar2.png"));
			textureMap.put("active_selector", new Texture("resources/combat/selector.png"));

			// Player
			textureMap.put("player_left", new Texture("resources/player/leftmech1_move.png"));
			textureMap.put("player_right", new Texture("resources/player/rightmech1_move.png"));

			// Inventory
			textureMap.put("potion_small", new Texture("resources/inventory/potion-small.png"));
			textureMap.put("potion_large", new Texture("resources/inventory/potion-large.png"));
			textureMap.put("armour_iron", new Texture("resources/inventory/armour-iron.png"));
			textureMap.put("armour_wood", new Texture("resources/inventory/armour-wood.png"));
			textureMap.put("treasure_box", new Texture("resources/inventory/treasure-box.png"));

			//Inventory Menu
			textureMap.put("inventory_menu", new Texture("resources/inventory_menu.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		addEnvironmentTextures();
		addAnimationFrames();
		addMinimapIcons();
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
			textureMap.put("LavaPool", new Texture("resources/environment/transparent_tiletexture.png"));
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
		textureMap.put("fireballIcon", new Texture("resources/combat/fireball_right.png"));
		textureMap.put("stingIcon", new Texture("resources/combat/fireball_left.png"));
		textureMap.put("iceballIcon", new Texture("resources/combat/iceball_small.png"));
		textureMap.put("explosionIcon", new Texture("resources/combat/explosive_fireball3.png"));
	}

	private void addMinimapIcons() {
		textureMap.put("iconDefault", new Texture("resources/enemies/orc_volcano_icon.png"));
	}

	/* ------------------------------------------------------------------------
	 * 				               ANIMATION FRAMES
	 * ------------------------------------------------------------------------ */

	/**
	 * Add the animation frames from a collections of files or a single sprite file.
	 *
	 * To import from a collection of files, use {@link #addAnimationFramesCollection(String id, String... files)}
	 * To import from a sprite sheet, use {@link #addAnimationFramesSprite(String id, String file, int numOfFrames,
	 * 		int frameWidth, int frameHeight, boolean horizontal)}
	 *
	 * Note: The initial facing direction should be RIGHT. The flipped version is not needed.
	 */
	private void addAnimationFrames() {
		try {
			// players
			addAnimationFramesCollection("playerIdle", "resources/player/rightmech1_move.png");
			addAnimationFramesCollection("playerMelee", "resources/player/rightmech1_melee3.png",
					"resources/player/rightmech1_melee1.png", "resources/player/rightmech1_melee2.png",
					"resources/player/rightmech1_melee2.png", "resources/player/rightmech1_melee1.png",
					"resources/player/rightmech1_melee2.png", "resources/player/rightmech1_melee2.png",
					"resources/player/rightmech1_melee1.png", "resources/player/rightmech1_melee2.png",
					"resources/player/rightmech1_melee2.png", "resources/player/rightmech1_melee3.png");  // intended
			addAnimationFramesCollection("playerRange", "resources/player/rightmech1_move.png");
			addAnimationFramesCollection("playerFireball", "resources/combat/right_skill1_fire1.png",
					"resources/combat/right_skill1_fire2.png", "resources/combat/right_skill1_fire3.png",
					"resources/combat/right_skill1_fire4.png", "resources/combat/right_skill1_fire5.png");
			addAnimationFramesCollection("playerFireballDefault", "resources/combat/right_skill1_fire1.png");
			addAnimationFramesCollection("playerSpin", "resources/player/rightmech1_move.png",
					"resources/player/leftmech1_move.png");

			// fireballs
			addAnimationFramesCollection("fireballDefault", "resources/combat/fireball_right.png");
			addAnimationFramesCollection("fireballExplosion", "resources/combat/explosive_fireball1.png",
					"resources/combat/explosive_fireball2.png", "resources/combat/explosive_fireball3.png",
					"resources/combat/explosive_fireball4.png", "resources/combat/explosive_fireball5.png",
					"resources/combat/explosive_fireball6.png", "resources/combat/explosive_fireball7.png",
					"resources/combat/explosive_fireball8.png", "resources/combat/explosive_fireball9.png");

			// Iceballs
			addAnimationFramesCollection("iceballDefault", "resources/combat/iceball_small.png");
			addAnimationFramesCollection("iceballExplosion", "resources/combat/water_skill1_1.png",
					"resources/combat/water_skill1_2.png", "resources/combat/water_skill1_3.png",
					"resources/combat/water_skill1_4.png");

			// Icebreath attack
			addAnimationFramesCollection("freezeTile", "resources/combat/ice-wave0.png",
					"resources/combat/ice-wave1.png", "resources/combat/ice-wave2.png",
					"resources/combat/ice-wave3.png", "resources/combat/ice-wave4.png",
					"resources/combat/ice-wave5.png", "resources/combat/ice-wave6.png",
					"resources/combat/ice-wave7.png", "resources/combat/ice-wave8.png",
					"resources/combat/ice-wave9.png", "resources/combat/ice-wave10.png",
					"resources/combat/ice-wave11.png");

			// Sand Tornado attack
			addAnimationFramesCollection("sandTornado", "resources/combat/tornado0.png",
					"resources/combat/tornado1.png", "resources/combat/tornado2.png",
					"resources/combat/tornado3.png", "resources/combat/tornado4.png",
					"resources/combat/tornado5.png", "resources/combat/tornado6.png",
					"resources/combat/tornado7.png", "resources/combat/tornado8.png");

			// enemies
			addAnimationFramesCollection("dummyIdle", "resources/enemies/dummy.png");
			addAnimationFramesCollection("goblinDesertIdle", "resources/enemies/goblin_desert.png");
			addAnimationFramesCollection("goblinSwampIdle", "resources/enemies/goblin_swamp.png");
			addAnimationFramesCollection("goblinTundraIdle", "resources/enemies/goblin_tundra.png");
			addAnimationFramesCollection("goblinVolcanoIdle", "resources/enemies/goblin_volcano.png");
			addAnimationFramesCollection("orcDesertIdle", "resources/enemies/orc_desert.png");
			addAnimationFramesCollection("orcSwampIdle", "resources/enemies/orc_swamp.png");
			addAnimationFramesCollection("orcTundraIdle", "resources/enemies/orc_tundra.png");
			addAnimationFramesCollection("orcVolcanoIdle", "resources/enemies/orc_volcano.png");
			addAnimationFramesCollection("dragonDesertIdle", "resources/enemies/dragon_desert.png");
			addAnimationFramesCollection("dragonSwampIdle", "resources/enemies/dragon_swamp.png");
			addAnimationFramesCollection("dragonTundraIdle", "resources/enemies/dragon_tundra.png");
			addAnimationFramesCollection("dragonVolcanoIdle", "resources/enemies/dragon_volcano.png");

			// goblin attack sprites
			addAnimationFramesSprite("goblinDesertAttack", "resources/enemies/goblin_desert_sprite_sheet.png",
					3, 350, 486, true);
			addAnimationFramesSprite("goblinSwampAttack", "resources/enemies/goblin_swamp_sprite_sheet.png",
					3, 350, 486, true);
			addAnimationFramesSprite("goblinTundraAttack", "resources/enemies/goblin_tundra_sprite_sheet.png",
					3, 350, 486, true);
			addAnimationFramesSprite("goblinVolcanoAttack", "resources/enemies/goblin_volcano_sprite_sheet.png",
					3, 350, 486, true);

			// orc attack sprites
			addAnimationFramesSprite("orcDesertAttack", "resources/enemies/orc_desert_sprite_sheet.png",
					3, 257, 324, false);
			addAnimationFramesSprite("orcSwampAttack", "resources/enemies/orc_swamp_sprite_sheet.png",
					3, 257, 324, false);

			addAnimationFramesSprite("orcTundraAttack", "resources/enemies/orc_tundra_sprite_sheet.png",
					3, 257, 324, false);
			addAnimationFramesSprite("orcVolcanoAttack", "resources/enemies/orc_volcano_sprite_sheet.png",
					3, 257, 324, false);

			// dragon attack sprites
			addAnimationFramesSprite("dragonDesertAttack", "resources/enemies/dragon_desert_sprite_sheet.png",
					2, 472, 264, true);
			addAnimationFramesSprite("dragonSwampAttack", "resources/enemies/dragon_swamp_sprite_sheet.png",
					2, 492, 264, false);
			addAnimationFramesSprite("dragonTundraAttack", "resources/enemies/dragon_tundra_sprite_sheet.png",
					2, 492, 264, true);
			addAnimationFramesSprite("dragonVolcanoAttack", "resources/enemies/dragon_volcano_sprite_sheet.png",
					2, 492, 264, false);

		} catch (GdxRuntimeException e) {
			e.printStackTrace();
		}
	}

	/** Import animation frames from multiple files, where each file is one frame. */
	private void addAnimationFramesCollection(String id, String ...files) throws GdxRuntimeException {
		Array<TextureRegion> frames = new Array<>();
		for (String file : files) frames.add(new TextureRegion(new Texture(file)));
		animationFrames.put(id, frames);
	}

	/** Import animation frames from single sprite file. */
	private void addAnimationFramesSprite(String id, String file, int numOfFrames, int frameWidth, int frameHeight,
										  boolean horizontal) throws GdxRuntimeException {
		Array<TextureRegion> frames = new Array<>();
		Texture sprite = new Texture(file);
		if (horizontal) for (int i = 0; i < numOfFrames; i++) {
			frames.add(new TextureRegion(sprite, i * frameWidth, 0, frameWidth, frameHeight));
		} else for (int i = 0; i < numOfFrames; i++) {
			frames.add(new TextureRegion(sprite, 0, i * frameHeight, frameWidth, frameHeight));
		}
		animationFrames.put(id, frames);
	}

	/**
	 * Get the frame of the animation
	 * @param id Texture identifier
	 * @return Frame of the animation
	 */
	public Array<TextureRegion> getAnimationFrames(String id) {
		if (animationFrames.containsKey(id)) {
			return animationFrames.get(id);
		}
		return null;
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
