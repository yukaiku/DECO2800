package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.volcano.VolcanoWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VolcanoDungeon extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/swamp/swamp-game-map.json";


    private boolean notGenerated = true;

    /**
     * Default Constructor for volcano world.
     */
    public VolcanoDungeon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public VolcanoDungeon(int width, int height){
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-0f, -24f, 0.15f));
        addEntity(this.getPlayerEntity());

        GameManager.get().addManager(new EnemyManager(this));

        Tile exitTile = this.getTile(-5, -20f);
        addEntity(new ExitPortal(exitTile, false, "portal", "ExitPortal"));
    }

    /**
     * Generates the tiles for the world
     */
    @Override
    protected void generateTiles() {

    }

    /**
     * Manages actions taking place for volcano world every tick.
     * @param i - tick count
     */
    public void onTick(long i) {
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            notGenerated = false;
        }
        super.onTick(i);
    }
}
