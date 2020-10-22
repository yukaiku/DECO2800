package deco2800.thomas.entities.items;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.swamp.SwampWorld;

import java.util.Random;

public class ItemDropTable {
    private String name;
    private AbstractWorld currentWorld;
    private PlayerPeon player;

    public ItemDropTable(String string, AbstractWorld world, PlayerPeon player){
        this.name = string;
        this.currentWorld = world;
        this.player = player;
    }


    public static void dropItemForEnemyType(Tile tile, EnemyPeon enemy, PlayerPeon player, AbstractWorld world){
        int rng = generateRNG();
        switch (enemy.getObjectName()){
            case "Swamp Orc":
                if (rng <= 20) {
                    Item item = new HealthPotionSmall(tile, false, player, "swamp");
                    world.addEntity(item);
                    ((SwampWorld) world).addDialogue(item.getDisplay());
                } else if (rng > 20 && rng <= 25){
                    Item item = new IronArmour(tile, false, player, "swamp");
                    world.addEntity(item);
                    ((SwampWorld) world).addDialogue(item.getDisplay());
                } else if (rng > 25 && rng <= 30){
                    Item item = new Treasure(tile, false, player, "swamp");
                    world.addEntity(item);
                    ((SwampWorld) world).addDialogue(item.getDisplay());
                }
                break;
            case "Swamp Goblin":
                if (rng <= 10) {
                    HealthPotionSmall item = new HealthPotionSmall(tile, false, player, "swamp");
                    world.addEntity(item);
                    ((SwampWorld) world).addDialogue(item.getDisplay());
                    System.out.println(item.getItemName());
                }
                break;
        }
    }

    public static int generateRNG(){
        return new Random().nextInt(100-0) + 1;
    }
}
