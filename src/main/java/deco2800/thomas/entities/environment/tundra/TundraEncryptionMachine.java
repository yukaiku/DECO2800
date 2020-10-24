package deco2800.thomas.entities.environment.tundra;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

public class TundraEncryptionMachine extends StaticEntity {
	public static final String ENTITY_ID_STRING = "TundraEncryptionMachine";

	private static final String TEXTURE = "tundra-encryption-machine";
	private static final boolean OBSTRUCTED = false;

	private TundraEncryptionMachine() {
		this.setObjectName(ENTITY_ID_STRING);
	}

	public TundraEncryptionMachine(Tile tile) {
		super(tile, RenderConstants.TUNDRA_ENCRYPTION_MACHINE, TEXTURE, OBSTRUCTED);
		this.setObjectName(ENTITY_ID_STRING);
	}
}
