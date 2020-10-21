package deco2800.thomas.worlds.volcano;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.environment.volcano.VolcanoLavaPool;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.WorldEvent;

import java.util.Random;

/**
 * A volcano event which updates tile types & textures for LavaPool's
 * On the VolcanoMap when the WorldEvent is triggered & removed.
 *
 * @author Arthur Mitchell (Gitlab: @ArthurM99115)
 */
public class VolcanoEvent extends WorldEvent {

    /**
     * Primary constructor for the VolcanoEvent.
     * @param world - An instance of the world the event will be modifying.
     */
    public VolcanoEvent(VolcanoWorld world) {
        super(world);
    }

    /**
     * Trigger method for the VolcanoWorld that modifies all tiles in the LavaPool,
     * changing their types to BurnTiles & textures to one of the lava textures in the
     * texture map (Volcano Textures > 5).
     */
    public void triggerEvent() {
        Random random = new Random();
        for (AbstractEntity entity :  getWorld().getEntities()) {
            if (entity instanceof VolcanoLavaPool) {
                for (SquareVector vector : ((VolcanoLavaPool) entity).getChildrenPositions()) {
                    int textureNumber = random.nextInt(4) + 5;
                    getWorld().getTile(vector).setType("BurnTile");
                    getWorld().getTile(vector).setTexture("Volcano_" + textureNumber);
                    getWorld().getTile(vector).setStatusEffect(true);
                }
            }
        }

    }

    /**
     * A method to return all of the tiles in each of the VolcanoWorld's
     * LavaPool back to non-burning, regular tile types & volcano
     * mountain textures (Volcano Textures < 5).
     */
    public void removeEvent() {
        Random random = new Random();
        for (AbstractEntity entity :  getWorld().getEntities()) {
            if (entity instanceof VolcanoLavaPool) {
                for (SquareVector vector : ((VolcanoLavaPool) entity).getChildrenPositions()) {
                    int textureNumber = random.nextInt(4) + 1;
                    getWorld().getTile(vector).setType("Tile");
                    getWorld().getTile(vector).setTexture("Volcano_" + textureNumber);
                    getWorld().getTile(vector).setStatusEffect(false);
                }
            }
        }
    }


}
