package deco2800.thomas.entities.enemies;

import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;

/**
 * A dummy enemy has limited movement and will apply zero or minor damage to the players.
 * Dummies are special enemies and need to be spawned manually using spawnSpecialEnemy() inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/monsters/dummy
 */
public class Dummy extends Monster implements PassiveEnemy {
    public Dummy(int height, float speed, int health) {
        super("Dummy", "dummy", height, speed, health, false);
    }

    public void hitByTarget() {
        // ouch
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeSpecialEnemy(this);
        GameManager.get().getWorld().getPlayerEntity().credit(100);
    }

    @Override
    public Dummy deepCopy() {
        return new Dummy(super.getHeight(), super.getSpeed(), super.getMaxHealth());
    }
}
