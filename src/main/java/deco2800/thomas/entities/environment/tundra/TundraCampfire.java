package deco2800.thomas.entities.environment.tundra;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class TundraCampfire extends StaticEntity {
	public static final String ENTITY_ID_STRING = "TundraCampfire";

	private static final String TEXTURE = "tundra-campfire";
	private static final boolean OBSTRUCTED = true;

	private TundraCampfire() {
		this.setObjectName(ENTITY_ID_STRING);
	}

	public TundraCampfire(Tile tile) {
		super(tile, RenderConstants.TUNDRA_CAMPFIRE, TEXTURE, OBSTRUCTED);
		this.setObjectName(ENTITY_ID_STRING);
	}
}
