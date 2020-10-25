package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game saving
 * file reads from being completed during rendering.
 *
 * With this in mind don't load textures you're not going to use.
 * Textures that are not used should probably (at some point) be removed
 * from the list and then read from disk when needed again using some type
 * of reference counting
 */
public class TextureManager extends AbstractManager {
    private final Logger log = LoggerFactory.getLogger(TextureManager.class);
    // width of the tile to use then positioning the tile
    public static final int TILE_WIDTH = 320;

    // height of the tile to use when positioning the tile
    public static final int TILE_HEIGHT = 278;

    // HashMap storing all textures with string keys
    private final Map<String, Texture> textureMap = new HashMap<>();

    // HashMap storing all animation frames
    private final Map<String, Array<TextureRegion>> animationFrames = new HashMap<>();

    // indicate if the animation frames have been loaded
    private boolean animationLoaded = false;

    /**
     * Texture manager constructor
     * Please don't load textures inside of constructor as it will
     * cause testing and game loading to perform slow.
     */
    public TextureManager() {
        try {
            // base texture for all entities
            textureMap.put("spacman_ded", new Texture("resources/spacman.png"));
            textureMap.put("background", new Texture("resources/background.jpg"));
            // you can load textures inside of individual methods below
		} catch (GdxRuntimeException e) {
			log.error(Arrays.toString(e.getStackTrace()));
		}
	}

