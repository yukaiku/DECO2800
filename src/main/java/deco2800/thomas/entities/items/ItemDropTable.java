package deco2800.thomas.entities.items;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.dungeons.SwampDungeon;
import deco2800.thomas.worlds.dungeons.VolcanoDungeon;
import deco2800.thomas.worlds.swamp.SwampWorld;
import deco2800.thomas.worlds.tundra.TundraWorld;
import deco2800.thomas.worlds.volcano.VolcanoWorld;

import java.security.SecureRandom;

public class ItemDropTable {

    private ItemDropTable(){
        // Do nothing
    }

    /**
     * Drops an item based on the given enemy types, using cryptographically strong random numbers to determine
     * which item is dropped by the enemy.
     * @param tile The tile position where the enemy died at
     * @param enemy The enemy in the current world
     * @param player The player character
     * @param world The currently loaded world
     */
    public static void dropItemForEnemyType(Tile tile, EnemyPeon enemy, PlayerPeon player, AbstractWorld world) {
        // The randomly generated number
        int rng = generateRNG();

        // Style type for different worlds
        String styletypeSwamp = "swamp";
        String styletypeTundra = "tundra";
        String styletypeDesert = "desert";
        String styletypeVolcano = "volcano";

        // Cases for various types of enemies
        switch (world.getType()) {
            case "Swamp":
                switch (enemy.getObjectName()) {
                    case "Swamp Orc":
                        if (rng <= 20) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 20 && rng <= 25) {
                            Item item = new WoodenArmour(tile, false, player, styletypeSwamp, 100);
                            world.addEntity(item);
                            ((SwampWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 25 && rng <= 30) {
                            Item item = new Treasure(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampWorld) world).addDialogue(item.getDisplay());
                        }  else if (rng > 30 && rng <= 35){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((SwampWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Swamp Goblin":
                        if (rng <= 5) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                    default: //Do nothing
                }
                break;
            case "SwampDungeon":
                switch (enemy.getObjectName()) {
                    case "Swamp Orc":
                        if (rng <= 15) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 15 && rng <= 18) {
                            Item item = new WoodenArmour(tile, false, player, styletypeSwamp, 100);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 18 && rng <= 21) {
                            Item item = new Treasure(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        }  else if (rng > 21 && rng <= 24){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Swamp Goblin":
                        if (rng <= 3) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                        default: // Do Nothing
                }
                break;
            case "Tundra":
                switch (enemy.getObjectName()) {
                    case "Tundra Orc":
                        if (rng <= 20) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeTundra);
                            world.addEntity(item);
                            ((TundraWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 20 && rng <= 25) {
                            Item item = new WoodenArmour(tile, false, player, styletypeTundra, 100);
                            world.addEntity(item);
                            ((TundraWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 25 && rng <= 30) {
                            Item item = new Treasure(tile, false, player, styletypeTundra);
                            world.addEntity(item);
                            ((TundraWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 30 && rng <= 35){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((TundraWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Tundra Goblin":
                        if (rng <= 5) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeTundra);
                            world.addEntity(item);
                            ((TundraWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                        default: // Do nothing
                }
                break;
            case "Desert":
                switch (enemy.getObjectName()) {
                    case "Desert Orc":
                        if (rng <= 20) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((DesertWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 20 && rng <= 25) {
                            Item item = new WoodenArmour(tile, false, player, styletypeDesert, 100);
                            world.addEntity(item);
                            ((DesertWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 25 && rng <= 30) {
                            Item item = new Treasure(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((DesertWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 30 && rng <= 35){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((DesertWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Desert Goblin":
                        if (rng <= 5) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((DesertWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                        default: // Do nothing
                }
                break;
            case "Volcano":
                switch (enemy.getObjectName()) {
                    case "Volcano Orc":
                        if (rng <= 20) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 20 && rng <= 25) {
                            Item item = new WoodenArmour(tile, false, player, styletypeVolcano, 100);
                            world.addEntity(item);
                            ((VolcanoWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 25 && rng <= 30) {
                            Item item = new Treasure(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoWorld) world).addDialogue(item.getDisplay());
                        } else if (rng > 30 && rng <= 35){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((VolcanoWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Volcano Goblin":
                        if (rng <= 5) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoWorld) world).addDialogue(item.getDisplay());
                        }
                        break;
                        default: // Do nothing
                }
                break;
            case "VolcanoDungeon":
                switch (enemy.getObjectName()) {
                    case "Volcano Orc":
                        if (rng <= 15) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 15 && rng <= 18) {
                            Item item = new WoodenArmour(tile, false, player, styletypeVolcano, 100);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 18 && rng <= 21) {
                            Item item = new Treasure(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 21 && rng <= 24){
                            Item item = new Poison(tile, false, player, styletypeDesert);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Volcano Goblin":
                        if (rng <= 3) {
                            HealthPotionSmall item = new HealthPotionSmall(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                        default: // Do nothing
                }
                break;
                default: //Do nothing
        }
    }

    /**
     * Generates a strong random number using cryptographic statistics.
     * @return A strong random number
     */
    public static int generateRNG(){
        return new SecureRandom().nextInt(100-0) + 1;
    }
}
