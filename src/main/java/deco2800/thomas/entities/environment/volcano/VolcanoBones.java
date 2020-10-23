package deco2800.thomas.entities.environment.volcano;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.worlds.Tile;

import java.util.List;


/**
 * A Bones entity that spawns in the VolcanoWorld.
 *
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoBones extends StaticEntity{
    private static final String ENTITY_ID_STRING = "VolcanoBones";

    /**
     * A default constructor for the VolcanoBones class that inherits from
     * StaticEntity with default textures & no tile given.
     *
     */
    public VolcanoBones() {
        super();
    }

    /**
     * A constructor for the VolcanoBones class that inherits from Static
     * Entity with given tile, texture & obstructed values.
     *
     * @param tile A Tile instance that resides within the VolcanoWorld.
     * @param texture The texture pattern for the Static entity
     * @param obstructed A boolean declaring whether or not agent entities
     *                   can pass through this static entity.
     */
    public VolcanoBones(Tile tile, String texture, boolean obstructed) {
        super(tile, RenderConstants.VOLCANO_BONES, texture, obstructed);
    }

    /**
     * A constructor for the VolcanoBones class that inherits from Static
     * Entity with a given position via a column float & row float as well as a respective
     * list of the total parts connected to this entity.
     *
     * @param col - The specified column coordinate of the VolcanoBones.
     * @param row - The specified row coordinate of the VolcanoBones.
     * @param entityParts - List of part instances that correspond to the VolcanoBones.
     */
    public VolcanoBones(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_BONES, entityParts);
    }
}
