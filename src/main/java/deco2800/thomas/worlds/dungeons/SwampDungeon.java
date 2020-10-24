package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.SwampDungeonNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.ArrayList;
import java.util.List;

public class SwampDungeon extends AbstractWorld {
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/swamp_dungeon/swamp_dungeon_map.json";

    private final SquareVector topTrap;

    private final SquareVector midTrap;

    private final SquareVector correctTile;

    private final List<AbstractDialogBox> allSwampDungeonDialogues;

    public SwampDungeon() {
        this(12, 7);
    }

    public SwampDungeon(int width, int height) {
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.generateTiles();
        this.allSwampDungeonDialogues = new ArrayList<>();

        // Create the player entity
        this.setPlayerEntity(new PlayerPeon(0f, 0f, 0.15f));
        addEntity(this.getPlayerEntity());

        // Provide enemies
        EnemyManager enemyManager = new EnemyManager(this);
        GameManager.get().addManager(enemyManager);

        // Creates swamp NPCs
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        SwampDungeonNPC npc1 = new SwampDungeonNPC("npc_swamp_dungeon_blue", new SquareVector(-9, 5),"npc_swamp_dungeon_blue");
        SwampDungeonNPC npc2 = new SwampDungeonNPC("npc_swamp_dungeon_green", new SquareVector(-7, 5),"npc_swamp_dungeon_green");
        SwampDungeonNPC npc3 = new SwampDungeonNPC("npc_swamp_dungeon_orange", new SquareVector(-5, 5),"npc_swamp_dungeon_orange");
        SwampDungeonNPC npc4 = new SwampDungeonNPC("npc_swamp_dungeon_red", new SquareVector(-3, 5),"npc_swamp_dungeon_red");
        SwampDungeonNPC npc5 = new SwampDungeonNPC("npc_swamp_dungeon_white", new SquareVector(-1, 5),"npc_swamp_dungeon_white");
        SwampDungeonNPC npc6 = new SwampDungeonNPC("npc_swamp_dungeon_yellow", new SquareVector(1, 5),"npc_swamp_dungeon_yellow");
        npnSpawns.add(npc1);
        npnSpawns.add(npc2);
        npnSpawns.add(npc3);
        npnSpawns.add(npc4);
        npnSpawns.add(npc5);
        npnSpawns.add(npc6);
        this.allSwampDungeonDialogues.add(npc1.getBox());
        this.allSwampDungeonDialogues.add(npc2.getBox());
        this.allSwampDungeonDialogues.add(npc3.getBox());
        this.allSwampDungeonDialogues.add(npc4.getBox());
        this.allSwampDungeonDialogues.add(npc5.getBox());
        this.allSwampDungeonDialogues.add(npc6.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, (PlayerPeon) this.playerEntity,
                npnSpawns);
        GameManager.get().addManager(npcManager);

        // Creates dialogue manager
        DialogManager dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allSwampDungeonDialogues);
        GameManager.get().addManager(dialog);

        topTrap = new SquareVector(7, 1);
        midTrap = new SquareVector(7, -1);
        correctTile = new SquareVector(7, -3);
        setUpPlatforms();
    }

    @Override
    protected void generateTiles() {
    }

    public List<AbstractDialogBox> getAllSwampDungeonDialogues() {
        return allSwampDungeonDialogues;
    }

    public SquareVector getTopTrap() {
        return topTrap;
    }

    public SquareVector getMidTrap() {
        return midTrap;
    }

    public SquareVector getCorrectTile() {
        return correctTile;
    }

    private void setUpPlatforms() {
        Tile topTrapTile = this.getTile(topTrap);
        topTrapTile.setTrapTile(true);
        topTrapTile.setTrapActivated(false);

        Tile midTrapTile = this.getTile(midTrap);
        midTrapTile.setTrapTile(true);
        midTrapTile.setTrapActivated(false);

        Tile rewardTile = this.getTile(correctTile);
        rewardTile.setRewardTile(true);
        rewardTile.setRewardActivated(false);
    }

    private void spawnEnemies() {
        EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
        enemyManager.spawnSpecialEnemy("swampGoblin", 1, 2);
        enemyManager.spawnSpecialEnemy("swampGoblin", -1, 1);
        enemyManager.spawnSpecialEnemy("swampGoblin", -2, -1);
        enemyManager.spawnSpecialEnemy("swampGoblin", -1, -3);
        enemyManager.spawnSpecialEnemy("swampGoblin", 1, -5);
        enemyManager.spawnSpecialEnemy("swampGoblin", 8, 2);
        enemyManager.spawnSpecialEnemy("swampGoblin", 10, 1);
        enemyManager.spawnSpecialEnemy("swampGoblin", 11, -1);
        enemyManager.spawnSpecialEnemy("swampGoblin", 10, -3);
        enemyManager.spawnSpecialEnemy("swampGoblin", 8, -5);

        for (int i = -6; i < 4; i++) {
            enemyManager.spawnSpecialEnemy("swampOrc", -3, i);
        }
    }

    @Override
    public void activateTrapTile(Tile tile) {
        tile.setTexture("dungeon-light-black");
        spawnEnemies();
    }

    private void spawnExitPortal() {
        Tile portalTile = getTile(-9, -1);
        entities.add(new Portal(portalTile, false, "swamp_portal", "ExitPortal"));
    }

    @Override
    public void activateRewardTile(Tile tile) {
        tile.setTexture("dungeon-light-black");
        GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.HEAL);
        spawnExitPortal();
    }

    /**
     * Adds a dialog box to this world
     * @param box: an AbstractDialogBox instance
     */
    public void addDialogue(AbstractDialogBox box){
        this.allSwampDungeonDialogues.add(box);
    }

    @Override
    public String getType() {
        return "SwampDungeon";
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }
    }

    @Override
    public List<AbstractDialogBox> returnAllDialogues() {
        return allSwampDungeonDialogues;
    }
}
