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
import deco2800.thomas.entities.Tree;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.managers.GameManager;

@SuppressWarnings("unused")
public class SwampWorld extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);

    boolean notGenerated = true;

    public SwampWorld() {
        super();
    }

    public SwampWorld(int width, int height) {
        super(width, height);
    }

    @Override
    protected void generateTiles() {
        Random random = new Random();
        for (int x = -width; x < width; x++) {
            for (int y = -height; y < height; y++) {
                tiles.add(new SwampTile("swamp_4", x, y));
            }
        }

        // Middle area has "swamp_3" texture
        for (int y = -height + 5; y < height - 5; y++) {
            int count = random.nextInt(41) + 10;
            int startX = -count / 2;
            for (int i = 0; i < count; i++) {
                this.getTile(startX + i, y).setTexture("swamp_3");
            }
        }

        // South-East area has "swamp_2" texture
        for (int y = -height; y < -height + 15; y++) {
            int count = random.nextInt(16) + 10;
            int startX = (-count / 2) + 12; // Shift the y axis 12 to the right
            for (int i = 0; i < count; i++) {
                this.getTile(startX + i, y).setTexture("swamp_2");
            }
        }

        // North-West area has "swamp_2" texture
        for (int y = 0; y < height; y++) {
            int count = random.nextInt(16) + 15;
            int startX = (-count / 2) - 5; // Shift the y axis 5 to the left
            for (int i = 0; i < count; i++) {
                this.getTile(startX + i, y).setTexture("swamp_2");
            }
        }

        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.1f));
        addEntity(this.getPlayerEntity());
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

        // 100 randomly-placed tiles and their corresponding neighbors have "swamp_1" texture
        for (int i = 0; i < 20; i++) {
            Tile t = this.getTile(random.nextInt(width * height * 4));
            t.setTexture("swamp_1");
            for (Tile neighbor : t.getNeighbours().values()) {
                neighbor.setTexture("swamp_1");
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
