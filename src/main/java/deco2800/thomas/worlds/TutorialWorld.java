package deco2800.thomas.worlds;

import java.util.ArrayList;
import java.util.List;

import deco2800.thomas.GameScreen;
import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.environment.tutorial.Barrel;
import deco2800.thomas.entities.environment.Portal;
import deco2800.thomas.entities.environment.tutorial.Stash;
import deco2800.thomas.entities.environment.tutorial.Target;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.entities.npc.TutorialNPC;
import deco2800.thomas.entities.environment.tutorial.*;
import deco2800.thomas.managers.DialogManager;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.NonPlayablePeonManager;

import deco2800.thomas.util.SquareVector;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.GameManager;

public class TutorialWorld extends AbstractWorld{

    DialogManager dialog;
    List<AbstractDialogBox> allDialogBoxes;
    boolean notGenerated = true;
    static final int TUTORIAL_WORLD_WIDTH = 10; // Height and width vars for the map size; constrains tile gen
    static final int TUTORIAL_WORLD_HEIGHT = 6; // Note the map will double these numbers (bounds are +/- these limits)
    private static final int PORTAL_COL = 0;
    private static final int PORTAL_ROW = -TUTORIAL_WORLD_HEIGHT;


    public TutorialWorld() {
        super(TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT);
    }

    @Override
    protected void generateTiles() {
        for (int col = -TUTORIAL_WORLD_WIDTH; col < TUTORIAL_WORLD_WIDTH; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT; row < TUTORIAL_WORLD_HEIGHT; row++) {
                String type = "stone-3";
                tiles.add(new Tile(type, col, row));
            }
        }

        for (int col = -TUTORIAL_WORLD_WIDTH+1; col < TUTORIAL_WORLD_WIDTH-1; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT+1; row < TUTORIAL_WORLD_HEIGHT-1; row++) {
                String type = "stone-2";
                tiles.add(new Tile(type, col, row));
            }
        }

        for (int col = -TUTORIAL_WORLD_WIDTH+2; col < TUTORIAL_WORLD_WIDTH-2; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT+2; row < TUTORIAL_WORLD_HEIGHT-2; row++) {
                String type = "stone-1";
                tiles.add(new Tile(type, col, row));
            }
        }

        PlayerPeon.buffDamageTotal = 0;
        PlayerPeon player = new PlayerPeon(-2f, -2f, 0.1f, 50);
            addEntity(player);
        this.setPlayerEntity(player);
        addEntity(this.getPlayerEntity());

        for (AbstractSkill s :((PlayerPeon) this.getPlayerEntity()).getWizardSkills()){
            s.setCooldownMax();
        }
        ((PlayerPeon) this.getPlayerEntity()).getMechSkill().setCooldownMax();


        // Spawn dummy
        EnemyManager enemyManager = new EnemyManager(this);
        enemyManager.spawnSpecialEnemy("dummy", 5, 0);
        GameManager.get().addManager(enemyManager);

        // Add NPC
        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        TutorialNPC NPC = new TutorialNPC("Master", new SquareVector(0,
                2),"tutorial_npc");
        npnSpawns.add(NPC);
        this.allDialogBoxes = new ArrayList<>();
        this.allDialogBoxes.add(NPC.getBox());
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, player, npnSpawns);
        GameManager.get().addManager(npcManager);

        //Dialog manager
        dialog = new DialogManager(this, (PlayerPeon) this.getPlayerEntity(),
                this.allDialogBoxes);
        GameManager.get().addManager(dialog);
    }

    public void generateEntities() {
        // Add stashes
        for (int i = -6; i < 6 + 1; i = i + 3) {
            Tile t = GameManager.get().getWorld().getTile(i, TUTORIAL_WORLD_HEIGHT - 1);
            if (t != null) {
                entities.add(new Stash(t, true));
            }
        }
        // Add targets
        for (int i = -6; i < 6 + 1; i = i + 2) {
            if (i == 0 || i == -2 || i == 2) {
                continue;
            }
            Tile t = GameManager.get().getWorld().getTile(i, -TUTORIAL_WORLD_HEIGHT);
            if (t != null) {
                entities.add(new Target(t, true));
            }
        }

        Tile t;
        // Add barrels
        t = GameManager.get().getWorld().getTile(TUTORIAL_WORLD_WIDTH - 1, TUTORIAL_WORLD_HEIGHT - 2);
        entities.add(new Barrel(t, true));

        t = GameManager.get().getWorld().getTile(TUTORIAL_WORLD_WIDTH - 1, -TUTORIAL_WORLD_HEIGHT + 1);
        entities.add(new Barrel(t, true));

        t = GameManager.get().getWorld().getTile(-TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT - 2);
        entities.add(new Barrel(t, true));

        t = GameManager.get().getWorld().getTile(-TUTORIAL_WORLD_WIDTH, -TUTORIAL_WORLD_HEIGHT + 1);
        entities.add(new Barrel(t, true));

        // Add chest
        t = GameManager.get().getWorld().getTile(-TUTORIAL_WORLD_WIDTH, 0);
        entities.add(new Chest(t, true));

        t = GameManager.get().getWorld().getTile(TUTORIAL_WORLD_WIDTH -1, 0);
        entities.add(new Chest(t, true));

        // Add portal
        t = GameManager.get().getWorld().getTile(PORTAL_COL, PORTAL_ROW);
        entities.add(new Portal(t, false));

        // Add message on how to leave tutorial (temporary)
        t = GameManager.get().getWorld().getTile(PORTAL_COL, PORTAL_ROW + 2);
        entities.add(new Notify(t, false));
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            generateEntities();
            notGenerated = false;
        }

        AgentEntity player = this.getPlayerEntity();
        // Check if the player is in the portal
        if (Math.abs(player.getCol() - PORTAL_COL) < 0.1 &
                Math.abs(player.getRow() - PORTAL_ROW) < 0.1) {
            // Remove the current map's enemy manager
            GameManager.get().removeManager(GameManager.get().getManager(EnemyManager.class));

            // Set new world
            GameManager gameManager = GameManager.get();
            gameManager.setWorld(GameScreen.gameType.NEW_GAME.method());


            GameManager.get().setNextWorld();
            // Keep $$ on world change.
            PlayerPeon.credit(((PlayerPeon) player).getWallet());
        }
    }
}
