package deco2800.thomas.entities;

// Class for storing the order that entities are ordered by when rendered 

public class RenderConstants {
	private RenderConstants() {
	}

	// the higher the constant value, the topmost they are in the world
	// i.e. if trees have a higher value 
	public static final int ROCK_RENDER = 1;
	public static final int PEON_RENDER = 3;
	public static final int TREE_RENDER = 5;
	public static final int TARGET_RENDER = 5;
	public static final int PORTAL_RENDER = 5;
	public static final int BUILDING_RENDER = 4;
}
