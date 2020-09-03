package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolcanoWorld extends AbstractWorld {
    private final Logger logger = LoggerFactory.getLogger(VolcanoWorld.class);

    boolean notGenerated = true;

    public VolcanoWorld() {
        super();
    }
    public VolcanoWorld(int width, int height) {
        super(width, height);
    }


    /**
     * Generates the tiles for the world
     */
    protected void generateTiles() {
        Random random = new Random();
        //Volcano Bottom half
        int tileCount = 0;
        for (int q = -width; q < width; q++) {
            for (int r = -height; r < height/5; r++) {
                tileCount += 1;
                int elevation = random.nextInt(2) + 1;
                String type = "Volcano_";
                type += elevation;
                tiles.add(new VolcanoDefaultTile(type, q, Tile));
            }

        }
        //Volcano Lava tiles
        for (int q = -width+width/8; q < width-width/8+1; q++) {
            for (int r = height / 5; r < height; r++) {
                tileCount += 1;
                int elevation = random.nextInt(4) + 5;
                String type = "Volcano_";
                type += elevation;
                tiles.add(new VolcanoDefaultTile(type, q, r));
            }
        }
        //Volcano Left Side
        for (int q = -width; q < -width+width/8; q++) {
            for (int r = height / 5; r < height; r++) {
                tileCount += 1;
                int elevation = random.nextInt(2) + 1;
                String type = "Volcano_";
                type += elevation;
                tiles.add(new VolcanoDefaultTile(type, q, r));
            }
        }//Volcano Right Side
        for (int q = width - 1; q > width-width/8; q--) {
            for (int r = height / 5; r < height; r++) {
                tileCount += 1;
                int elevation = random.nextInt(2) + 1;
                String type = "Volcano_";
                type += elevation;
                tiles.add(new VolcanoDefaultTile(type, q, r));
            }
        }//Volcano Front
        for (int q = -width; q < width; q++) {
            for (int r = height/5-height/8; r < height/5; r++) {
                tileCount += 1;
                int elevation = random.nextInt(2) + 1;
                String type = "Volcano_";
                type += elevation;
                tiles.add(new VolcanoDefaultTile(type, q, r));
            }
        }

        // Create the entities in the game
        this.setPlayerEntity(new PlayerPeon(10f, 5f, 0.1f));
        addEntity(this.getPlayerEntity());

    }

    public void onTick(long i){
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createStaticEntities();
            notGenerated = false;
        }
    }

    public void createStaticEntities(){
        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTiles().size();

        //addBones();
        //addGraveYard();
        //addRuins();
        addRandomLavaPools();

        // 100 randomly-placed tiles and their corresponding neighbors have "swamp_1" texture
        for (int i = 0; i < 20; i++) {
            Tile t = this.getTile(random.nextInt(10));
            System.out.println(this.getTiles().toString());
            t.setTexture("Volcano_3");
            for (Tile neighbor : t.getNeighbours().values()) {
                neighbor.setTexture("Volcano_1");
            }
        }
    }

    public void addRandomLavaPools(){

    }


    public void addRuins(float col, float row){

    }

    public void addLavaPool(float col, float row){

    }

    public void addBones(float col, float row){

    }

    public void addGraveYard(float col, float row){
       /* List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(new SquareVector(0, 0), "buildingA", true));
        // Bottom
        parts.add(new Part(new SquareVector(-1, -1), "fenceE-W", true));
        parts.add(new Part(new SquareVector(0, -1), "fenceE-W", true));
        parts.add(new Part(new SquareVector(1, -1), "fenceE-W", true));

        StaticEntity building = new StaticEntity(col, row, 1, parts);
        entities.add(building);
        return building; */
    }

    public void volcanoEvent(){

    }


}
