package deco2800.thomas.entities.enemies.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class TundraDragon extends Dragon {
    public TundraDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.identifier = "dragonTundra";
        this.variation = Variation.TUNDRA;
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
        this.setObjectName("Giaphias");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }
}
