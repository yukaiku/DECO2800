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

			// 4 Orb Textures
			textureMap.put("orb_1", new Texture("resources/orbs/orb1.png"));
			textureMap.put("orb_2", new Texture("resources/orbs/orb2.png"));
			textureMap.put("orb_3", new Texture("resources/orbs/orb3.png"));
			textureMap.put("orb_4", new Texture("resources/orbs/orb4.png"));

			// Swamp Zone Tile Textures
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

			// Tundra Zone Tile Texture
			textureMap.put("tundra-tile-1", new Texture("resources/environment/tundra/tundra-tile-1.png"));
			textureMap.put("tundra-tile-2", new Texture("resources/environment/tundra/tundra-tile-2.png"));
			textureMap.put("tundra-tile-3", new Texture("resources/environment/tundra/tundra-tile-3.png"));
			textureMap.put("tundra-tile-4", new Texture("resources/environment/tundra/tundra-tile-4.png"));
			textureMap.put("tundra-tile-5", new Texture("resources/environment/tundra/tundra-tile-5.png"));

			// textures for the desert zone
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
			// change these textures once they are drawn
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

			textureMap.put("grass_0", new Texture("resources/square.png"));
			textureMap.put("grass_1", new Texture("resources/square2.png"));
			textureMap.put("grass_2", new Texture("resources/square3.png"));

			textureMap.put("selection", new Texture("resources/square-select.png"));
			textureMap.put("path", new Texture("resources/yellow_selection.png"));

			textureMap.put("buildingB", new Texture("resources/building3x2.png"));

			textureMap.put("buildingA", new Texture("resources/buildingA.png"));
			textureMap.put("tree", new Texture("resources/tree.png"));
			textureMap.put("rock", new Texture("resources/rock_L.png"));
			textureMap.put("fenceN-S", new Texture("resources/fence_N-S.png"));
			textureMap.put("fenceE-W", new Texture("resources/fence_E-W.png"));
			textureMap.put("fenceN-W", new Texture("resources/fence_N-W.png"));
			textureMap.put("fenceS-W", new Texture("resources/fence_S-W.png"));
			textureMap.put("fenceN-E", new Texture("resources/fence_N-E.png"));
			textureMap.put("fenceS-E", new Texture("resources/fence_S-E.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
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
