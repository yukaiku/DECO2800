package deco2800.thomas.entities;

// Class for storing the order that entities are ordered by when rendered 

public class RenderConstants {
    public static final int ORB = 2;

    private RenderConstants() {
	}

	// the higher the constant value, the topmost they are in the world
	// i.e. if trees have a higher value 
	public static final int ROCK_RENDER = 1;
	public static final int PEON_RENDER = 3;
	public static final int BUILDING_RENDER = 4;
	public static final int TREE_RENDER = 5;

	// Entities in Swamp Zone
	public static final int SWAMP_DEAD_TREE = 6;
	public static final int SWAMP_VINE_TREE = 7;
	public static final int SWAMP_POND = 8;
	public static final int SWAMP_FALLEN_TREE = 9;
	public static final int SWAMP_TREE_STUB = 10;
	public static final int SWAMP_TREE_LOG = 11;

	// Entities in Volcano Zone
	public static final int VOLCANO_RUINS = 12;
	public static final int VOLCANO_BONES = 13;
	public static final int VOLCANO_BURNING_TREE = 14;
	public static final int VOLCANO_DRAGON_SKULL = 15;
	public static final int VOLCANO_LAVA_POOL = 16;
	public static final int VOLCANO_GRAVEYARD = 17;
}