    /* ------------------------------------------------------------------------
     * 				             BASE TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads base textures.
     */
	public void loadBaseTextures() {
        try {
            // Player
            textureMap.put("player_left", new Texture("resources/player/leftmech1_move.png"));
            textureMap.put("player_right", new Texture("resources/player/rightmech1_move.png"));

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

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             ENEMY TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads enemy textures.
     */
    public void loadEnemyTextures() {
        try {
            textureMap.put("dummy", new Texture("resources/enemies/dummy.png"));
            textureMap.put("elder_dragon", new Texture("resources/enemies/elder_dragon.png"));

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
            textureMap.put("dragonSwamp", new Texture("resources/enemies/olddragonswamp.png"));
            textureMap.put("dragonTundra", new Texture("resources/enemies/dragon_tundra.png"));
            textureMap.put("dragonVolcano", new Texture("resources/enemies/dragon_volcano.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             COMBAT TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads combat textures.
     */
    public void loadCombatTextures() {
        try {
            // Attacks
            textureMap.put("fireball_right", new Texture("resources/combat/fireball_right.png"));
            textureMap.put("explosion", new Texture("resources/combat/explosive_fireball3.png"));
            textureMap.put("wizard_icon", new Texture("resources/combat/waterwizard.png"));
            textureMap.put("knight_icon", new Texture("resources/combat/knight.png"));
            textureMap.put("knight_hotbar", new Texture("resources/combat/hotbar2.png"));
            textureMap.put("active_selector", new Texture("resources/combat/selector.png"));

            textureMap.put("fireballIcon", new Texture("resources/combat/firewizard_skill_icon.png"));
            textureMap.put("stingIcon", new Texture("resources/combat/right_swamp_skill1.png"));
            textureMap.put("iceballIcon", new Texture("resources/combat/tundra_skill_icon.png"));
            textureMap.put("explosionIcon", new Texture("resources/combat/explosive_fireball3.png"));
            textureMap.put("watershieldIcon", new Texture("resources/combat/watershield_icon.png"));
            textureMap.put("healIcon", new Texture("resources/combat/health_skill_icon.png"));
            textureMap.put("sandTornadoIcon", new Texture("resources/combat/desert_skill_icon.png"));
            textureMap.put("iceBreathIcon", new Texture("resources/combat/ice-wave4.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             STORYLINE TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads storyline textures.
     */
    public void loadStorylineTextures() {
        try {
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
            textureMap.put("fire-team", new Texture("resources/storyline/team_selection/fire_team.png"));
            textureMap.put("water-team", new Texture("resources/storyline/team_selection/water_team.png"));

            // orbs
            textureMap.put("orb", new Texture("resources/orb.png"));
            textureMap.put("orb_1", new Texture("resources/orbs/orb1.png"));
            textureMap.put("orb_2", new Texture("resources/orbs/orb2.png"));
            textureMap.put("orb_3", new Texture("resources/orbs/orb3.png"));
            textureMap.put("orb_4", new Texture("resources/orbs/orb4.png"));
            textureMap.put("orb_0qt", new Texture("resources/orbs/orbbar0.png"));
            textureMap.put("orb_1qt", new Texture("resources/orbs/orbbar1.png"));
            textureMap.put("orb_2qt", new Texture("resources/orbs/orbbar2.png"));
            textureMap.put("orb_3qt", new Texture("resources/orbs/orbbar3.png"));
            textureMap.put("orb_4qt", new Texture("resources/orbs/orbbar4.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				               NPC TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads all NPC textures.
     */
    public void loadNPCTextures() {
        try {
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
            textureMap.put("npc_swamp_dungeon_green", new Texture("resources/npcs/npc_swamp_dungeon_green.png"));
            textureMap.put("npc_swamp_dungeon_blue", new Texture("resources/npcs/npc_swamp_dungeon_blue.png"));
            textureMap.put("npc_swamp_dungeon_red", new Texture("resources/npcs/npc_swamp_dungeon_red.png"));
            textureMap.put("npc_swamp_dungeon_orange", new Texture("resources/npcs/npc_swamp_dungeon_orange.png"));
            textureMap.put("npc_swamp_dungeon_white", new Texture("resources/npcs/npc_swamp_dungeon_white.png"));
            textureMap.put("npc_swamp_dungeon_yellow", new Texture("resources/npcs/npc_swamp_dungeon_yellow.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             INVENTORY TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads all inventory textures.
     */
    public void loadInventoryTextures() {
        try {
            // Items
            textureMap.put("potion_small", new Texture("resources/inventory/potion-small.png"));
            textureMap.put("potion_large", new Texture("resources/inventory/potion-large.png"));
            textureMap.put("poison", new Texture("resources/inventory/potion-poison.png"));
            textureMap.put("armour_iron", new Texture("resources/inventory/armour-iron.png"));
            textureMap.put("armour_wood", new Texture("resources/inventory/armour-wood.png"));
            textureMap.put("treasure_box", new Texture("resources/inventory/treasure-box.png"));
            textureMap.put("cdreduction_buff", new Texture("resources/inventory/cdreduction_buff.png"));
            textureMap.put("attack_buff", new Texture("resources/inventory/attack_buff.png"));
            textureMap.put("crown_buff", new Texture("resources/inventory/crown_buff.png"));

            // Inventory Menu
            textureMap.put("inventory_menu", new Texture("resources/inventory_menu.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             MINIMAP TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads all minimap textures.
     */
	public void loadMinimapTextures() {
        try {
            textureMap.put("iconDefault", new Texture("resources/enemies/orc_swamp_icon.png"));
            textureMap.put("orcSwampIcon", new Texture("resources/enemies/orc_swamp_icon.png"));
            textureMap.put("orcVolcanoIcon", new Texture("resources/enemies/orc_volcano_icon.png"));
            textureMap.put("orcTundraIcon", new Texture("resources/enemies/orc_tundra_icon.png"));
            textureMap.put("orcDesertIcon", new Texture("resources/enemies/orc_desert_icon.png"));

            textureMap.put("goblinSwampIcon", new Texture("resources/enemies/goblin_swamp_icon.png"));
            textureMap.put("goblinVolcanoIcon", new Texture("resources/enemies/goblin_volcano_icon.png"));
            textureMap.put("goblinTundraIcon", new Texture("resources/enemies/goblin_tundra_icon.png"));
            textureMap.put("goblinDesertIcon", new Texture("resources/enemies/goblin_desert_icon.png"));

            textureMap.put("dragonSwampIcon", new Texture("resources/enemies/dragon_swamp_icon.png"));
            textureMap.put("dragonVolcanoIcon", new Texture("resources/enemies/dragon_volcano_icon.png"));
            textureMap.put("dragonTundraIcon", new Texture("resources/enemies/dragon_tundra_icon.png"));
            textureMap.put("dragonDesertIcon", new Texture("resources/enemies/dragon_desert_icon.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				             HEALTH TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads all health textures.
     */
	public void loadHealthTextures() {
        try {
            // Health
            // Player health & game over screen
            textureMap.put("health0", new Texture("resources/healthResources/health-bar-update-0.png"));
            textureMap.put("health5", new Texture("resources/healthResources/health-bar-update-5.png"));
            textureMap.put("health10", new Texture("resources/healthResources/health-bar-update-10.png"));
            textureMap.put("health15", new Texture("resources/healthResources/health-bar-update-15.png"));
            textureMap.put("health20", new Texture("resources/healthResources/health-bar-update-20.png"));
            textureMap.put("health25", new Texture("resources/healthResources/health-bar-update-25.png"));
            textureMap.put("health30", new Texture("resources/healthResources/health-bar-update-30.png"));
            textureMap.put("health35", new Texture("resources/healthResources/health-bar-update-35.png"));
            textureMap.put("health40", new Texture("resources/healthResources/health-bar-update-40.png"));
            textureMap.put("health45", new Texture("resources/healthResources/health-bar-update-45.png"));
            textureMap.put("health50", new Texture("resources/healthResources/health-bar-update-50.png"));
            textureMap.put("health55", new Texture("resources/healthResources/health-bar-update-55.png"));
            textureMap.put("health60", new Texture("resources/healthResources/health-bar-update-60.png"));
            textureMap.put("health65", new Texture("resources/healthResources/health-bar-update-65.png"));
            textureMap.put("health70", new Texture("resources/healthResources/health-bar-update-70.png"));
            textureMap.put("health75", new Texture("resources/healthResources/health-bar-update-75.png"));
            textureMap.put("health80", new Texture("resources/healthResources/health-bar-update-80.png"));
            textureMap.put("health85", new Texture("resources/healthResources/health-bar-update-85.png"));
            textureMap.put("health90", new Texture("resources/healthResources/health-bar-update-90.png"));
            textureMap.put("health95", new Texture("resources/healthResources/health-bar-update-95.png"));
            textureMap.put("health100", new Texture("resources/healthResources/health-bar-update-100.png"));
            textureMap.put("game-over", new Texture("resources/healthResources/game-over.png"));

            // boss health
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

            textureMap.put("bossHealth-desert100", new Texture("resources/healthResources/bossHealthBar-Desert-100.png"));
            textureMap.put("bossHealth-desert95", new Texture("resources/healthResources/bossHealthBar-Desert-95.png"));
            textureMap.put("bossHealth-desert90", new Texture("resources/healthResources/bossHealthBar-Desert-90.png"));
            textureMap.put("bossHealth-desert85", new Texture("resources/healthResources/bossHealthBar-Desert-85.png"));
            textureMap.put("bossHealth-desert80", new Texture("resources/healthResources/bossHealthBar-Desert-80.png"));
            textureMap.put("bossHealth-desert75", new Texture("resources/healthResources/bossHealthBar-Desert-75.png"));
            textureMap.put("bossHealth-desert70", new Texture("resources/healthResources/bossHealthBar-Desert-70.png"));
            textureMap.put("bossHealth-desert65", new Texture("resources/healthResources/bossHealthBar-Desert-65.png"));
            textureMap.put("bossHealth-desert60", new Texture("resources/healthResources/bossHealthBar-Desert-60.png"));
            textureMap.put("bossHealth-desert55", new Texture("resources/healthResources/bossHealthBar-Desert-55.png"));
            textureMap.put("bossHealth-desert50", new Texture("resources/healthResources/bossHealthBar-Desert-50.png"));
            textureMap.put("bossHealth-desert45", new Texture("resources/healthResources/bossHealthBar-Desert-45.png"));
            textureMap.put("bossHealth-desert40", new Texture("resources/healthResources/bossHealthBar-Desert-40.png"));
            textureMap.put("bossHealth-desert35", new Texture("resources/healthResources/bossHealthBar-Desert-35.png"));
            textureMap.put("bossHealth-desert30", new Texture("resources/healthResources/bossHealthBar-Desert-30.png"));
            textureMap.put("bossHealth-desert25", new Texture("resources/healthResources/bossHealthBar-Desert-25.png"));
            textureMap.put("bossHealth-desert20", new Texture("resources/healthResources/bossHealthBar-Desert-20.png"));
            textureMap.put("bossHealth-desert15", new Texture("resources/healthResources/bossHealthBar-Desert-15.png"));
            textureMap.put("bossHealth-desert10", new Texture("resources/healthResources/bossHealthBar-Desert-10.png"));
            textureMap.put("bossHealth-desert5", new Texture("resources/healthResources/bossHealthBar-Desert-5.png"));
            textureMap.put("bossHealth-desert0", new Texture("resources/healthResources/bossHealthBar-Desert-0.png"));

            textureMap.put("bossHealth-swamp100", new Texture("resources/healthResources/bossHealthBar-Swamp-100.png"));
            textureMap.put("bossHealth-swamp95", new Texture("resources/healthResources/bossHealthBar-Swamp-95.png"));
            textureMap.put("bossHealth-swamp90", new Texture("resources/healthResources/bossHealthBar-Swamp-90.png"));
            textureMap.put("bossHealth-swamp85", new Texture("resources/healthResources/bossHealthBar-Swamp-85.png"));
            textureMap.put("bossHealth-swamp80", new Texture("resources/healthResources/bossHealthBar-Swamp-80.png"));
            textureMap.put("bossHealth-swamp75", new Texture("resources/healthResources/bossHealthBar-Swamp-75.png"));
            textureMap.put("bossHealth-swamp70", new Texture("resources/healthResources/bossHealthBar-Swamp-70.png"));
            textureMap.put("bossHealth-swamp65", new Texture("resources/healthResources/bossHealthBar-Swamp-65.png"));
            textureMap.put("bossHealth-swamp60", new Texture("resources/healthResources/bossHealthBar-Swamp-60.png"));
            textureMap.put("bossHealth-swamp55", new Texture("resources/healthResources/bossHealthBar-Swamp-55.png"));
            textureMap.put("bossHealth-swamp50", new Texture("resources/healthResources/bossHealthBar-Swamp-50.png"));
            textureMap.put("bossHealth-swamp45", new Texture("resources/healthResources/bossHealthBar-Swamp-45.png"));
            textureMap.put("bossHealth-swamp40", new Texture("resources/healthResources/bossHealthBar-Swamp-40.png"));
            textureMap.put("bossHealth-swamp35", new Texture("resources/healthResources/bossHealthBar-Swamp-35.png"));
            textureMap.put("bossHealth-swamp30", new Texture("resources/healthResources/bossHealthBar-Swamp-30.png"));
            textureMap.put("bossHealth-swamp25", new Texture("resources/healthResources/bossHealthBar-Swamp-25.png"));
            textureMap.put("bossHealth-swamp20", new Texture("resources/healthResources/bossHealthBar-Swamp-20.png"));
            textureMap.put("bossHealth-swamp15", new Texture("resources/healthResources/bossHealthBar-Swamp-15.png"));
            textureMap.put("bossHealth-swamp10", new Texture("resources/healthResources/bossHealthBar-Swamp-10.png"));
            textureMap.put("bossHealth-swamp5", new Texture("resources/healthResources/bossHealthBar-Swamp-5.png"));
            textureMap.put("bossHealth-swamp0", new Texture("resources/healthResources/bossHealthBar-Swamp-0.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    /* ------------------------------------------------------------------------
     * 				           ENVIRONMENT TEXTURES
     * ------------------------------------------------------------------------ */

    /**
     * Preloads all environment textures.
     */
    public void loadEnvironmentTextures() {
        addDesertTextures();
        addSwampTextures();
        addSwampDungeonTextures();
        addTundraTextures();
        addVolcanoTextures();
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
            textureMap.put("VolcanoPortal", new Texture("resources/environment/portals/volcano_portal.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
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
            textureMap.put("tundra-encryption-machine", new Texture("resources/environment/tundra/entities/typewriter.png"));
            textureMap.put("tundra-dungeon-help", new Texture("resources/environment/tundra/entities/questionmark.png"));
        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
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
            textureMap.put("swamp_portal", new Texture("resources/environment/portals/swamp_portal.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private void addSwampDungeonTextures() {
        try {
            textureMap.put("dungeon-black", new Texture("resources/environment/swamp_dungeon/tile/dungeon-black.png"));
            textureMap.put("dungeon-light-black", new Texture("resources/environment/swamp_dungeon/tile/dungeon-light-black.png"));
            textureMap.put("dungeon-grey", new Texture("resources/environment/swamp_dungeon/tile/dungeon-grey.png"));
            textureMap.put("dungeon-green", new Texture("resources/environment/swamp_dungeon/tile/dungeon-green.png"));
            textureMap.put("dungeon-yellow", new Texture("resources/environment/swamp_dungeon/tile/dungeon-yellow.png"));

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
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

        } catch (GdxRuntimeException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

	/* ------------------------------------------------------------------------
	 * 				               ANIMATION FRAMES
	 * ------------------------------------------------------------------------ */

    /**
     * Add the animation frames from a collections of files or a single sprite file.
     * <p>
     * To import from a collection of files, use {@link #addAnimationFramesCollection(String id, String... files)}
     * To import from a sprite sheet, use {@link #addAnimationFramesSprite(String id, String file, int numOfFrames,
     * int frameWidth, int frameHeight, boolean horizontal)}
     * <p>
     * Note: The initial facing direction should be RIGHT. The flipped version is not needed.
     */
    public void loadAnimationFrames() {
        try {
            // players
            addAnimationFramesCollection("playerIdle", "resources/player/rightmech1_move.png");
            addAnimationFramesCollection("playerWalk", "resources/player/rightmech1_walk1.png",
                    "resources/player/rightmech1_walk2.png", "resources/player/rightmech1_walk3.png",
                    "resources/player/rightmech1_walk4.png");
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
            addAnimationFramesCollection("iceballDefault", "resources/combat/right_tundra_skill1.png");
            addAnimationFramesCollection("iceballExplosion", "resources/combat/tundra_skill2.png",
                    "resources/combat/tundra_skill3.png", "resources/combat/tundra_skill4.png",
                    "resources/combat/tundra_skill5.png");

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

            // Water shield
            addAnimationFramesCollection("waterShield", "resources/combat/tundra_skill2.png",
                    "resources/combat/tundra_skill3.png", "resources/combat/tundra_skill4.png",
                    "resources/combat/tundra_skill5.png");

            // Heal effect
            addAnimationFramesCollection("healEffect", "resources/combat/health_skill1.png",
                    "resources/combat/health_skill2.png", "resources/combat/health_skill3.png",
                    "resources/combat/health_skill4.png");

            // Sting skill projectile and effect
            addAnimationFramesCollection("stingProjectile", "resources/combat/right_swamp_skill1.png");
            addAnimationFramesCollection("stingEffect", "resources/combat/swamp_skill2.png",
                    "resources/combat/swamp_skill3.png", "resources/combat/swamp_skill4.png",
                    "resources/combat/swamp_skill5.png");

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
            addAnimationFramesCollection("dragonSwampIdle", "resources/enemies/dragon_swamp_2.png");
            addAnimationFramesCollection("dragonTundraIdle", "resources/enemies/dragon_tundra.png");
            addAnimationFramesCollection("dragonVolcanoIdle", "resources/enemies/dragon_volcano.png");

            // goblin attack sprites
            addAnimationFramesSprite("goblinDesertAttack", "resources/enemies/goblin_desert_sprite_sheet_ext.png",
                    6, 176, 236, true);
            addAnimationFramesSprite("goblinSwampAttack", "resources/enemies/goblin_swamp_sprite_sheet_ext.png",
                    6, 176, 236, true);
            addAnimationFramesSprite("goblinTundraAttack", "resources/enemies/goblin_tundra_sprite_sheet_ext.png",
                    6, 176, 236, true);
            addAnimationFramesSprite("goblinVolcanoAttack", "resources/enemies/goblin_volcano_sprite_sheet_ext.png",
                    6, 176, 236, true);

            // goblin walking sprites
            addAnimationFramesSprite("goblinDesertWalk", "resources/enemies/goblin_desert_walking_sprites.png",
                    9, 147, 188, false);
            addAnimationFramesSprite("goblinSwampWalk", "resources/enemies/goblin_swamp_walking_sprites.png",
                    9, 147, 188, false);
            addAnimationFramesSprite("goblinTundraWalk", "resources/enemies/goblin_tundra_walking_sprites.png",
                    9, 147, 188, false);
            addAnimationFramesSprite("goblinVolcanoWalk", "resources/enemies/goblin_volcano_walking_sprites.png",
                    9, 147, 188, false);

			// orc attack sprites
			addAnimationFramesSprite("orcDesertAttack", "resources/enemies/orc_desert_sprite_sheet_ext.png",
					10, 145, 172, true);
			addAnimationFramesSprite("orcSwampAttack", "resources/enemies/orc_swamp_sprite_sheet_ext.png",
					10, 145, 172, true);
			addAnimationFramesSprite("orcTundraAttack", "resources/enemies/orc_tundra_sprite_sheet_ext.png",
					10, 145, 172, true);
			addAnimationFramesSprite("orcVolcanoAttack", "resources/enemies/orc_volcano_sprite_sheet_ext.png",
					10, 145, 172, true);

			//orc walking sprites
            addAnimationFramesSprite("orcDesertWalk", "resources/enemies/orc_desert_walking_sprites.png",
                    8, 162, 192, true);
            addAnimationFramesSprite("orcSwampWalk", "resources/enemies/orc_swamp_walking_sprites.png",
                    8, 162, 192, true);
            addAnimationFramesSprite("orcTundraWalk", "resources/enemies/orc_tundra_walking_sprites.png",
                    8, 162, 192, true);
            addAnimationFramesSprite("orcVolcanoWalk", "resources/enemies/orc_volcano_walking_sprites.png",
                    8, 162, 192, true);

            // dragon attack sprites
            addAnimationFramesSprite("dragonDesertAttack", "resources/enemies/desert_dragon_attack_sprites.png",
                    3, 701, 701, true);
            addAnimationFramesSprite("dragonSwampAttack", "resources/enemies/dragon_swamp_attacking_sprites.png",
                    2, 652, 531, true);
            addAnimationFramesSprite("dragonTundraAttack", "resources/enemies/dragon_tundra_attacking_sprites.png",
                    4, 1363, 1000, true);
            addAnimationFramesSprite("dragonVolcanoAttack", "resources/enemies/dragon_volcano_fireball_sprites.png",
                    3, 1190, 1100, false);

            // dragon walk sprites
            addAnimationFramesSprite("dragonVolcanoWalk", "resources/enemies/dragon_volcano_walking_sprites.png",
            8, 1163, 1100, false);
            addAnimationFramesSprite("dragonTundraWalk", "resources/enemies/dragon_tundra_walking_sprites.png",
                    2, 1363, 1000, false);
            addAnimationFramesSprite("dragonDesertWalk", "resources/enemies/desert_dragon_tail_sprites.png",
                    5, 701, 701, true);
            addAnimationFramesSprite("dragonSwampWalk", "resources/enemies/dragon_swamp_walking_sprites.png",
                    2, 652, 531, true);

        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        } finally {
            this.animationLoaded = true;
        }
    }

    /**
     * Import animation frames from multiple files, where each file is one frame.
     */
    private void addAnimationFramesCollection(String id, String... files) throws GdxRuntimeException {
        Array<TextureRegion> frames = new Array<>();
        for (String file : files) frames.add(new TextureRegion(new Texture(file)));
        this.animationFrames.put(id, frames);
    }

    /**
     * Import animation frames from single sprite file.
     */
    private void addAnimationFramesSprite(String id, String file, int numOfFrames, int frameWidth, int frameHeight,
                                          boolean horizontal) throws GdxRuntimeException {
        Array<TextureRegion> frames = new Array<>();
        Texture sprite = new Texture(file);
        if (horizontal) for (int i = 0; i < numOfFrames; i++) {
            frames.add(new TextureRegion(sprite, i * frameWidth, 0, frameWidth, frameHeight));
        }
        else for (int i = 0; i < numOfFrames; i++) {
            frames.add(new TextureRegion(sprite, 0, i * frameHeight, frameWidth, frameHeight));
        }
        this.animationFrames.put(id, frames);
    }

    /**
     * Get the frame of the animation
     *
     * @param id Texture identifier
     * @return Frame of the animation
     */
    public Array<TextureRegion> getAnimationFrames(String id) {
        if (!this.animationLoaded) {
            loadAnimationFrames();
        }
        try {
            return this.animationFrames.get(id);
        } catch (Exception e) {
            return null;
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
            return textureMap.get("spacman_ded");
        }
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

    public int getSize() {
        return textureMap.keySet().size();
    }
}
