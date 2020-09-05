package deco2800.thomas.entities;

// Class for storing the order that entities are ordered by when rendered 

public class RenderConstants {
	private RenderConstants() {
	}

	// the higher the constant value, the topmost they are in the world
	public static final int ROCK_RENDER = 1;
	public static final int PEON_RENDER = 3;
	public static final int BUILDING_RENDER = 4;
	public static final int TREE_RENDER = 5;

	// Entities in Swamp Zone
	public static final int SWAMP_DEAD_TREE = 6;

	public static final int SWAMP_POND = 6;
	public static final int SWAMP_FALLEN_TREE = 6;
	public static final int SWAMP_TREE_STUB = 6;
	public static final int SWAMP_TREE_LOG = 6;

	// Orb
	public static final int ORB = 12;

	public static final int SWAMP_VINE_TREE = 7;
	public static final int SWAMP_POND = 8;
	public static final int SWAMP_FALLEN_TREE = 9;
	public static final int SWAMP_TREE_STUB = 10;
	public static final int SWAMP_TREE_LOG = 11;

	// Entities in the Desert Zone
	public static final int DESERT_CACTUS = 12;
	public static final int DESERT_QUICKSAND = 13;
	public static final int DESERT_SAND_DUNE = 14;
	public static final int DESERT_DEAD_TREE = 15;
	public static final int OASIS_TREE = 16;
	public static final int OASIS_SHRUB = 17;
	public static final int DESERT_ORB = 18;
}
