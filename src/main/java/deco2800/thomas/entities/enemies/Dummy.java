package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Peon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;

/**
 * A dummy enemy has limited movement and will apply zero or minor damage to the players.
 * Dummies are special enemies and need to be spawned manually using spawnSpecialEnemy() inside EnemyManager.
 */
public class Dummy extends Monster implements PassiveEnemy {
    public Dummy(int height, float speed, int health) {
        super("Dummy", "spacman_blue", height, speed, health, false);
    }

    public void hitByTarget(Peon Target) {
        // ouch
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeSpecialEnemy(this);
    }

    @Override
    public Dummy deepCopy() {
        return new Dummy(super.getHeight(), super.getSpeed(), super.getMaxHealth());
    }
}
