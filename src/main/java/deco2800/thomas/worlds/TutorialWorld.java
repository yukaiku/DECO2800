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
import deco2800.thomas.entities.Environment.Rock;
import deco2800.thomas.entities.Environment.Tree;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.entities.NPC.TutorialNPC;
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

    public void createBuildings() {
        PlayerPeon player = new PlayerPeon(-2f, -2f, 0.1f);
        addEntity(player);

        List<NonPlayablePeon> npnSpawns = new ArrayList<>();
        npnSpawns.add(new TutorialNPC("Master", new SquareVector(0, 2)));
        NonPlayablePeonManager npcManager = new NonPlayablePeonManager(this, player, npnSpawns);
        GameManager.get().addManager(npcManager);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createBuildings();

            notGenerated = false;
        }
    }
}
