package deco2800.thomas.entities.environment.tundra;

import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.worlds.AbstractWorld;

public class TundraDungeonPortal extends Portal {
	public static final String ENTITY_NAME = "TundraDungeonPortal";

	public TundraDungeonPortal(AbstractWorld world, float col, float row) {
		super(world.getTile(col, row), false, "portal", ENTITY_NAME);
	}
}
