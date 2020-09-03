package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.managers.DatabaseManager;
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

        addBones();
        addGraveYard();
        addRuins();
        addRandomLavaPools();

        // 100 randomly-placed tiles and their corresponding neighbors have "swamp_1" texture
        /*for (int i = 0; i < 20; i++) {
            Tile t = this.getTile(random.nextInt(10));
            System.out.println(this.getTiles().toString());
            t.setTexture("Volcano_3");
            for (Tile neighbor : t.getNeighbours().values()) {
                neighbor.setTexture("Volcano_1");
            }
        }*/
    }

    public void addRandomLavaPools(){

    }


    public void addRuins(){

    }

    public void addLavaPool(){

    }

    public void addBones(){

    }

    public void addGraveYard(){
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
