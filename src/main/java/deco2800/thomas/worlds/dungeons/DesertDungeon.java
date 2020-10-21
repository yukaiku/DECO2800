package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.SoundManager;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonDialog;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonOpeningDialog;

/**
 * A dungeon for the desert world, where a player must use the
 * environment to kill an otherwise immune enemy.
 */
public class DesertDungeon extends DesertWorld {

    // the save file location to load this dungeon
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/desert/desert_dungeon_map.json";

    /**
     * Creates a Desert Dungeon with size 50x50.
     */
    public DesertDungeon() {
        this(50, 50);
    }

    /**
     * Creates a Desert Dungeon with specified width and height.
     *
     * @param width The specified width.
     * @param height The specified height.
     */
    public DesertDungeon(int width, int height){
        super(width, height);
    }

    @Override
    public String getType() {
        return "DesertDungeon";
    }

    @Override
    protected void generateTiles() {
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-2f, -11f, 0.15f));
        addEntity(this.getPlayerEntity());

        // spawns the immune orc enemy
        GameManager.get().addManager(new EnemyManager(this));
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.spawnSpecialEnemy("immuneOrc", -2f, 8f);

        // sets up all types of dialogs
        Stage stage = GameManager.get().getStage();
        DesertDungeonDialog.setup(stage);

        // displays the opening dialog to help kill the enemy
        DesertDungeonOpeningDialog announcementDialog = new DesertDungeonOpeningDialog("The Desert Dungeon");
        announcementDialog.show();

        // Start ambience
        GameManager.getManagerFromInstance(SoundManager.class).playAmbience("desertAmbience");
    }
}
