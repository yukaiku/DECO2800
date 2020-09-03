package deco2800.thomas.util;

import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.enemies.EnemyPeon;

/**
 * The brain of the enemy.
 */
public class EnemyUtil {
    public static boolean playerInRadius(EnemyPeon enemy, AgentEntity player, int awareRadius) {
        return Math.sqrt(Math.pow(Math.round(enemy.getCol()) - Math.round(player.getCol()), 2) +
                Math.pow(Math.round(enemy.getRow()) - Math.round(player.getRow()), 2)) < awareRadius;
    }

    public static boolean playerInRange(EnemyPeon enemy, AgentEntity player, int attackRange) {
        return Math.sqrt(Math.pow(Math.round(enemy.getCol()) - Math.round(player.getCol()), 2) +
                Math.pow(Math.round(enemy.getRow()) - Math.round(player.getRow()), 2)) < attackRange;
    }
}
