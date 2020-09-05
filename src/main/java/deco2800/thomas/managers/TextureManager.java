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
            textureMap.put("basic_npc", new Texture("resources/tutorial/basic_npc.jpg"));

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

            // enemies
            textureMap.put("goblin_swamp_left", new Texture("resources/enemies/goblin_swamp_left.png"));
            textureMap.put("goblin_swamp_right", new Texture("resources/enemies/goblin_swamp_right.png"));
            textureMap.put("orc_swamp_left", new Texture("resources/enemies/orc_swamp_left.png"));
            textureMap.put("orc_swamp_right", new Texture("resources/enemies/orc_swamp_right.png"));
            textureMap.put("orc_volcano_left", new Texture("resources/enemies/orc_volcano_left.png"));
            textureMap.put("orc_volcano_right", new Texture("resources/enemies/orc_volcano_right.png"));
            textureMap.put("dummy", new Texture("resources/enemies/dummy.png"));
            textureMap.put("elder_dragon", new Texture("resources/enemies/elder_dragon.png"));

            // storyline
			textureMap.put("stone_floor", new Texture("resources/tutorial/tutorial-tile-design-opt1.png"));
			textureMap.put("target", new Texture("resources/tutorial/tutorial-decoration-target.png"));
			textureMap.put("portal", new Texture("resources/tutorial/tutorial-decoration-portal.png"));
			textureMap.put("stash", new Texture("resources/tutorial/tutorial-decoration-weapon-stash.png"));
			textureMap.put("barrel", new Texture("resources/tutorial/tutorial-decoration-barrel.png"));
			textureMap.put("dialog-box", new Texture("resources/tutorial/tutorial-helper-box.png"));
			textureMap.put("orb", new Texture("resources/orb.png"));

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
            textureMap.put("fireball_right", new Texture("resources/combat/fireball_right.png"));

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
