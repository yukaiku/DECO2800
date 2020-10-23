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

import java.util.Random;

public class ItemDropTable {

    public ItemDropTable(){
        // Do nothing
    }


    public static void dropItemForEnemyType(Tile tile, EnemyPeon enemy, PlayerPeon player, AbstractWorld world) {
        int rng = generateRNG();
        String styletypeSwamp = "swamp";
        String styletypeTundra = "tundra";
        String styletypeDesert = "desert";
        String styletypeVolcano = "volcano";
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
                        if (rng <= 10) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 10 && rng <= 12) {
                            Item item = new WoodenArmour(tile, false, player, styletypeSwamp, 100);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 12 && rng <= 14) {
                            Item item = new Treasure(tile, false, player, styletypeSwamp);
                            world.addEntity(item);
                            ((SwampDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Swamp Goblin":
                        if (rng <= 2) {
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
                        if (rng <= 10) {
                            Item item = new HealthPotionSmall(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 10 && rng <= 12) {
                            Item item = new WoodenArmour(tile, false, player, styletypeVolcano, 100);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        } else if (rng > 12 && rng <= 14) {
                            Item item = new Treasure(tile, false, player, styletypeVolcano);
                            world.addEntity(item);
                            ((VolcanoDungeon) world).addDialogue(item.getDisplay());
                        }
                        break;
                    case "Volcano Goblin":
                        if (rng <= 2) {
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

    public static int generateRNG(){
        return new Random().nextInt(100-0) + 1;
    }
}
