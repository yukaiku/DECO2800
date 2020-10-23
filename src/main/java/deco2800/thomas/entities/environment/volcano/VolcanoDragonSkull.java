package deco2800.thomas.entities.environment.volcano;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;

/**
 * A DragonSkull entity that spawns in the VolcanoWorld.
 *
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoDragonSkull extends StaticEntity {
    private static final String ENTITY_ID_STRING = "VolcanoDragonSkull";

    /**
     * A default constructor for the VolcanoDragonSkull class that inherits from
     * StaticEntity with default textures & no tile given.
     *
     */
    public VolcanoDragonSkull() {
        super();
    }

    /**
     * A constructor for the VolcanoDragonSkull class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the VolcanoWorld.
     * @param texture The texture pattern for the Static entity
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public VolcanoDragonSkull(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_DRAGON_SKULL, texture, obstructed);
    }

    /**
     * A constructor for the VolcanoDragonSkull class that inherits from Static
     * Entity with a given position via a column float & row float as well as a respective
     * list of the total parts connected to this entity.
     *
     * @param col - The specified column coordinate of the VolcanoDragonSkull.
     * @param row - The specified row coordinate of the VolcanoDragonSkull.
     * @param entityParts - List of part instances that correspond to the VolcanoDragonSkull.
     */
    public VolcanoDragonSkull(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_DRAGON_SKULL, entityParts);
    }
}
