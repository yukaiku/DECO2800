package deco2800.thomas.entities.environment.desert;

import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.worlds.AbstractWorld;

public class DesertDungeonPortal extends Portal {
    public static final String ENTITY_NAME = "DesertDungeonPortal";

    public DesertDungeonPortal(AbstractWorld world, float col, float row) {
        super(world.getTile(col, row), false, "portal", ENTITY_NAME);
    }
}
