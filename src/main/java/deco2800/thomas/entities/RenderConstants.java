package deco2800.thomas.entities;

// Class for storing the order that entities are ordered by when rendered 

public class RenderConstants {
	private RenderConstants() {
	}

	// the higher the constant value, the topmost they are in the world
	// i.e. if trees have a higher value 
	public static final int ROCK_RENDER = 1;
	public static final int TARGET_RENDER = 1;
	public static final int STASH_RENDER = 1;
	public static final int PORTAL_RENDER = 1;
	public static final int CHEST_RENDER = 1;
	public static final int PEON_RENDER = 3;
	public static final int PEON_EFFECT_RENDER = 4;
	public static final int TREE_RENDER = 5;
	public static final int PROJECTILE_RENDER = 6;
	public static final int BUILDING_RENDER = 1;

	// Entities in Swamp Zone
	public static final int SWAMP_DEAD_TREE = 1;
	public static final int SWAMP_POND = 1;
	public static final int SWAMP_FALLEN_TREE = 1;
	public static final int SWAMP_TREE_STUB = 1;
	public static final int SWAMP_TREE_LOG = 1;

	// Orb
	public static final int ORB = 2;

	// Entities in the Desert Zone
	public static final int DESERT_CACTUS = 1;
	public static final int DESERT_QUICKSAND = 1;
	public static final int DESERT_SAND_DUNE = 1;
	public static final int DESERT_DEAD_TREE = 1;
	public static final int OASIS_TREE = 1;
	public static final int OASIS_SHRUB = 1;
	public static final int DESERT_ORB = 1;

	// Entities in Volcano Zone
	public static final int VOLCANO_RUINS = 1;
	public static final int VOLCANO_BONES = 1;
	public static final int VOLCANO_BURNING_TREE = 1;
	public static final int VOLCANO_DRAGON_SKULL = 1;
	public static final int VOLCANO_LAVA_POOL = 2;
	public static final int VOLCANO_GRAVEYARD = 1;
	public static final int VOLCANO_PORTAL = 1;

	// Entities in Tundra Zone
	public static final int TUNDRA_CAMPFIRE = 1;
	public static final int TUNDRA_TREE_LOG = 1;
	public static final int TUNDRA_ROCK = 1;
	public static final int BARREL_RENDER = 1;
	public static final int TUNDRA_ENCRYPTION_MACHINE = 1;

	// All Item Entities render with a value of 2
	public static final int ITEM_RENDER = 1;
}
