package deco2800.thomas.worlds.volcano;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Environment.volcano.VolcanoLavaPool;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.WorldEvent;

import java.util.Random;

public class VolcanoEvent extends WorldEvent {

    public VolcanoEvent(VolcanoWorld world) {
        super(world);
    }

    public void triggerEvent() {
        Random random = new Random();
        for (AbstractEntity entity :  getWorld().getEntities()) {
            if (entity instanceof VolcanoLavaPool) {
                for (SquareVector vector : ((VolcanoLavaPool) entity).getChildrenPositions()) {
                    int textureNumber = random.nextInt(4) + 5;
                    getWorld().getTile(vector).setType("BurnTile");
                    getWorld().getTile(vector).setTexture("Volcano_" + textureNumber);
                }
            }
        }

    }

    public void removeEvent() {
        Random random = new Random();
        for (AbstractEntity entity :  getWorld().getEntities()) {
            if (entity instanceof VolcanoLavaPool) {
                for (SquareVector vector : ((VolcanoLavaPool) entity).getChildrenPositions()) {
                    int textureNumber = random.nextInt(4) + 1;
                    getWorld().getTile(vector).setType("Tile");
                    getWorld().getTile(vector).setTexture("Volcano_" + textureNumber);
                }
            }
        }
    }


}
