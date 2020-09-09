package deco2800.thomas.entities.environment.tundra;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class TundraTreeLog extends StaticEntity {
	public static final String ENTITY_ID_STRING = "TundraTreeLog";

	private static final String TEXTURE = "tundra-tree-log";
	private static final boolean OBSTRUCTED = true;

	private TundraTreeLog() {
	}

	public TundraTreeLog(Tile tile) {
		super(tile, RenderConstants.TUNDRA_TREE_LOG, TEXTURE, OBSTRUCTED);
		this.setObjectName(ENTITY_ID_STRING);
	}
}
