package deco2800.thomas.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.util.SquareVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.Tree;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.managers.GameManager;

@SuppressWarnings("unused")
public class SwampWorld extends AbstractWorld {
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/swamp-game-map.json";

    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);

    boolean notGenerated = true;

    public SwampWorld() {
        this(AbstractWorld.DEFAULT_WIDTH, AbstractWorld.DEFAULT_HEIGHT);
    }

    public SwampWorld(int width, int height) {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
    }

    @Override
    protected void generateTiles() {
    }

    public void createStaticEntities() {
        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTiles().size();

        // 100 Rocks randomly placed
        for (int i = 0; i < 100; i++) {
            Tile t = this.getTile(random.nextInt(tileCount));
            if (t != null) {
                entities.add(new Rock(t, true));
            }
        }

        // 50 Trees randomly placed
        for (int i = 0; i < 50; i++) {
            Tile t = this.getTile(random.nextInt(tileCount));
            if (t != null) {
                entities.add(new Tree(t, true));
            }
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }
    }
}
