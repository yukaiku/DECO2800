package deco2800.thomas.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Environment.*;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.entities.NPC.TutorialNPC;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.NonPlayablePeonManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.Guideline;
import deco2800.thomas.renderers.OverlayRenderer;
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

    boolean notGenerated = true;
    static final int TUTORIAL_WORLD_WIDTH = 10; // Height and width vars for the map size; constrains tile gen
    static final int TUTORIAL_WORLD_HEIGHT = 6; // Note the map will double these numbers (bounds are +/- these limits)

    public TutorialWorld() {
        super(TUTORIAL_WORLD_WIDTH, TUTORIAL_WORLD_HEIGHT);
    }

    @Override
    protected void generateWorld() {

    }

    @Override
    protected void generateTiles() {
        for (int col = -TUTORIAL_WORLD_WIDTH; col < TUTORIAL_WORLD_WIDTH; col++) {
            for (int row = -TUTORIAL_WORLD_HEIGHT; row < TUTORIAL_WORLD_HEIGHT; row++) {
                String type = "stone_floor";
                tiles.add(new Tile(type, col, row));
            }
        }
        PlayerPeon player = new PlayerPeon(-2f, -2f, 0.1f);
//        addEntity(player);
        this.setPlayerEntity(player);
        addEntity(this.getPlayerEntity());

        EnemyManager enemyManager = new EnemyManager(this, (PlayerPeon) this.getPlayerEntity(), 5);
        GameManager.get().addManager(enemyManager);

        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        npnSpawns.add(new TutorialNPC("Master", new SquareVector(0, 2)));
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, player, npnSpawns);
        GameManager.get().addManager(npcManager);
    }

    public void createObjects() {
        for (int i = -4; i < 4 + 1; i = i + 2) {
            Tile t = GameManager.get().getWorld().getTile(i, TUTORIAL_WORLD_HEIGHT - 1);
            if (t != null) {
                entities.add(new Target(t, true));
            }
        }
        for (int i = -6; i < 6 + 1; i = i + 2) {
            if (i == 0) {
                continue;
            }
            Tile t = GameManager.get().getWorld().getTile(i, -TUTORIAL_WORLD_HEIGHT);
            if (t != null) {
                entities.add(new Target(t, true));
            }
        }

        Tile t = GameManager.get().getWorld().getTile(0, -TUTORIAL_WORLD_HEIGHT);
        entities.add(new Portal(t, true));

        for (int i = 0; i < 2 + 1; i = i + 2) {
            Tile b = GameManager.get().getWorld().getTile(9, -TUTORIAL_WORLD_HEIGHT + i);
            if (b != null) {
                entities.add(new Barrel(b, true));
            }
        }

        for (int i = -6; i < 6 + 1; i = i + 12) {
            Tile s = GameManager.get().getWorld().getTile(i, TUTORIAL_WORLD_HEIGHT - 1);
            if (s != null) {
                entities.add(new Stash(s, true));
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
            createObjects();
            notGenerated = false;
        }
    }
}
