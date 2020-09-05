package deco2800.thomas.worlds;

import java.util.Random;

import deco2800.thomas.entities.*;
import deco2800.thomas.entities.SwampDeadTree;
import deco2800.thomas.entities.SwampFallenTree;
import deco2800.thomas.entities.SwampPond;
import deco2800.thomas.entities.SwampTreeLog;
import deco2800.thomas.entities.SwampTreeStub;
import deco2800.thomas.managers.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.managers.GameManager;

@SuppressWarnings("unused")
public class SwampWorld extends AbstractWorld {
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/swamp-game-map.json";

    private final Logger logger = LoggerFactory.getLogger(TestWorld.class);

    boolean notGenerated = true;

    public SwampWorld() {
        this(AbstractWorld.DEFAULT_WIDTH, AbstractWorld.DEFAULT_HEIGHT);
        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.1f));
        addEntity(this.getPlayerEntity());
    }

    public SwampWorld(int width, int height) {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(5, 10, 0.1f));
        this.generateTileMap();
        this.generateTileIndices();
        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.1f));
        addEntity(this.getPlayerEntity());
    }

    @Override
    protected void generateTiles() {
    }

    public void generateDeadTree() {
        // South Forest
        entities.add(new SwampDeadTree(this.getTile(-5, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-6, -23), true));
        entities.add(new SwampDeadTree(this.getTile(-4, -22), true));
        entities.add(new SwampDeadTree(this.getTile(-3, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-2, -22), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -24), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -22), true));

        // North Forest
        entities.add(new SwampDeadTree(this.getTile(20, 15), true));
        entities.add(new SwampDeadTree(this.getTile(22, 15), true));
        entities.add(new SwampDeadTree(this.getTile(22, 14), true));
        entities.add(new SwampDeadTree(this.getTile(-1, -22), true));

        // Mid Forest
        entities.add(new SwampDeadTree(this.getTile(13, -6), true));
        entities.add(new SwampDeadTree(this.getTile(14, -7), true));
        entities.add(new SwampDeadTree(this.getTile(15, -5), true));
        entities.add(new SwampDeadTree(this.getTile(15, -8), true));
        entities.add(new SwampDeadTree(this.getTile(16, -6), true));
        entities.add(new SwampDeadTree(this.getTile(12, -6), true));
        entities.add(new SwampDeadTree(this.getTile(12, -9), true));
        entities.add(new SwampDeadTree(this.getTile(13, -10), true));
        entities.add(new SwampDeadTree(this.getTile(14, -9), true));

        // Near the Orb
        entities.add(new SwampDeadTree(this.getTile(17, -24), true));
        entities.add(new SwampDeadTree(this.getTile(16, -23), true));
        entities.add(new SwampDeadTree(this.getTile(18, -22), true));
        entities.add(new SwampDeadTree(this.getTile(15, -24), true));
        entities.add(new SwampDeadTree(this.getTile(18, -24), true));
    }

    public void createTreeStub() {
        // South Forest
        entities.add(new SwampTreeStub(this.getTile(-5, -22), true));
        entities.add(new SwampTreeStub(this.getTile(0, -23), true));
        entities.add(new SwampTreeStub(this.getTile(1, -24), true));

        // North Forest
        entities.add(new SwampTreeStub(this.getTile(21, 13), true));
        entities.add(new SwampTreeStub(this.getTile(24, 15), true));
        entities.add(new SwampTreeStub(this.getTile(21, 16), true));

        // Mid Forest
        entities.add(new SwampTreeStub(this.getTile(14, -5), true));
        entities.add(new SwampTreeStub(this.getTile(12, -7), true));
        entities.add(new SwampTreeStub(this.getTile(16, -7), true));
        entities.add(new SwampTreeStub(this.getTile(13, -8), true));
        entities.add(new SwampTreeStub(this.getTile(17, -5), true));
        entities.add(new SwampTreeStub(this.getTile(17, -8), true));
        entities.add(new SwampTreeStub(this.getTile(13, -4), true));
    }

    public void createPond() {
        // Near the Orb (Above)
        entities.add(new SwampPond(this.getTile(20, -19), true));
        entities.add(new SwampPond(this.getTile(21, -19), true));

        // Near the Orb (Below)
        entities.add(new SwampPond(this.getTile(16, -24), true));
        entities.add(new SwampPond(this.getTile(17, -25), true));
        entities.add(new SwampPond(this.getTile(16, -25), true));
        entities.add(new SwampPond(this.getTile(19, -23), true));
        entities.add(new SwampPond(this.getTile(19, -24), true));

        // North Forest
        entities.add(new SwampPond(this.getTile(21, 14), true));
    }

    public void createTreeLog() {
        // Near the Orb
        entities.add(new SwampTreeLog(this.getTile(19, -19), true));
        entities.add(new SwampTreeLog(this.getTile(20, -18), true));

        // North Forest
        entities.add(new SwampTreeLog(this.getTile(20, 13), true));
        entities.add(new SwampTreeLog(this.getTile(19, 14), true));

        // Mid Forest
        entities.add(new SwampTreeLog(this.getTile(12, -4), true));
        entities.add(new SwampTreeLog(this.getTile(11, -5), true));
        entities.add(new SwampTreeLog(this.getTile(11, -6), true));
    }

    public void createFallenTree() {
        // Near the Orb
        entities.add(new SwampFallenTree((this.getTile(22, -19)), true));
        entities.add(new SwampFallenTree((this.getTile(22, -20)), true));

        // South Forest
        entities.add(new SwampFallenTree((this.getTile(-4, -23)), true));
        entities.add(new SwampFallenTree((this.getTile(-2, -23)), true));

        // North Forst
        entities.add(new SwampFallenTree((this.getTile(24, 14)), true));
        entities.add(new SwampFallenTree((this.getTile(23, 13)), true));
    }

    public void generateStaticEntities() {
//        Random random = new Random();
//        int tileCount = GameManager.get().getWorld().getTiles().size();

        // 100 Rocks randomly placed
//        for (int i = 0; i < 100; i++) {
//            Tile t = this.getTile(random.nextInt(tileCount));
//            if (t != null) {
//                entities.add(new Rock(t, true));
//            }
//        }
        this.setOrbEntity(new Orb(this.getTile(23, -24), false));

        // 50 Trees randomly placed
//        for (int i = 0; i < 50; i++) {
//            Tile t = this.getTile(random.nextInt(tileCount));
//            if (t != null) {
//                entities.add(new Tree(t, true));
//            }
//        }

        this.createPond();
        this.generateDeadTree();
        this.createTreeStub();
        this.createFallenTree();
        this.createTreeLog();
    }

    @Override
    public void onTick(long i) {
        if (notGenerated) {
            generateStaticEntities();
            notGenerated = false;
        }

        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }
}
