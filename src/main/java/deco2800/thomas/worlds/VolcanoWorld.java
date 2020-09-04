package deco2800.thomas.worlds;

import deco2800.thomas.entities.*;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolcanoWorld extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);
    public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/volcano/VolcanoZone.json";

    boolean notGenerated = true;

    public VolcanoWorld() {
        super();
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-3f, -24f, 0.1f));
        addEntity(this.getPlayerEntity());
    }

    public VolcanoWorld(int width, int height) {
        super(width, height);
        DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
        this.setPlayerEntity(new PlayerPeon(-3f, -24f, 0.1f));
        addEntity(this.getPlayerEntity());
    }


    /**
     * Generates the tiles for the world
     */
    protected void generateTiles() {

    }

    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createStaticEntities();
            updateLavaTiles();
            for (Tile t : GameManager.get().getWorld().getTiles()) {
                System.out.println(t.getClass());
            }
            notGenerated = false;
        }
    }

    public void updateLavaTiles() {
        for (Tile t : GameManager.get().getWorld().getTiles()) {

            String tileTexture = t.getTextureName();
            int tileNumber = Integer.parseInt(tileTexture.split("_")[1]);
            if (tileNumber > 4) {
                int index = GameManager.get().getWorld().getTiles().indexOf(t);
                float row = t.getRow();
                float col = t.getCol();
                t = new VolcanoBurnTile(tileTexture, col, row, 5);
                GameManager.get().getWorld().getTiles().set(index, t);
            }

        }
    }

    public void createStaticEntities() {

        //Add Main Static entities
        entities.add(createGraveYard(7, -15));
        entities.add(createRuins(0, -4));
        entities.add(createDragonSkull(-23, 23));
        //entities.addBones();
        entities.add(createVolcanoOrb(21, 20));

        //For objects that are added randomly & require more specific addition
        //entities, they're methodology will folllow add()
        addBoulders();


    }

        public void addBoulders() {
        //Add random boulders on tiles that aren't Lava
            Random random = new Random();
            int tileCount = GameManager.get().getWorld().getTiles().size();

            for (int i = 0; i < 30; i++) {

                //Get respective volcano tile (5 <= Lava tiles Index <= 8
                Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
                String tileTexture = t.getTextureName();
                int tileNumber = Integer.parseInt(tileTexture.split("_")[1]);

                if (t != null && tileNumber < 5 && tileNumber > 1) {
                    entities.add(new Rock(t, true));
                } else {
                    i--;
                }
            }
        }

        public Orb createVolcanoOrb(float col, float row) {
            List<Part> parts = new ArrayList<Part>();
            parts.add(new Part(new SquareVector(0, 0), "Orb_Volcano", false));
            Orb VolcanoOrb = new Orb(col, row, 1, parts);
            return VolcanoOrb;
        }

        public VolcanoDragonSkull createDragonSkull(float col, float row) {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(new SquareVector(0, 0), "tree", true));
        VolcanoDragonSkull dragonSkull = new VolcanoDragonSkull(col, row, parts);
        return dragonSkull;
        }

        public VolcanoRuins createRuins(float col, float row) {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(new SquareVector(12, -3), "tree", true));
        VolcanoRuins graveYard = new VolcanoRuins(col, row, parts);
        return graveYard;
        }

        public VolcanoGraveYard createGraveYard(float col, float row) {
        List<Part> parts = new ArrayList<Part>();
        //Top left
        parts.add(new Part(new SquareVector(1, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(2, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(3,  0), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(0, 0), "fenceS-E", true));
        //Top right
        parts.add(new Part(new SquareVector(9, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(10, 0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(11,  0), "fenceE-W", true));
        parts.add(new Part(new SquareVector(12, 0), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(13, 0), "fenceS-W", true));

        // Bottom Left
        parts.add(new Part(new SquareVector(1, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(2, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(3,  -7), "fenceE-W", true));
        //Corner
        parts.add(new Part(new SquareVector(0, -7), "fenceN-E", true));

        //Bottom right
        parts.add(new Part(new SquareVector(9, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(10, -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(11,  -7), "fenceE-W", true));
        parts.add(new Part(new SquareVector(12, -7), "fenceE-W", true));
        //Bottom right corner
        parts.add(new Part(new SquareVector(13, -7), "fenceN-W", true));

        //Verticle sides Left
        parts.add(new Part(new SquareVector(0, -6), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -5), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0,  -2), "fenceN-S", true));
        parts.add(new Part(new SquareVector(0, -1), "fenceN-S", true));
        //Verticle sides Right
        parts.add(new Part(new SquareVector(13, -6), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13, -5), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13,  -2), "fenceN-S", true));
        parts.add(new Part(new SquareVector(13, -1), "fenceN-S", true));

        //Graves & Bones
        parts.add(new Part(new SquareVector(1, -1), "tree", true));
        parts.add(new Part(new SquareVector(12, -1), "tree", true));
        parts.add(new Part(new SquareVector(1,  -6), "tree", true));
        parts.add(new Part(new SquareVector(12, -6), "tree", true));
        VolcanoGraveYard graveYard = new VolcanoGraveYard(col, row, parts);
        return graveYard;
        }

        public void volcanoEvent () {

        }


}
