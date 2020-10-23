package deco2800.thomas.entities.enemies.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.PassiveEnemy;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

/**
 * A dummy enemy has limited movement and will apply zero or minor damage to the players.
 * Dummies are special enemies and need to be spawned manually using spawnSpecialEnemy() inside EnemyManager.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/monsters/dummy
 */
public class Dummy extends Monster implements PassiveEnemy {
    private final Animation<TextureRegion> dummyIdle;
    private float stateTimer;

    public Dummy(int health, float speed) {
        super(health, speed);
        this.identifier = "dummy";
        this.setTexture("dummy");
        this.setObjectName("Dummy");
        this.stateTimer = 0;
        this.dummyIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    public void hitByTarget() {
        // ouch
    }

    @Override
    public void death() {
        super.death();
        GameManager.getManagerFromInstance(EnemyManager.class).removeSpecialEnemy(this);
        PlayerPeon.credit(100);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = dummyIdle.getKeyFrame(stateTimer);
        stateTimer = stateTimer + delta;
        return region;
    }

    @Override
    public Dummy deepCopy() {
        return new Dummy(super.getMaxHealth(), super.getSpeed());
    }
}
