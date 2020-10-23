package deco2800.thomas.entities.environment.volcano;

import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.entities.StaticEntity;

import java.util.List;

/**
 * A VolcanoGraveYard entity that spawns in the VolcanoWorld.
 * Entity contains collectible items contribute towards sidequests.
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoGraveYard extends StaticEntity {
    private static final String ENTITY_ID_STRING = "VolcanoGraveYard";

    /**
     * A default constructor for the VolcanoGraveYard class that inherits from
     * StaticEntity with default textures & no tile given.
     *
     */
    public VolcanoGraveYard() {
        super();
    }

    /**
     * A constructor for the VolcanoGraveYard class that inherits from Static
     * Entity with a given position via a column float & row float as well as a respective
     * list of the total parts connected to this entity.
     *
     * @param col - The specified column coordinate of the VolcanoGraveYard.
     * @param row - The specified row coordinate of the VolcanoGraveYard.
     * @param entityParts - List of part instances that correspond to the VolcanoGraveYard.
     */
    public VolcanoGraveYard(float col, float row, List<Part> entityParts) {
        super(col, row, RenderConstants.VOLCANO_GRAVEYARD, entityParts);
    }
}
