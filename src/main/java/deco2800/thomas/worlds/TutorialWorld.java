package deco2800.thomas.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import deco2800.thomas.util.SquareVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.StaticEntity;
//import deco2800.thomas.entities.Tree;
//import deco2800.thomas.entities.PlayerPeon;
//import deco2800.thomas.entities.Rock;
import deco2800.thomas.managers.GameManager;

public class TutorialWorld extends AbstractWorld{

    static final int TUTORIAL_WORLD_WIDTH = 10; // Height and width vars for the map size; constrains tile gen
    static final int TUTORIAL_WORLD_HEIGHT = 7; // Note the map will double these numbers (bounds are +/- these limits)

    public TutorialWorld() {
        super(TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT);
    }

    @Override
    protected void generateTiles() {
        for (int col = -TUTORIAL_WORLD_WIDTH; col < TUTORIAL_WORLD_WIDTH; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT; row < TUTORIAL_WORLD_HEIGHT; row++) {
                String type = "grass_1";
                tiles.add(new Tile(type, col, row));
            }
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        //addTree(0f, 0f);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }
}
