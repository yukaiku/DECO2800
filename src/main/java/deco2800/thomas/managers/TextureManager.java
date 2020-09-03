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

			textureMap.put("swamp_1", new Texture("resources/environment/swamp/tile/swamp_1.png"));
			textureMap.put("swamp_2", new Texture("resources/environment/swamp/tile/swamp_2.png"));
			textureMap.put("swamp_3", new Texture("resources/environment/swamp/tile/swamp_3.png"));
			textureMap.put("swamp_4", new Texture("resources/environment/swamp/tile/swamp_4.png"));

			textureMap.put("Volcano_1", new Texture("resources/environment/volcano/tile/Volcano_1.png"));
			textureMap.put("Volcano_2", new Texture("resources/environment/volcano/tile/Volcano_2.png"));
			textureMap.put("Volcano_3", new Texture("resources/environment/volcano/tile/Volcano_3.png"));
			textureMap.put("Volcano_4", new Texture("resources/environment/volcano/tile/Volcano_4.png"));
			textureMap.put("Volcano_5", new Texture("resources/environment/volcano/tile/Volcano_5.png"));
			textureMap.put("Volcano_6", new Texture("resources/environment/volcano/tile/Volcano_6.png"));
			textureMap.put("Volcano_7", new Texture("resources/environment/volcano/tile/Volcano_7.png"));
			textureMap.put("Volcano_8", new Texture("resources/environment/volcano/tile/Volcano_8.png"));

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
