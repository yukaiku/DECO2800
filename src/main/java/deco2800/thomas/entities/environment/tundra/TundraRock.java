package deco2800.thomas.entities.environment.tundra;

import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.Random;

public class TundraRock extends StaticEntity {
	public static final String ENTITY_ID_STRING = "TundraRock";

	private static final boolean OBSTRUCTED = true;
	private static final String[] textures = {
			"tundra-rock-1",
			"tundra-rock-2",
			"tundra-rock-3",
			"tundra-rock-4",
			"tundra-rock-5"
	};

	private TundraRock() {
	}

	public TundraRock(Tile tile) {
		this(tile, new Random().nextInt(TundraRock.textures.length));
	}

	public TundraRock(Tile tile, int type) {
		super(tile, RenderConstants.TUNDRA_ROCK, textures[type], OBSTRUCTED);
		this.setObjectName(ENTITY_ID_STRING);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TundraRock{");
		sb.append("children=").append(children);
		sb.append(", position=").append(position);
		sb.append('}');
		sb.append("\n");
		return sb.toString();
	}
}
